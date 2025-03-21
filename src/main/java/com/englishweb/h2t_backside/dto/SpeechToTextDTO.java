package com.englishweb.h2t_backside.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SpeechToTextDTO {
    private MultipartFile audioFile;
}
