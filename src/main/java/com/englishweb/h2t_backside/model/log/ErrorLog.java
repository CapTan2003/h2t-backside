package com.englishweb.h2t_backside.model.log;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("Message for error log")
    private String message;             // Lời nhắn lỗi

    @Column(nullable = false)
    @Comment("Error code for error log")
    private String errorCode;           // Mã lỗi

    @Column(nullable = false)
    @Comment("Timestamp for error log")
    private LocalDateTime timestamp;    // Thời gian xảy ra lỗi
}