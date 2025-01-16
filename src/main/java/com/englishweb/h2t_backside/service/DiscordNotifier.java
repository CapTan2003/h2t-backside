package com.englishweb.h2t_backside.service;

import com.englishweb.h2t_backside.dto.response.ErrorDTO;

public interface DiscordNotifier {

    void sendNotification(String content);

    void buildErrorAndSend(ErrorDTO errorDTO);
}
