package com.englishweb.h2t_backside.service.ai.impl;

import com.englishweb.h2t_backside.service.ai.LLMService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LLMServiceImpl implements LLMService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.api.url:https://openrouter.ai/api/v1/chat/completions}")
    private String apiUrl;

    @Value("${openrouter.model:deepseek/deepseek-chat-v3-0324:free}")
    private String model;

    public LLMServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String generateText(String prompt) {
        try {
            log.info("Generating text for prompt: {}", truncatePrompt(prompt));

            ResponseEntity<String> response = makeApiRequest(prompt);

            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String generatedText = rootNode.path("choices").path(0).path("message").path("content").asText();

            log.info("Text generation completed successfully. Response length: {} characters",
                    generatedText != null ? generatedText.length() : 0);

            return generatedText;
        } catch (Exception e) {
            log.error("Error generating text from LLM: {}", e.getMessage(), e);
            throw new RuntimeException("Error generating text from LLM: " + e.getMessage(), e);
        }
    }

    @Override
    public Object generateResult(String prompt) {
        try {
            ResponseEntity<String> response = makeApiRequest(prompt);
            return objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            log.error("Error generating result from LLM: {}", e.getMessage(), e);
            throw new RuntimeException("Error generating result from LLM: " + e.getMessage(), e);
        }
    }

    private ResponseEntity<String> makeApiRequest(String prompt) {
        try {
            long startTime = System.currentTimeMillis();
            log.info("Making API request to OpenRouter at: {}", apiUrl);
            log.info("Using model: {}", model);
            log.debug("Request prompt: {}", prompt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            // Add OpenRouter specific headers for data policy
            headers.set("HTTP-Referer", "http://localhost:3000/");  // Replace with your actual domain
            headers.set("X-Title", "EnglishWeb LLM Service");  // Replace with your application name

            // Add data policies to bypass the 404 error
            headers.set("OR-DATA-STORAGE-CONSENT", "true");
            headers.set("OR-PROMPT-AI-TRAINING-CONSENT", "true");
            headers.set("OR-RESPONSE-AI-TRAINING-CONSENT", "true");

            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", model);

            // Add OpenRouter specific parameters
            requestBody.put("route", "fallback");  // Use fallback routing if preferred model is unavailable

            ArrayNode messagesArray = requestBody.putArray("messages");
            ObjectNode userMessage = messagesArray.addObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);

            String requestBodyString = requestBody.toString();
            log.debug("Request payload: {}", requestBodyString.replaceAll("(\"content\":\\s*\").*?(\")", "$1[CONTENT MASKED]$2"));

            HttpEntity<String> request = new HttpEntity<>(requestBodyString, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

            long endTime = System.currentTimeMillis();
            log.info("API request completed in {}ms with status code: {}",
                    (endTime - startTime), response.getStatusCode());

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.warn("Non-successful status code received: {}", response.getStatusCode());
                log.debug("Error response: {}", response.getBody());
            }

            return response;
        } catch (Exception e) {
            log.error("Exception occurred during API request: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Truncates the prompt for logging purposes
     */
    private String truncatePrompt(String prompt) {
        if (prompt == null) {
            return "null";
        }
        int maxLength = 100;
        if (prompt.length() <= maxLength) {
            return prompt;
        }
        return prompt.substring(0, maxLength) + "...";
    }
}