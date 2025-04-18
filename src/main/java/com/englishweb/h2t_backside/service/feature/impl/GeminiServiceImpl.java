package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.GeminiRequestDTO;
import com.englishweb.h2t_backside.service.feature.GeminiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GeminiServiceImpl implements GeminiService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${gemini.api.key}")
    private String apiKey;
    @Value("${gemini.api.url}")
    private String apiUrl;

    @Override
    public String generateText(String prompt) {

        GeminiRequestDTO geminiRequest = GeminiRequestDTO.builder()
                .contents(List.of(Map.of("parts", List.of(Map.of("text", prompt))))).build();

        // Log the request details before sending it
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(geminiRequest);
            log.info("Preparing to call Gemini API with request body: {}", requestBody);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash" + ":generateContent?key=" + apiKey;
            log.info("Calling Gemini API at URL: {}", apiUrl + ":generateContent?key=<apiKey>");

            // Send the request and log the response
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            String responseBody = response.getBody();
            log.info("Received response from Gemini API: {}", responseBody);

            // Process the response
            ObjectMapper responseMapper = new ObjectMapper();
            JsonNode rootNode = responseMapper.readTree(responseBody);
            JsonNode candidatesNode = rootNode.path("candidates");
            if (candidatesNode.isArray() && !candidatesNode.isEmpty()) {
                JsonNode contentNode = candidatesNode.get(0).path("content").path("parts").get(0);
                return contentNode.path("text").asText();
            } else {
                log.error("No candidates found in the response from Gemini API.");
                throw new RuntimeException("No candidates found in the response.");
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                log.error("Unauthorized access to Gemini API. Status code: {}, Message: {}", e.getStatusCode(), e.getMessage());
                throw new RuntimeException("Unauthorized access to Gemini API. Please check your API key and permissions.", e);
            } else {
                log.error("Error communicating with Gemini API. Status code: {}, Message: {}", e.getStatusCode(), e.getMessage());
                throw new RuntimeException("Error communicating with Gemini API: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage(), e);
        }
    }
}
