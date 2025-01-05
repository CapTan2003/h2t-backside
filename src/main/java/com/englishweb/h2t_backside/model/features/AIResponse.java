package com.englishweb.h2t_backside.model.features;

public class AIResponse {
    private Long id;
    private String request;     // Prompt đã gửi đến AI
    private String response;    // Kết quả trả về
    private String evaluate;    // Đánh giá của giáo viên
    private Long userId;        // Id giáo viên đã đánh giá phần trả về của AI
}
