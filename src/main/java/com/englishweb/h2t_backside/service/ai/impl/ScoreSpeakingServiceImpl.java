package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.dto.feature.AIResponseDTO;
import com.englishweb.h2t_backside.dto.feature.ConversationScoreDTO;
import com.englishweb.h2t_backside.dto.feature.SpeakingScoreDTO;
import com.englishweb.h2t_backside.dto.feature.WritingScoreDTO;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.ai.ScoreSpeakingService;
import com.englishweb.h2t_backside.service.ai.ScoreWritingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ScoreSpeakingServiceImpl implements ScoreSpeakingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    // Updated to use correct API URLs based on app.py
    @Value("${vosk.api.url}")
    private String BASE_API_URL;

    private final ScoreWritingService scoreWritingService;
    private final AIResponseService aiResponseService;

    public ScoreSpeakingServiceImpl(ObjectMapper objectMapper, ScoreWritingService scoreWritingService, AIResponseService aiResponseService) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = objectMapper;
        this.scoreWritingService = scoreWritingService;
        this.aiResponseService = aiResponseService;
    }

    @Override
    public SpeakingScoreDTO evaluateSpeaking(MultipartFile audioFile, String expectedText) throws Exception {
        log.info("Starting speech evaluation for expectedText: {}", expectedText);

        // Create a HttpHeaders object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create MultiValueMap to hold the multipart request
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Add the audio file - using "file" as the parameter name expected by the API
        ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
            @Override
            public String getFilename() {
                return audioFile.getOriginalFilename();
            }
        };
        body.add("file", resource);

        // Add topic parameter as expected by the API
        body.add("topic", expectedText);

        // Create the HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Send POST request to the API
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_API_URL + "/predict",
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
        log.info("Starting speech evaluation in topic for file: {}", audioFile.getOriginalFilename());

        // Create HttpHeaders object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create MultiValueMap for the request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Add the audio file - using "file" as the parameter name expected by the API
        ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
            @Override
            public String getFilename() {
                return audioFile.getOriginalFilename();
            }
        };
        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Send POST request to the regular predict API with topic
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_API_URL + "/predict_pronunciation",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from speech API with status: {}", response.getStatusCode());

            // Parse the response
            SpeakingScoreDTO scoreResult = parseApiResponse(response);

            // Save AI Response to db
            AIResponseDTO aiResponse = new AIResponseDTO();
            aiResponse.setRequest(
                    "Score speaking in topic: " + audioFile.getOriginalFilename() + "\n" +
                            "{\n" +
                                "Topic: " + topic + ",\n" +
                                "Transcript: " + scoreResult.getTranscript() + "\n" +
                            "}"
            );
            aiResponse.setResponse(
                    "{\n" +
                            "Score: " + scoreResult.getScore() + ",\n" +
                            "Strengths: " + scoreResult.getStrengths() + ",\n" +
                            "Areas to improve: " + scoreResult.getAreas_to_improve() + ",\n" +
                            "Feedback: " + scoreResult.getFeedback()+ "\n" +
                    "}"
            );
            aiResponse.setUserId(null);
            aiResponseService.create(aiResponse);

            // Additional evaluation with writing service if needed
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
            log.error("Error calling speech API: {}", e.getMessage(), e);
            throw new Exception("Failed to communicate with speech API: " + e.getMessage());
        }
    }

    @Override
    public ConversationScoreDTO evaluateMultipleFiles(List<MultipartFile> audioFiles, List<String> expectedTexts) throws Exception {
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

        // Add all audio files - using "files" as expected by the API, not "files[]"
        for (MultipartFile audioFile : audioFiles) {
            ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            };
            body.add("files", resource);
        }

        // Add all topics - using "topics" as expected by the API (will be split by \n in API)
        body.add("topics", String.join("\n", expectedTexts));

        // Create the HTTP entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Send POST request to the multiple files API
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_API_URL + "/predict_multiple",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("Received response from multiple files API with status: {}", response.getStatusCode());

            // Parse the response for multiple files
            return parseMultipleFilesResponse(response);

        } catch (RestClientException e) {
            log.error("Error calling multiple files API: {}", e.getMessage(), e);

            // Fall back to individual processing if batch processing fails
            log.info("Falling back to individual file processing");

            List<SpeakingScoreDTO> scores = new ArrayList<>();

            for (int i = 0; i < audioFiles.size(); i++) {
                String expectedText = i < expectedTexts.size() ? expectedTexts.get(i) : "";
                scores.add(evaluateSpeaking(audioFiles.get(i), expectedText));
            }

            ConversationScoreDTO scoreResult = new ConversationScoreDTO();

            // Calculate average score
            double averageScore = scores.stream()
                    .mapToDouble(score -> Double.parseDouble(score.getScore()))
                    .average()
                    .orElse(0);
            scoreResult.setScore(String.valueOf(averageScore));

            // Collect transcripts in order
            List<String> transcripts = scores.stream()
                    .map(SpeakingScoreDTO::getTranscript)
                    .collect(Collectors.toList());
            scoreResult.setTranscripts(transcripts);

            // Collect unique strengths
            Set<String> uniqueStrengths = new HashSet<>();
            scores.forEach(score -> {
                if (score.getStrengths() != null) {
                    uniqueStrengths.addAll(score.getStrengths());
                }
            });
            scoreResult.setStrengths(new ArrayList<>(uniqueStrengths));

            // Collect unique areas to improve
            Set<String> uniqueAreasToImprove = new HashSet<>();
            scores.forEach(score -> {
                if (score.getAreas_to_improve() != null) {
                    uniqueAreasToImprove.addAll(score.getAreas_to_improve());
                }
            });
            scoreResult.setAreas_to_improve(new ArrayList<>(uniqueAreasToImprove));

            // Collect unique feedback
            Set<String> uniqueFeedback = new HashSet<>();
            scores.forEach(score -> {
                if (score.getFeedback() != null && !score.getFeedback().isEmpty()) {
                    uniqueFeedback.add(score.getFeedback());
                }
            });
            scoreResult.setFeedback(String.join("\n\n", uniqueFeedback));

            // Save AI Response to db
            AIResponseDTO aiResponse = new AIResponseDTO();
            aiResponse.setRequest(
                    "Score speaking in conversation: " + "\n" +
                     "{\n" +
                            "Transcripts: " + scoreResult.getTranscripts() + "\n" +
                     "}"
            );
            aiResponse.setResponse(
                    "{\n" +
                            "Score: " + scoreResult.getScore() + ",\n" +
                            "Strengths: " + scoreResult.getStrengths() + ",\n" +
                            "Areas_to_improve: " + scoreResult.getAreas_to_improve() + ",\n" +
                            "Feedback: " + scoreResult.getFeedback()+ "\n" +
                    "}"
            );
            aiResponseService.create(aiResponse);

            return scoreResult;
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

            // Map fields from API response to DTO
            // Based on the API response structure in api.py
            scoreDTO.setScore(rootNode.path("score").asText());
            scoreDTO.setTranscript(rootNode.path("transcript").asText());

            // The API returns "feedback" but our DTO expects "detailedFeedback"
            scoreDTO.setFeedback(rootNode.path("feedback").asText());

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
            JsonNode areasNode = rootNode.path("areas_to_improve");
            if (areasNode.isArray()) {
                for (JsonNode node : areasNode) {
                    areasToImprove.add(node.asText());
                }
            }
            scoreDTO.setAreas_to_improve(areasToImprove);

            // Handle potential error case
            if (rootNode.has("error")) {
                log.warn("API returned an error: {}", rootNode.path("error").asText());
                if (scoreDTO.getFeedback() == null || scoreDTO.getFeedback().isEmpty()) {
                    scoreDTO.setFeedback("Error: " + rootNode.path("error").asText());
                }
            }

            return scoreDTO;
        } else {
            log.error("API returned non-successful status: {}", response.getStatusCode());
            throw new Exception("Failed to process audio: " + response.getStatusCode());
        }
    }

    /**
     * Helper method to parse API responses for multiple file evaluations
     */
    private ConversationScoreDTO parseMultipleFilesResponse(ResponseEntity<String> response) throws Exception {
        // Parse the response into a ConversationScoreDTO object
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            ConversationScoreDTO scoreDTO = new ConversationScoreDTO();
            scoreDTO.setScore(rootNode.path("score").asText());

            // For multiple files, collect the transcripts
            List<String> transcripts = new ArrayList<>();
            JsonNode transcriptsNode = rootNode.path("transcripts");

            if (transcriptsNode.isArray()) {
                for (JsonNode node : transcriptsNode) {
                    transcripts.add(node.asText());
                }
            }

            scoreDTO.setTranscripts(transcripts);

            // Use "feedback" field instead of "detailedFeedback" to match API response
            scoreDTO.setFeedback(rootNode.path("feedback").asText());

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
            JsonNode areasNode = rootNode.path("areas_to_improve");
            if (areasNode.isArray()) {
                for (JsonNode node : areasNode) {
                    areasToImprove.add(node.asText());
                }
            }
            scoreDTO.setAreas_to_improve(areasToImprove);

            // Handle potential error case
            if (rootNode.has("error")) {
                log.warn("API returned an error: {}", rootNode.path("error").asText());
                if (scoreDTO.getFeedback() == null || scoreDTO.getFeedback().isEmpty()) {
                    scoreDTO.setFeedback("Error: " + rootNode.path("error").asText());
                }
            }

            return scoreDTO;
        } else {
            log.error("API returned non-successful status: {}", response.getStatusCode());
            throw new Exception("Failed to process multiple audio files: " + response.getStatusCode());
        }
    }
}