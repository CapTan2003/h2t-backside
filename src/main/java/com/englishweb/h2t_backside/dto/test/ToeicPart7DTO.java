package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart7DTO extends AbstractBaseDTO {

    @NotBlank(message = "File cannot be blank")
    private String file; // Lưu đường dẫn file docx

    @NotBlank(message = "Questions list cannot be blank")
    private String questions; // Lưu danh sách ID câu hỏi dưới dạng chuỗi
}
