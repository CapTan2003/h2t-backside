package com.englishweb.h2t_backside.model.log;

import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.enummodel.ActionEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment("Id user performing update")
    private User user;                // Id người dùng thực hiện update

    @Column(nullable = false)
    @Comment("Id of record being updated")
    private Long targetId;              // Id đối tượng được update

    @Column(nullable = false)
    @Comment("Table name of record being updated")
    private String targetTable;         // Tên bảng của đối tượng được update

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Comment("Timestamp of update")
    private LocalDateTime timestamp;    // Thời gian thực hiện update

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("Action performed: CREATE, UPDATE, DELETE")
    @Builder.Default
    private ActionEnum action = ActionEnum.CREATE;          // Hành động được thực hiện: CREATE, UPDATE, DELETE
}