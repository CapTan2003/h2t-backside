package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestReadingDTO extends AbstractBaseDTO {

    @NotNull(message = "File cannot be null")
    private String file; // Lưu đường dẫn file docx của Reading

    @NotNull(message = "Questions cannot be null")
    private String questions; // Lưu danh sách ID các câu hỏi
}
