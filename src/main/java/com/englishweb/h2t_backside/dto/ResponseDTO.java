package com.englishweb.h2t_backside.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ResponseDTO {
    ResponseStatusEnum status;  // Success || Fail
    Object data;                // Payload
    String message;             // Message
}