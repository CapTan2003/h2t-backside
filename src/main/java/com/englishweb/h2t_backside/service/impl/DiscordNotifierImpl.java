package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.ErrorDTO;
import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DiscordNotifierImpl implements DiscordNotifier {

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendNotification(String message) {
        try {
            String payload = objectMapper.writeValueAsString(new DiscordMessage(message));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            // Gửi yêu cầu POST đến Discord webhook
            ResponseEntity<String> response = restTemplate.exchange(discordWebhookUrl, HttpMethod.POST, entity, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Failed to send notification to Discord. HTTP Status: {}", response.getStatusCode());
            } else {
                log.info("Notification sent to Discord successfully.");
            }
        } catch (RestClientException ex) {
            log.error("An error occurred while sending notification to Discord: {}", ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        }
    }

    public <DTO> void buildErrorAndSend(DTO dto, String errorMessage, String errorCode) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .data(dto)
                .build();

        try {
            String discordPayload = objectMapper.writeValueAsString(errorDTO);
            this.sendNotification("```json\n" + discordPayload + "\n```");
        } catch (Exception e) {
            log.error("Error converting ErrorDTO to JSON: ", e);
        }
    }

    @Getter
    @AllArgsConstructor
    private static class DiscordMessage {
        private String content;
    }
}
