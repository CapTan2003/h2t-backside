package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.TestCommentRequestDTO;
import com.englishweb.h2t_backside.dto.TestCommentResponseDTO;
import com.englishweb.h2t_backside.service.feature.CommentTestService;
import com.englishweb.h2t_backside.service.feature.LLMService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@AllArgsConstructor
public class CommentTestServiceImpl implements CommentTestService {

    private final LLMService llmService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final int LLM_TIMEOUT_SECONDS = 15;
    private static final String JSON_START_DELIMITER = "{";
    private static final String JSON_END_DELIMITER = "}";
    private static final String MARKDOWN_JSON_START = "```json";
    private static final String MARKDOWN_BLOCK_DELIMITER = "```";

    @Override
    public TestCommentResponseDTO comment(TestCommentRequestDTO request) {
        log.info("Generating test comment via LLM for request ID: {}", request.hashCode());

        try {
            String prompt = buildEnhancedPrompt(request);

            // Asynchronously call the LLM service with a timeout
            String llmResponse = invokeAIModelWithTimeout(prompt);
            log.debug("Received LLM response of length: {}", llmResponse.length());

            // Extract and validate JSON from response
            String cleanJson = extractValidJsonFromResponse(llmResponse);
            log.debug("Successfully extracted valid JSON of length: {}", cleanJson.length());

            // Parse JSON to response DTO
            TestCommentResponseDTO responseDTO = deserializeResponse(cleanJson);
            log.info("Successfully generated test comment with feedback length: {}",
                    responseDTO.getFeedback().length());

            return responseDTO;
        } catch (Exception e) {
            log.error("Failed to generate test comment: {}", e.getMessage(), e);
            return createFallbackResponse(e.getMessage());
        }
    }

    private String invokeAIModelWithTimeout(String prompt) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                return llmService.generateText(prompt);
            } catch (Exception e) {
                throw new RuntimeException("LLM service error: " + e.getMessage(), e);
            }
        });

        try {
            return future.get(LLM_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new TimeoutException("LLM service timed out after " + LLM_TIMEOUT_SECONDS + " seconds");
        }
    }

    private String extractValidJsonFromResponse(String response) {
        // Remove markdown formatting if present
        String cleanResponse = removeMarkdownFormatting(response);

        // If the response is already a well-formed JSON, return it
        if (isValidJson(cleanResponse)) {
            return cleanResponse;
        }

        // Try to extract a JSON block
        return extractJsonBlock(cleanResponse);
    }

    private String removeMarkdownFormatting(String response) {
        String cleaned = response;

        // Remove ```json blocks
        if (cleaned.contains(MARKDOWN_JSON_START)) {
            cleaned = cleaned.replaceAll(MARKDOWN_JSON_START, "");
        }

        // Remove remaining ``` blocks
        if (cleaned.contains(MARKDOWN_BLOCK_DELIMITER)) {
            cleaned = cleaned.replaceAll(MARKDOWN_BLOCK_DELIMITER, "");
        }

        return cleaned.trim();
    }

    private boolean isValidJson(String text) {
        return text.startsWith(JSON_START_DELIMITER) &&
                text.endsWith(JSON_END_DELIMITER) &&
                isJsonDeserializable(text);
    }

    private boolean isJsonDeserializable(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    private String extractJsonBlock(String text) {
        int startIndex = text.indexOf(JSON_START_DELIMITER);
        int endIndex = text.lastIndexOf(JSON_END_DELIMITER);

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            String jsonCandidate = text.substring(startIndex, endIndex + 1);

            // Verify extracted JSON is valid
            if (isJsonDeserializable(jsonCandidate)) {
                return jsonCandidate;
            }
        }

        throw new RuntimeException("Could not extract valid JSON from response");
    }

    private TestCommentResponseDTO deserializeResponse(String json) {
        try {
            return objectMapper.readValue(json, TestCommentResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize JSON response: {}", e.getMessage());
            throw new RuntimeException("Invalid JSON format in AI response", e);
        }
    }

    private TestCommentResponseDTO createFallbackResponse(String errorMessage) {
        TestCommentResponseDTO fallback = new TestCommentResponseDTO();
        fallback.setStrengths(Collections.emptyList());
        fallback.setAreasToImprove(Collections.emptyList());
        fallback.setFeedback("Unable to generate feedback at this time due to: " + errorMessage);
        return fallback;
    }

    private String buildEnhancedPrompt(TestCommentRequestDTO dto) {
        StringBuilder sb = new StringBuilder();

        // Define AI role and task
        sb.append("You are an expert English language assessment professional with years of experience providing detailed, actionable feedback.\n\n");
        sb.append("TASK: Analyze the following test responses and provide structured, personalized feedback.\n\n");

        // Specify required output format
        sb.append("OUTPUT REQUIREMENTS:\n");
        sb.append("- Return ONLY valid JSON with no markdown formatting\n");
        sb.append("- Include 3-5 specific strengths with clear examples from the responses\n");
        sb.append("- Include 3-5 actionable areas for improvement with specific guidance\n");
        sb.append("- Provide a concise 2-4 sentence overall evaluation\n");
        sb.append("- Do NOT include scores\n\n");

        // Add test content sections
        appendTestSections(sb, dto);

        // Add explicit JSON formatting instructions for better AI compliance
        sb.append("\nRETURN ONLY THIS JSON STRUCTURE WITH NO ADDITIONAL TEXT:\n");
        sb.append("{\n");
        sb.append("  \"strengths\": [\"specific strength 1\", \"specific strength 2\", ...],\n");
        sb.append("  \"areasToImprove\": [\"specific area 1 with suggestion\", \"specific area 2 with suggestion\", ...],\n");
        sb.append("  \"feedback\": \"concise overall evaluation\"\n");
        sb.append("}\n");

        return sb.toString();
    }

    private void appendTestSections(StringBuilder sb, TestCommentRequestDTO dto) {
        // Vocabulary section
        if (dto.getVocabulary() != null && !dto.getVocabulary().isEmpty()) {
            sb.append("VOCABULARY SECTION:\n");
            dto.getVocabulary().forEach(q -> {
                sb.append("Question: ").append(q.getQuestion()).append("\n");
                sb.append("Options: ").append(q.getChoices()).append("\n");
                sb.append("User Response: ").append(q.getUserAnswer()).append("\n\n");
            });
        }

        // Grammar section
        if (dto.getGrammar() != null && !dto.getGrammar().isEmpty()) {
            sb.append("GRAMMAR SECTION:\n");
            dto.getGrammar().forEach(q -> {
                sb.append("Question: ").append(q.getQuestion()).append("\n");
                sb.append("Options: ").append(q.getChoices()).append("\n");
                sb.append("User Response: ").append(q.getUserAnswer()).append("\n\n");
            });
        }

        // Reading section
        if (dto.getReading() != null && !dto.getReading().isEmpty()) {
            sb.append("READING COMPREHENSION SECTION:\n");
            dto.getReading().forEach(r -> {
                sb.append("Passage:\n").append(r.getPassage()).append("\n\n");
                if (r.getQuestions() != null) {
                    r.getQuestions().forEach(q -> {
                        sb.append("Question: ").append(q.getQuestion()).append("\n");
                        sb.append("Options: ").append(q.getChoices()).append("\n");
                        sb.append("User Response: ").append(q.getUserAnswer()).append("\n\n");
                    });
                }
            });
        }

        // Listening section
        if (dto.getListening() != null && !dto.getListening().isEmpty()) {
            sb.append("LISTENING COMPREHENSION SECTION:\n");
            dto.getListening().forEach(l -> {
                sb.append("Audio Transcript:\n").append(l.getTranscript()).append("\n\n");
                if (l.getQuestions() != null) {
                    l.getQuestions().forEach(q -> {
                        sb.append("Question: ").append(q.getQuestion()).append("\n");
                        sb.append("Options: ").append(q.getChoices()).append("\n");
                        sb.append("User Response: ").append(q.getUserAnswer()).append("\n\n");
                    });
                }
            });
        }

        // Speaking section
        if (dto.getSpeaking() != null && !dto.getSpeaking().isEmpty()) {
            sb.append("SPEAKING SECTION:\n");
            dto.getSpeaking().forEach(s -> {
                sb.append("Prompt: ").append(s.getQuestion()).append("\n");
                sb.append("User's Spoken Response (Transcribed): ").append(s.getTranscript()).append("\n\n");
            });
        }

        // Writing section
        if (dto.getWriting() != null && !dto.getWriting().isEmpty()) {
            sb.append("WRITING SECTION:\n");
            dto.getWriting().forEach(w -> {
                sb.append("Topic: ").append(w.getTopic()).append("\n");
                sb.append("User's Written Response:\n").append(w.getUserAnswer()).append("\n\n");
            });
        }
    }
}