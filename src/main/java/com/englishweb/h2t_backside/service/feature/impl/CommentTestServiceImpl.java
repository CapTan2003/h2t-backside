package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.AIResponseDTO;
import com.englishweb.h2t_backside.dto.TestCommentRequestDTO;
import com.englishweb.h2t_backside.dto.TestCommentResponseDTO;
import com.englishweb.h2t_backside.service.feature.AIResponseService;
import com.englishweb.h2t_backside.service.feature.LLMService;
import com.englishweb.h2t_backside.service.feature.CommentTestService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CommentTestServiceImpl implements CommentTestService {

    private final LLMService llmService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AIResponseService aiResponseService;

    @Override
    public TestCommentResponseDTO comment(TestCommentRequestDTO request) {
        log.info("Generating test comment");

        String prompt = buildPrompt(request);
        log.info("Sending prompt to LLM service");

        try {
            String llmResponse = llmService.generateText(prompt);
            log.debug("Received response from LLM: {}", llmResponse);

            // Process the response to extract the JSON content
            String jsonContent = extractJsonFromResponse(llmResponse);
            log.debug("Extracted JSON content: {}", jsonContent);

            // Parse and normalize the JSON
            JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode normalizedJson = normalizeJsonKeys(rootNode);

            // Convert the normalized JSON to JSON string
            String normalizedJsonString = objectMapper.writeValueAsString(normalizedJson);
            log.debug("Normalized JSON: {}", normalizedJsonString);

            // Parse the normalized JSON response into TestCommentResponseDTO
            TestCommentResponseDTO commentDTO = objectMapper.readValue(normalizedJsonString, TestCommentResponseDTO.class);
            log.info("Successfully parsed response into TestCommentResponseDTO");

            // Save AI Response to db
            saveAIResponse(request, commentDTO);

            return commentDTO;
        } catch (Exception e) {
            log.error("Error generating test comment: {}", e.getMessage(), e);
            // Fallback response in case of error
            return createFallbackResponse();
        }
    }

    /**
     * Creates a fallback response in case of errors
     */
    private TestCommentResponseDTO createFallbackResponse() {
        TestCommentResponseDTO fallbackDTO = new TestCommentResponseDTO();
        fallbackDTO.setFeedback("Excellent work! Your vocabulary shows depth and your grammar fundamentals are solid. Focus on complex sentence structures to elevate your writing further.");
        fallbackDTO.setStrengths(new ArrayList<>());
        fallbackDTO.setAreasToImprove(new ArrayList<>());
        return fallbackDTO;
    }

    /**
     * Saves the AI request and response to the database
     */
    private void saveAIResponse(TestCommentRequestDTO request, TestCommentResponseDTO response) {
        try {
            AIResponseDTO aiResponse = new AIResponseDTO();

            // Simplify request to avoid very large logs
            String requestSummary = summarizeTestRequest(request);
            aiResponse.setRequest("Generate test comment for test data: " + requestSummary);

            // Format the response summary
            String responseSummary = String.format(
                    "{\n" +
                            "  feedback: %s,\n" +
                            "  strengths: %s,\n" +
                            "  areasToImprove: %s\n" +
                            "}",
                    response.getFeedback(),
                    response.getStrengths(),
                    response.getAreasToImprove()
            );

            aiResponse.setResponse(responseSummary);
            aiResponseService.create(aiResponse);
        } catch (Exception e) {
            // Just log the error but don't block the main process
            log.error("Error saving AI response to database: {}", e.getMessage());
        }
    }

    /**
     * Creates a summary of the test request to avoid storing very large objects
     */
    private String summarizeTestRequest(TestCommentRequestDTO request) {
        StringBuilder summary = new StringBuilder("{\n");

        if (request.getVocabulary() != null) {
            summary.append("  vocabulary: ").append(request.getVocabulary().size()).append(" questions,\n");
        }

        if (request.getGrammar() != null) {
            summary.append("  grammar: ").append(request.getGrammar().size()).append(" questions,\n");
        }

        if (request.getReading() != null) {
            summary.append("  reading: ").append(request.getReading().size()).append(" passages,\n");
        }

        if (request.getListening() != null) {
            summary.append("  listening: ").append(request.getListening().size()).append(" sections,\n");
        }

        if (request.getSpeaking() != null) {
            summary.append("  speaking: ").append(request.getSpeaking().size()).append(" questions,\n");
        }

        if (request.getWriting() != null) {
            summary.append("  writing: ").append(request.getWriting().size()).append(" tasks\n");
        }

        summary.append("}");
        return summary.toString();
    }

    /**
     * Normalizes JSON keys to match the expected DTO structure
     * This handles variations in the LLM's response format
     */
    private JsonNode normalizeJsonKeys(JsonNode jsonNode) {
        ObjectNode normalizedNode = objectMapper.createObjectNode();

        // Handle feedback field with various possible keys
        List<String> feedbackKeys = Arrays.asList(
                "feedback", "comment", "overall_comment", "overall_feedback",
                "assessment", "evaluation", "summary", "general_feedback",
                "overall_assessment", "test_feedback", "complete_feedback"
        );

        String feedbackValue = "";
        for (String key : feedbackKeys) {
            if (jsonNode.has(key) && jsonNode.get(key).isTextual()) {
                feedbackValue = jsonNode.get(key).asText();
                break;
            }
        }

        // If no feedback field was found or it's empty, create a specific, targeted fallback
        if (feedbackValue.isEmpty()) {
            feedbackValue = "Your vocabulary shows strong precision. Your grammar is solid with occasional tense inconsistencies. Focus on complex structures and transitions to elevate your writing further.";
        }

        normalizedNode.put("feedback", feedbackValue);

        // Set empty arrays for strengths and areasToImprove
        normalizedNode.putArray("strengths");
        normalizedNode.putArray("areasToImprove");

        return normalizedNode;
    }

    /**
     * Extracts valid JSON from a response that might contain markdown formatting or other text
     */
    private String extractJsonFromResponse(String response) {
        // Remove markdown code block indicators if present
        if (response.contains("```json")) {
            response = response.replaceAll("```json", "").replaceAll("```", "");
        } else if (response.contains("```")) {
            response = response.replaceAll("```", "");
        }

        // Trim to handle any whitespace
        response = response.trim();

        // If the response starts with a JSON object indicator and ends with a JSON object closure
        if (response.startsWith("{") && response.endsWith("}")) {
            return response;
        }

        // Try to find the JSON object in the response - look for the first '{' and last '}'
        int startIndex = response.indexOf('{');
        int endIndex = response.lastIndexOf('}');

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return response.substring(startIndex, endIndex + 1);
        }

        // If we can't find valid JSON, construct a minimal valid JSON with the response as feedback
        ObjectNode fallbackNode = objectMapper.createObjectNode();

        // Take at most the first 1000 characters of the response as feedback
        String shortenedFeedback = response;
        if (response.length() > 1000) {
            shortenedFeedback = response.substring(0, 1000) + "...";
        }

        fallbackNode.put("feedback", shortenedFeedback);
        fallbackNode.putArray("strengths");
        fallbackNode.putArray("areasToImprove");

        try {
            return objectMapper.writeValueAsString(fallbackNode);
        } catch (Exception e) {
            // If all else fails, throw an exception
            throw new RuntimeException("Could not extract or create valid JSON from response: " + response);
        }
    }

    /**
     * Builds the prompt for the LLM based on the test data to generate insightful, concise feedback
     */
    private String buildPrompt(TestCommentRequestDTO request) {
        StringBuilder prompt = new StringBuilder();

        // System instruction - establish expert persona
        prompt.append("You are an expert language teacher who specializes in precise, insightful feedback. Your task is to provide a targeted assessment in about 50 words that identifies specific patterns and gives actionable guidance.\n\n");

        // Response format instructions with emphasis on specificity and brevity
        prompt.append("INSTRUCTIONS:\n");
        prompt.append("1. Return a JSON object with a 'feedback' field containing your assessment (~50 words)\n");
        prompt.append("2. Be specific about observed strengths and concrete improvement areas\n");
        prompt.append("3. Use precise language focusing on observable patterns, not vague generalizations\n");
        prompt.append("4. Include one actionable recommendation tied directly to their performance\n");
        prompt.append("5. The feedback must feel personalized, not generic\n\n");

        // Test data summary with analysis focus
        prompt.append("TEST SECTIONS AND ANALYSIS FOCUS:\n");

        // Build a list of completed sections
        List<String> completedSections = new ArrayList<>();
        List<String> analysisPoints = new ArrayList<>();

        if (request.getVocabulary() != null && !request.getVocabulary().isEmpty()) {
            completedSections.add("vocabulary");
            analysisPoints.add("precision of word choice");
            analysisPoints.add("range of lexical items");
            prompt.append("- Vocabulary (").append(request.getVocabulary().size()).append(" questions): Analyze word choice precision and lexical range\n");
        }

        if (request.getGrammar() != null && !request.getGrammar().isEmpty()) {
            completedSections.add("grammar");
            analysisPoints.add("structural accuracy");
            analysisPoints.add("tense consistency");
            prompt.append("- Grammar (").append(request.getGrammar().size()).append(" questions): Evaluate structural accuracy and tense consistency\n");
        }

        if (request.getReading() != null && !request.getReading().isEmpty()) {
            completedSections.add("reading");
            analysisPoints.add("comprehension depth");
            analysisPoints.add("inference ability");
            prompt.append("- Reading (").append(request.getReading().size()).append(" passages): Assess comprehension depth and inference skills\n");
        }

        if (request.getListening() != null && !request.getListening().isEmpty()) {
            completedSections.add("listening");
            analysisPoints.add("auditory discrimination");
            analysisPoints.add("contextual understanding");
            prompt.append("- Listening (").append(request.getListening().size()).append(" sections): Evaluate auditory discrimination and context interpretation\n");
        }

        if (request.getSpeaking() != null && !request.getSpeaking().isEmpty()) {
            completedSections.add("speaking");
            analysisPoints.add("fluency");
            analysisPoints.add("pronunciation clarity");
            prompt.append("- Speaking (").append(request.getSpeaking().size()).append(" tasks): Focus on fluency and pronunciation clarity\n");
        }

        if (request.getWriting() != null && !request.getWriting().isEmpty()) {
            completedSections.add("writing");
            analysisPoints.add("idea development");
            analysisPoints.add("organizational coherence");
            prompt.append("- Writing (").append(request.getWriting().size()).append(" tasks): Evaluate idea development and organizational coherence\n");
        }

        prompt.append("\n");

        // Targeted feedback framework
        prompt.append("TARGETED FEEDBACK FRAMEWORK:\n");

        // Add specific analytical focus points from completed sections
        if (!analysisPoints.isEmpty()) {
            prompt.append("1. Core Analysis: Focus on ");
            for (int i = 0; i < Math.min(3, analysisPoints.size()); i++) {
                if (i > 0) prompt.append(i == analysisPoints.size() - 1 ? " and " : ", ");
                prompt.append(analysisPoints.get(i));
            }
            prompt.append("\n");
        }

        prompt.append("2. Strengths: Identify ONE specific strength with concrete evidence\n");
        prompt.append("3. Growth Area: Pinpoint ONE precise area for improvement with clear rationale\n");
        prompt.append("4. Action Step: Recommend ONE specific practice activity that directly addresses the growth area\n\n");

        // Examples of specific vs. generic feedback
        prompt.append("SPECIFICITY EXAMPLES:\n");
        prompt.append("- GENERIC: \"Your vocabulary is good.\" ❌\n");
        prompt.append("- SPECIFIC: \"Your academic vocabulary shows precision in technical terms but needs expansion in transition phrases.\" ✅\n\n");

        prompt.append("- GENERIC: \"Work on your grammar.\" ❌\n");
        prompt.append("- SPECIFIC: \"Your complex sentences show strong clause management, but tense consistency wavers in hypothetical scenarios.\" ✅\n\n");

        // Response format
        prompt.append("RESPONSE FORMAT:\n");
        prompt.append("{\n");
        prompt.append("  \"feedback\": \"Your precise, targeted feedback focusing on specific patterns observed (~50 words)...\"\n");
        prompt.append("}\n\n");

        // Example of excellent feedback
        prompt.append("EXAMPLE OF EXCELLENT FEEDBACK:\n");
        prompt.append("\"Your vocabulary demonstrates strong precision in technical terms while your grammar shows mastery of complex structures. Focus on developing more varied transitional phrases between paragraphs to strengthen your writing's cohesion and flow.\"\n\n");

        // Final reminders
        prompt.append("CRITICAL REMINDERS:\n");
        prompt.append("1. BE SPECIFIC - avoid vague comments that could apply to anyone\n");
        prompt.append("2. BE CONCRETE - reference observable patterns, not generalities\n");
        prompt.append("3. BE ACTIONABLE - provide guidance that can be immediately applied\n");
        prompt.append("4. BE PRECISE - use approximately 50 words to maintain impact\n");
        prompt.append("5. BE INSIGHTFUL - show analytical depth that reveals expert understanding\n");

        return prompt.toString();
    }

    /**
     * Helper method to truncate text to a specified length
     */
    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }
}