package com.englishweb.h2t_backside.dto.feature;


import lombok.Data;
import java.util.List;

@Data
public class TestCommentResponseDTO {
    private List<String> strengths;
    private List<String> areasToImprove;
    private String feedback;
}