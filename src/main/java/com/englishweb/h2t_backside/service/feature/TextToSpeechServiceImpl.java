package com.englishweb.h2t_backside.service.feature;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class TextToSpeechServiceImpl implements TextToSpeechService {

    @Value("${tts.api.key}")
    private String apiKey;

    @Value("${tts.user.id}")
    private String userId;

    @Override
    public InputStreamResource convertTextToSpeech(String text, String voice) throws IOException, InterruptedException {
        String jsonPayload = String.format("{\"text\":\"%s\",\"voice\":\"%s\",\"output_format\":\"mp3\"}", text, voice);

        // Log jsonPayload để kiểm tra
        log.info("Sending request with payload: {}", jsonPayload);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.play.ht/api/v2/tts/stream"))
                .header("accept", "audio/mpeg")
                .header("content-type", "application/json")
                .header("AUTHORIZATION", apiKey)
                .header("X-USER-ID", userId)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() != 200) {
            String responseBody = new String(response.body(), StandardCharsets.UTF_8);
            throw new IOException("Failed to get TTS response: " + response.statusCode() + " Response body: " + responseBody);
        }

        return new InputStreamResource(new ByteArrayInputStream(response.body()));
    }

    @Override
    public String getAvailableVoices() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.play.ht/api/v2/voices"))
                .header("AUTHORIZATION", apiKey)
                .header("X-USER-ID", userId)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch voices: " + response.statusCode());
        }

        return response.body();
    }
}
