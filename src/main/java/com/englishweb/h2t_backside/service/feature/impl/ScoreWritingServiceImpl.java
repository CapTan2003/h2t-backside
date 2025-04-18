package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.WritingScoreDTO;
import com.englishweb.h2t_backside.service.feature.GeminiService;
import com.englishweb.h2t_backside.service.feature.ScoreWritingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ScoreWritingServiceImpl implements ScoreWritingService {

    private final GeminiService geminiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public WritingScoreDTO scoreWriting(String text, String topic) {
        log.info("Scoring writing with topic: {}", topic);

        String prompt = buildPrompt(text, topic);
        log.info("Sending prompt to Gemini service");

        try {
            String geminiResponse = geminiService.generateText(prompt);
            log.info("Received response from Gemini: {}", geminiResponse);

            // Process the response to extract the JSON content
            String jsonContent = extractJsonFromResponse(geminiResponse);
            log.info("Extracted JSON content: {}", jsonContent);

            // Parse the JSON response into WritingScoreDTO
            WritingScoreDTO scoreDTO = objectMapper.readValue(jsonContent, WritingScoreDTO.class);
            log.info("Successfully parsed response into WritingScoreDTO");

            return scoreDTO;
        } catch (Exception e) {
            log.error("Error scoring writing: {}", e.getMessage(), e);
            // Fallback response in case of error
            WritingScoreDTO fallbackDTO = new WritingScoreDTO();
            fallbackDTO.setScore("0");
            fallbackDTO.setStrengths(new ArrayList<>());
            fallbackDTO.setAreas_to_improve(new ArrayList<>());
            fallbackDTO.setFeedback("Error processing writing sample. Please try again.");
            return fallbackDTO;
        }
    }

    /**
     * Extracts valid JSON from a response that might contain md formatting or other text
     *
     * @param response the raw response from Gemini
     * @return cleaned JSON string
     */
    private String extractJsonFromResponse(String response) {
        // Remove md code block indicators if present
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

        // If we can't find valid JSON, throw an exception
        throw new RuntimeException("Could not extract valid JSON from response: " + response);
    }

    private String buildPrompt(String text, String topic) {
        return "You are a professional English teacher with 15+ years of experience in evaluating academic writing, IELTS, TOEFL, and English proficiency assessments. Your expertise lies in providing detailed, constructive feedback to help students improve their writing skills.\n\n" +
                "Task: Thoroughly evaluate the following text based on multiple criteria and provide a comprehensive assessment.\n\n" +
                "Topic: \"" + topic + "\"\n\n" +
                "Text to evaluate:\n\"" + text + "\"\n\n" +
                "IMPORTANT: Your response must only contain the JSON object with no markdown formatting, no code blocks, and no prefixes like ```json. Just return the plain JSON object.\n\n" +
                "Please conduct a detailed analysis of the text using the following criteria:\n\n" +
                "1. Vocabulary (25 points):\n" +
                "   - Lexical range: Variety and sophistication of vocabulary (0-8 points)\n" +
                "   - Word choice accuracy: Appropriate word selection for context (0-8 points)\n" +
                "   - Collocations and idiomatic expressions: Natural language use (0-5 points)\n" +
                "   - Technical/topic-specific vocabulary: Use of relevant terminology (0-4 points)\n\n" +

                "2. Grammar & Syntax (25 points):\n" +
                "   - Grammatical accuracy: Correct verb tenses, articles, prepositions (0-8 points)\n" +
                "   - Syntactic variety: Mix of simple, compound, and complex sentences (0-6 points)\n" +
                "   - Punctuation: Proper use of punctuation marks (0-5 points)\n" +
                "   - Agreement: Subject-verb agreement, pronoun reference (0-6 points)\n\n" +

                "3. Topic Relevance & Development (25 points):\n" +
                "   - Focus: Clear alignment with the assigned topic (0-7 points)\n" +
                "   - Development: Thorough exploration of ideas related to the topic (0-7 points)\n" +
                "   - Examples & evidence: Supporting details that enhance arguments (0-6 points)\n" +
                "   - Coherence: Logical flow of ideas within the topic framework (0-5 points)\n\n" +

                "4. Organization & Structure (15 points):\n" +
                "   - Introduction: Clear presentation of main ideas/thesis (0-4 points)\n" +
                "   - Body paragraphs: Well-structured with topic sentences and support (0-4 points)\n" +
                "   - Conclusion: Effective summary and closing thoughts (0-3 points)\n" +
                "   - Transitions: Smooth connections between ideas and paragraphs (0-4 points)\n\n" +

                "5. Overall Effectiveness (10 points):\n" +
                "   - Clarity: Clear communication of ideas (0-3 points)\n" +
                "   - Audience awareness: Appropriate tone and style (0-3 points)\n" +
                "   - Persuasiveness: Convincing presentation of arguments (0-2 points)\n" +
                "   - Originality: Fresh perspective or approach to the topic (0-2 points)\n\n" +

                "Calculate the total score by adding all sections (maximum 100 points).\n\n" +

                "Identify 3-5 specific strengths that are particularly noteworthy in the text.\n" +
                "Identify 3-5 specific areas for improvement with actionable suggestions.\n" +
                "Provide a concise but comprehensive overall assessment (2-4 sentences) highlighting the most significant aspects of the writing.\n\n" +

                "Your evaluation MUST be returned ONLY in the following JSON format with NO additional text or explanation:\n" +
                "{\n" +
                "  \"score\": \"[numerical score out of 100]\",\n" +
                "  \"strengths\": [\"strength1\", \"strength2\", \"strength3\", ...],\n" +
                "  \"areas_to_improve\": [\"specific area1 with suggestion\", \"specific area2 with suggestion\", \"specific area3 with suggestion\", ...],\n" +
                "  \"feedback\": \"[concise overall assessment of the writing quality]\"\n" +
                "}\n\n" +

                "Important instructions:\n" +
                "- Be specific and detailed in your analysis while maintaining the JSON format\n" +
                "- Ensure all strengths and areas for improvement are actionable and specific\n" +
                "- Base your evaluation strictly on the text provided, not assumptions\n" +
                "- Maintain a professional, constructive tone in your feedback\n" +
                "- Return ONLY the JSON object with no additional text, explanations, or commentary\n" +
                "- Ensure the JSON is properly formatted and can be parsed by standard JSON parsers";
    }
}