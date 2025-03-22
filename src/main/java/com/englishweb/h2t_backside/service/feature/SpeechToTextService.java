package com.englishweb.h2t_backside.service.feature;

import org.springframework.web.multipart.MultipartFile;

public interface SpeechToTextService {
    String convertSpeechToText(MultipartFile audioFile);

    String convertSpeechToText(String base64Audio);  // 🎯 Xử lý Base64
}
