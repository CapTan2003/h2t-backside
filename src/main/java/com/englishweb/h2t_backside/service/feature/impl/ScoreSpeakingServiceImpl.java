package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.SpeakingScoreDTO;
import com.englishweb.h2t_backside.service.feature.ScoreSpeakingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ScoreSpeakingServiceImpl implements ScoreSpeakingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "http://localhost:5000/api/speech/process-audio";

    public ScoreSpeakingServiceImpl(ObjectMapper objectMapper) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = objectMapper;
    }

    @Override
    public SpeakingScoreDTO evaluateSpeaking(MultipartFile audioFile, String topic) throws Exception {
        log.info("Starting speech evaluation for topic: {}", topic);

        // Create a HttpHeaders object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create MultiValueMap to hold the multipart request
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Add the audio file
        ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
            @Override
            public String getFilename() {
                return audioFile.getOriginalFilename();
            }
        };
        body.add("audio", resource);

        // Add the topic
        body.add("topic", topic);

        // Create the HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Send POST request to the API
            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from speech API with status: {}", response.getStatusCode());

            // Parse the response into a SpeakingScoreDTO object
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                SpeakingScoreDTO scoreDTO = new SpeakingScoreDTO();
                scoreDTO.setScore(rootNode.path("score").asText());
                scoreDTO.setTranscript(rootNode.path("transcript").asText());
                scoreDTO.setFeedback(rootNode.path("detailedFeedback").asText());

                // Convert JSON arrays to Lists
                List<String> strengths = new ArrayList<>();
                JsonNode strengthsNode = rootNode.path("strengths");
                if (strengthsNode.isArray()) {
                    for (JsonNode node : strengthsNode) {
                        strengths.add(node.asText());
                    }
                }
                scoreDTO.setStrengths(strengths);

                List<String> areasToImprove = new ArrayList<>();
                JsonNode areasNode = rootNode.path("areasToImprove");
                if (areasNode.isArray()) {
                    for (JsonNode node : areasNode) {
                        areasToImprove.add(node.asText());
                    }
                }
                scoreDTO.setAreas_to_improve(areasToImprove);

                return scoreDTO;
            } else {
                log.error("API returned non-successful status: {}", response.getStatusCode());
                throw new Exception("Failed to process audio: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            log.error("Error calling speech API: {}", e.getMessage(), e);
            throw new Exception("Failed to communicate with speech processing API: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error processing speech evaluation: {}", e.getMessage(), e);
            throw new Exception("Error evaluating speech: " + e.getMessage());
        }
    }
}