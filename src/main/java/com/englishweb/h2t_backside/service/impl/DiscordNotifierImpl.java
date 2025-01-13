package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.response.ErrorDTO;
import com.englishweb.h2t_backside.utils.CustomLocalDateTimeSerializer;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import com.englishweb.h2t_backside.service.ErrorLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class DiscordNotifierImpl implements DiscordNotifier {

    private final ErrorLogService errorLogService;

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DiscordNotifierImpl(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
        configureObjectMapper();
    }

    private void configureObjectMapper() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        // Disable serialization as timestamps (arrays or numeric)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Register the custom serializer for LocalDateTime
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        objectMapper.registerModule(module);
    }

    @Override
    public void sendNotification(String message) {
        try {
            String payload = objectMapper.writeValueAsString(new DiscordMessage(message));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            // Send POST request to Discord webhook
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
        final LocalDateTime errorTime = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

        ErrorLogDTO errorLogDTO = ErrorLogDTO.builder()
                .message(errorMessage)
                .errorCode(errorCode)
                .timestamp(errorTime)
                .build();

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .errorCode(errorCode)
                .timestamp(errorTime)
                .data(dto)
                .build();

        try {
            String discordPayload = objectMapper.writeValueAsString(errorDTO);
            // Send the notification with formatted JSON
            this.sendNotification("```json\n" + discordPayload + "\n```");
            // Persist the error log
            errorLogService.create(errorLogDTO);
        } catch (JsonProcessingException e) { // Catch specific exception
            log.error("Error converting ErrorDTO to JSON: {}", e.getMessage(), e);
        }
    }

    @Getter
    @AllArgsConstructor
    private static class DiscordMessage {
        private String content;
    }
}
