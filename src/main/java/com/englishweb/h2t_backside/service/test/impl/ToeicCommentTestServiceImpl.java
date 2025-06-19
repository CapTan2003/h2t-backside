package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.dto.ai.TestCommentResponseDTO;
import com.englishweb.h2t_backside.dto.ai.ToeicCommentRequestDTO;
import com.englishweb.h2t_backside.exception.CommentTestException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.service.ai.AIResponseService;
import com.englishweb.h2t_backside.service.ai.LLMService;
import com.englishweb.h2t_backside.service.ai.ToeicCommentTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ToeicCommentTestServiceImpl implements ToeicCommentTestService {

    private final LLMService llmService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AIResponseService aiResponseService;

    @Override
    public TestCommentResponseDTO comment(ToeicCommentRequestDTO request) {
        log.info("Generating TOEIC test comment");

        String prompt = buildToeicPrompt(request);
        log.info("Sending TOEIC prompt to LLM service");

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
        } catch (JsonProcessingException e) {
            log.error("Error generating TOEIC test comment: {}", e.getMessage(), e);
            throw new CommentTestException("Error when parsing response from llm in generating TOEIC test comment: " + e.getMessage(), SeverityEnum.MEDIUM);
        } catch (Exception e) {
            log.error("Error generating TOEIC test comment: {}", e.getMessage(), e);
            throw new CommentTestException("Unexpected error when generating TOEIC test comment: " + e.getMessage(), SeverityEnum.HIGH);
        }
    }

    /**
     * Saves the AI request and response to the database
     */
    private void saveAIResponse(ToeicCommentRequestDTO request, TestCommentResponseDTO response) {
        try {
            AIResponseDTO aiResponse = new AIResponseDTO();

            // Simplify request to avoid very large logs
            String requestSummary = summarizeToeicTestRequest(request);
            aiResponse.setRequest("Generate TOEIC test comment for test result: " + requestSummary);

            // Format the response summary
            String responseSummary = String.format(
                    "{\n" +
                            "  feedback: %s\n" +
                            "}",
                    response.getFeedback()
            );

            aiResponse.setResponse(responseSummary);
            aiResponseService.create(aiResponse);
        } catch (Exception e) {
            // Just log the error but don't block the main process
            log.error("Error saving TOEIC AI response to database: {}", e.getMessage());
        }
    }

    /**
     * Creates a summary of the TOEIC test request for logging purposes
     */
    private String summarizeToeicTestRequest(ToeicCommentRequestDTO request) {
        StringBuilder summary = new StringBuilder("TOEIC Performance Analysis:\n");

        summary.append("Overall Score: ").append(request.getTotalScore()).append("/990\n");
        summary.append("Listening: ").append(request.getListeningScore()).append("/495\n");
        summary.append("Reading: ").append(request.getReadingScore()).append("/495\n");
        summary.append("Accuracy: ").append(request.getCorrectAnswers()).append("/").append(request.getAnsweredQuestions()).append("\n");

        if (request.getPartAccuracy() != null) {
            ToeicCommentRequestDTO.PartAccuracyInfo accuracy = request.getPartAccuracy();
            summary.append("Part Breakdown:\n");
            if (accuracy.getPart1Accuracy() != null)
                summary.append("  Part 1: ").append(String.format("%.1f", accuracy.getPart1Accuracy())).append("%\n");
            if (accuracy.getPart2Accuracy() != null)
                summary.append("  Part 2: ").append(String.format("%.1f", accuracy.getPart2Accuracy())).append("%\n");
            if (accuracy.getPart3Accuracy() != null)
                summary.append("  Part 3: ").append(String.format("%.1f", accuracy.getPart3Accuracy())).append("%\n");
            if (accuracy.getPart4Accuracy() != null)
                summary.append("  Part 4: ").append(String.format("%.1f", accuracy.getPart4Accuracy())).append("%\n");
            if (accuracy.getPart5Accuracy() != null)
                summary.append("  Part 5: ").append(String.format("%.1f", accuracy.getPart5Accuracy())).append("%\n");
            if (accuracy.getPart6Accuracy() != null)
                summary.append("  Part 6: ").append(String.format("%.1f", accuracy.getPart6Accuracy())).append("%\n");
            if (accuracy.getPart7Accuracy() != null)
                summary.append("  Part 7: ").append(String.format("%.1f", accuracy.getPart7Accuracy())).append("%");
        }

        return summary.toString();
    }

    /**
     * Builds the prompt for the LLM based on the TOEIC test data
     */
    private String buildToeicPrompt(ToeicCommentRequestDTO request) {
        StringBuilder prompt = new StringBuilder();

        // Expert persona establishment
        prompt.append("You are a seasoned TOEIC expert with over 10 years of experience teaching and analyzing TOEIC test performance. You have helped thousands of students improve their scores through precise diagnostic feedback and targeted learning strategies. Your expertise lies in identifying performance patterns and providing actionable improvement plans.\n\n");

        // Response format requirements
        prompt.append("TASK: Analyze the following TOEIC test results and provide expert feedback.\n\n");

        prompt.append("RESPONSE FORMAT:\n");
        prompt.append("Return ONLY a JSON object with this exact structure:\n");
        prompt.append("{\n");
        prompt.append("  \"feedback\": \"Your professional analysis and recommendations (90-120 words)\"\n");
        prompt.append("}\n\n");

        // Content requirements
        prompt.append("FEEDBACK REQUIREMENTS:\n");
        prompt.append("• Identify the strongest and weakest TOEIC sections with specific part numbers\n");
        prompt.append("• Analyze listening vs reading balance and what it reveals about the test-taker\n");
        prompt.append("• Provide 3 specific, actionable improvement strategies tailored to the weak areas\n");
        prompt.append("• Reference actual accuracy percentages from the data\n");
        prompt.append("• Use professional TOEIC instructor tone but remain encouraging\n\n");

        // Test performance data
        prompt.append("═══ TOEIC TEST RESULTS ANALYSIS ═══\n\n");

        prompt.append("OVERALL PERFORMANCE:\n");
        prompt.append("• Total Score: ").append(request.getTotalScore()).append(" / 990 points\n");
        prompt.append("• Listening Score: ").append(request.getListeningScore()).append(" / 495 points\n");
        prompt.append("• Reading Score: ").append(request.getReadingScore()).append(" / 495 points\n");
        prompt.append("• Questions Answered Correctly: ").append(request.getCorrectAnswers())
                .append(" out of ").append(request.getAnsweredQuestions()).append(" attempted\n\n");

        // Section-by-section breakdown
        ToeicCommentRequestDTO.PartAccuracyInfo accuracy = request.getPartAccuracy();
        if (accuracy != null) {
            prompt.append("DETAILED SECTION PERFORMANCE:\n\n");

            prompt.append("LISTENING SKILLS ASSESSMENT:\n");
            if (accuracy.getPart1Accuracy() != null)
                prompt.append("• Part 1 (Photographs): ").append(String.format("%.1f", accuracy.getPart1Accuracy())).append("% correct\n");
            if (accuracy.getPart2Accuracy() != null)
                prompt.append("• Part 2 (Question-Response): ").append(String.format("%.1f", accuracy.getPart2Accuracy())).append("% correct\n");
            if (accuracy.getPart3Accuracy() != null)
                prompt.append("• Part 3 (Conversations): ").append(String.format("%.1f", accuracy.getPart3Accuracy())).append("% correct\n");
            if (accuracy.getPart4Accuracy() != null)
                prompt.append("• Part 4 (Short Talks): ").append(String.format("%.1f", accuracy.getPart4Accuracy())).append("% correct\n");

            prompt.append("\nREADING SKILLS ASSESSMENT:\n");
            if (accuracy.getPart5Accuracy() != null)
                prompt.append("• Part 5 (Grammar & Vocabulary): ").append(String.format("%.1f", accuracy.getPart5Accuracy())).append("% correct\n");
            if (accuracy.getPart6Accuracy() != null)
                prompt.append("• Part 6 (Text Completion): ").append(String.format("%.1f", accuracy.getPart6Accuracy())).append("% correct\n");
            if (accuracy.getPart7Accuracy() != null)
                prompt.append("• Part 7 (Reading Comprehension): ").append(String.format("%.1f", accuracy.getPart7Accuracy())).append("% correct\n");
        }

        // Analysis framework
        prompt.append("\n═══ EXPERT ANALYSIS FRAMEWORK ═══\n\n");
        prompt.append("As a TOEIC specialist, analyze this performance using these criteria:\n\n");

        prompt.append("1. STRENGTH IDENTIFICATION:\n");
        prompt.append("   - Which parts show mastery (>80% accuracy)?\n");
        prompt.append("   - Is this student stronger in listening or reading?\n");
        prompt.append("   - What skills are already well-developed?\n\n");

        prompt.append("2. WEAKNESS DIAGNOSIS:\n");
        prompt.append("   - Which parts need immediate attention (<70% accuracy)?\n");
        prompt.append("   - Are there specific skill gaps (grammar, vocabulary, comprehension)?\n");
        prompt.append("   - What's preventing score improvement?\n\n");

        prompt.append("3. STRATEGIC RECOMMENDATIONS:\n");
        prompt.append("   - Prioritize the 2-3 most impactful areas for improvement\n");
        prompt.append("   - Suggest specific study methods for weak parts\n");
        prompt.append("   - Recommend time allocation for maximum score gains\n\n");

        // Professional examples
        prompt.append("EXPERT FEEDBACK EXAMPLES:\n\n");
        prompt.append("Example 1: \"Your listening foundation is solid with Part 1 at 85% and Part 2 at 80%, demonstrating strong basic comprehension skills. However, Part 7 reading at 58% is significantly limiting your overall score. Focus on daily timed reading practice with business articles, develop skimming strategies for main ideas, and build academic vocabulary. Your grammar strength (Part 5: 75%) suggests you can quickly improve Part 6 through targeted practice.\"\n\n");

        prompt.append("Example 2: \"Strong reading performance (385/495) versus weaker listening (295/495) indicates excellent analytical skills but limited audio exposure. Your Part 5 dominance (88%) shows solid grammar foundation. To balance your profile, dedicate 60% of study time to Parts 3-4 listening through dictation exercises, note-taking practice, and exposure to various English accents in business contexts.\"\n\n");

        // Final instructions
        prompt.append("═══ FINAL INSTRUCTIONS ═══\n\n");
        prompt.append("Provide your expert analysis with:\n");
        prompt.append("✓ Specific part numbers and percentages from the actual data\n");
        prompt.append("✓ Clear identification of strongest and weakest areas\n");
        prompt.append("✓ Three concrete, actionable improvement strategies\n");
        prompt.append("✓ Professional but encouraging tone\n");
        prompt.append("✓ 90-120 words total\n");
        prompt.append("✓ Focus on practical steps the student can implement immediately\n\n");

        prompt.append("Remember: Your goal is to provide insights that lead to measurable score improvement on the next TOEIC attempt.");

        return prompt.toString();
    }

    /**
     * Normalizes JSON keys to match the expected DTO structure
     */
    private JsonNode normalizeJsonKeys(JsonNode jsonNode) {
        ObjectNode normalizedNode = objectMapper.createObjectNode();

        // Handle feedback field with various possible keys
        List<String> feedbackKeys = Arrays.asList(
                "feedback", "comment", "overall_comment", "overall_feedback",
                "assessment", "evaluation", "summary", "general_feedback"
        );

        String feedbackValue = "";
        for (String key : feedbackKeys) {
            if (jsonNode.has(key) && jsonNode.get(key).isTextual()) {
                feedbackValue = jsonNode.get(key).asText();
                break;
            }
        }

        // If no feedback field was found or it's empty, create a TOEIC-specific fallback
        if (feedbackValue.isEmpty()) {
            feedbackValue = "Your TOEIC performance shows strong listening comprehension in Parts 1-2 but needs improvement in Part 7 reading comprehension. Focus on improving reading speed and grammar structure recognition to raise your overall score. Practice with timed reading exercises and business vocabulary to enhance your performance.";
        }

        normalizedNode.put("feedback", feedbackValue);

        // Chỉ trả về feedback
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

        fallbackNode.put("feedback", "Your TOEIC performance shows good listening skills in Parts 1-2. To improve your overall score, focus on reading comprehension in Part 7 by practicing skimming and scanning techniques, and strengthen your grammar mastery for Part 5 and 6 with targeted practice on prepositions and verb tenses.");

        // Không sử dụng strengths và areasToImprove
        fallbackNode.putArray("strengths");
        fallbackNode.putArray("areasToImprove");

        try {
            return objectMapper.writeValueAsString(fallbackNode);
        } catch (Exception e) {
            // If all else fails, throw an exception
            throw new RuntimeException("Could not extract or create valid JSON from TOEIC response");
        }
    }
}