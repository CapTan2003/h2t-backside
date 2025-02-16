package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestSpeakingDTO extends AbstractBaseDTO {

    @NotNull(message = "Questions cannot be null")
    private String questions; // Lưu danh sách ID các câu hỏi
}
