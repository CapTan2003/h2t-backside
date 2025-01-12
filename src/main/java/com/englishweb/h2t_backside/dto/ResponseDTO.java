package com.englishweb.h2t_backside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    ResponseStatusEnum status;  // Success || Fail
    T data;                // Payload
    String message;             // Message
}