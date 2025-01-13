package com.englishweb.h2t_backside.service;

public interface DiscordNotifier {

    void sendNotification(String content);

    <DTO>void buildErrorAndSend(DTO dto, String errorMessage, String errorCode);
}
