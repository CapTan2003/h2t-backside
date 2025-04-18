package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.SpeakingScoreDTO;
import com.englishweb.h2t_backside.dto.WritingScoreDTO;
import com.englishweb.h2t_backside.service.feature.ScoreSpeakingService;
import com.englishweb.h2t_backside.service.feature.ScoreWritingService;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ScoreSpeakingServiceImpl implements ScoreSpeakingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    // Base API URL
    private static final String BASE_API_URL = "https://speech-scoring-api-production.up.railway.app/api/speech";

    // Specific endpoints
    private static final String PROCESS_AUDIO_URL = BASE_API_URL + "/process-audio";
    private static final String EVALUATE_PRONUNCIATION_URL = BASE_API_URL + "/evaluate-pronunciation";
    private static final String EVALUATE_MULTIPLE_URL = BASE_API_URL + "/evaluate-multiple";
    private final ScoreWritingService scoreWritingService;

    public ScoreSpeakingServiceImpl(ObjectMapper objectMapper, ScoreWritingService scoreWritingService) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = objectMapper;
        this.scoreWritingService = scoreWritingService;
    }

    @Override
    public SpeakingScoreDTO evaluateSpeaking(MultipartFile audioFile, String expectedText) throws Exception {
        log.info("Starting speech evaluation for expectedText: {}", expectedText);

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
        body.add("topic", expectedText);

        // Create the HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Send POST request to the API
            ResponseEntity<String> response = restTemplate.exchange(
                    PROCESS_AUDIO_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from speech API with status: {}", response.getStatusCode());

            // Parse the response
            return parseApiResponse(response);

        } catch (RestClientException e) {
            log.error("Error calling speech API: {}", e.getMessage(), e);
            throw new Exception("Failed to communicate with speech processing API: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error processing speech evaluation: {}", e.getMessage(), e);
            throw new Exception("Error evaluating speech: " + e.getMessage());
        }
    }

    @Override
    public SpeakingScoreDTO evaluateSpeechInTopic(MultipartFile audioFile, String topic) throws Exception {
        log.info("Starting pronunciation-only evaluation for file: {}", audioFile.getOriginalFilename());

        // Create HttpHeaders object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create MultiValueMap for the request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Add the audio file
        ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
            @Override
            public String getFilename() {
                return audioFile.getOriginalFilename();
            }
        };
        body.add("audio", resource);

        // Create the HTTP entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Send POST request to the pronunciation-only API
            ResponseEntity<String> response = restTemplate.exchange(
                    EVALUATE_PRONUNCIATION_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from pronunciation API with status: {}", response.getStatusCode());

            // Parse the response
            SpeakingScoreDTO scoreResult = parseApiResponse(response);

            // Evaluate the relevance of the topic
            WritingScoreDTO writingScore = scoreWritingService.scoreWriting(scoreResult.getTranscript(), topic);
            scoreResult.setStrengths(Stream.of(scoreResult.getStrengths(), writingScore.getStrengths())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()));
            scoreResult.setAreas_to_improve(Stream.of(scoreResult.getAreas_to_improve(), writingScore.getAreas_to_improve())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()));
            scoreResult.setFeedback(scoreResult.getFeedback() + "\n"+ writingScore.getFeedback());

            scoreResult.setScore(((Double.parseDouble(scoreResult.getScore()) * 10 + Double.parseDouble(writingScore.getScore())) / 2) + "");

            return scoreResult;

        } catch (RestClientException e) {
            log.error("Error calling pronunciation API: {}", e.getMessage(), e);
            throw new Exception("Failed to communicate with pronunciation API: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error evaluating pronunciation: {}", e.getMessage(), e);
            throw new Exception("Error evaluating pronunciation: " + e.getMessage());
        }
    }

    @Override
    public SpeakingScoreDTO evaluateMultipleFiles(List<MultipartFile> audioFiles, List<String> expectedTexts) throws Exception {
        log.info("Starting evaluation of multiple files, count: {}", audioFiles.size());

        // Validate input
        if (audioFiles == null || audioFiles.isEmpty()) {
            throw new Exception("No audio files provided");
        }

        if (expectedTexts == null || expectedTexts.isEmpty()) {
            log.warn("No expected texts provided, defaulting to empty strings");
            expectedTexts = new ArrayList<>();
            for (int i = 0; i < audioFiles.size(); i++) {
                expectedTexts.add("");
            }
        }

        // Create HttpHeaders object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create MultiValueMap for the request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Add all audio files
        for (MultipartFile audioFile : audioFiles) {
            ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            };
            body.add("files", resource);
        }

        // Add all topics
        for (String expectedText : expectedTexts) {
            body.add("topics", expectedText);
        }

        // Create the HTTP entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Send POST request to the multiple files API
            ResponseEntity<String> response = restTemplate.exchange(
                    EVALUATE_MULTIPLE_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from multiple files API with status: {}", response.getStatusCode());

            // Parse the response for multiple files
            return parseMultipleFilesResponse(response);

        } catch (RestClientException e) {
            log.error("Error calling multiple files API: {}", e.getMessage(), e);
            throw new Exception("Failed to communicate with multiple files API: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error evaluating multiple files: {}", e.getMessage(), e);
            throw new Exception("Error evaluating multiple files: " + e.getMessage());
        }
    }

    /**
     * Helper method to parse API responses for single file evaluations
     */
    private SpeakingScoreDTO parseApiResponse(ResponseEntity<String> response) throws Exception {
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
    }

    /**
     * Helper method to parse API responses for multiple file evaluations
     */
    private SpeakingScoreDTO parseMultipleFilesResponse(ResponseEntity<String> response) throws Exception {
        // Parse the response into a SpeakingScoreDTO object
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            SpeakingScoreDTO scoreDTO = new SpeakingScoreDTO();
            scoreDTO.setScore(rootNode.path("score").asText());

            // For multiple files, join the transcripts
            StringBuilder transcriptBuilder = new StringBuilder();
            JsonNode transcriptsNode = rootNode.path("transcripts");
            if (transcriptsNode.isArray()) {
                for (int i = 0; i < transcriptsNode.size(); i++) {
                    if (i > 0) {
                        transcriptBuilder.append("\n");
                    }
                    transcriptBuilder.append("File ").append(i + 1).append(": ")
                            .append(transcriptsNode.get(i).asText());
                }
            }
            scoreDTO.setTranscript(transcriptBuilder.toString());

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
            throw new Exception("Failed to process multiple audio files: " + response.getStatusCode());
        }
    }
}