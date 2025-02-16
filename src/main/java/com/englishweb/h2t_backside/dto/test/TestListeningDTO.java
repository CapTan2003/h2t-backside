package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestListeningDTO extends AbstractBaseDTO {

    @NotNull(message = "Audio cannot be null")
    private String audio; // Lưu đường dẫn file âm thanh của Reading

    @NotNull(message = "Transcript cannot be null")
    private String transcript;

    @NotNull(message = "Questions cannot be null")
    private String questions; // Lưu danh sách ID các câu hỏi
}
