package com.englishweb.h2t_backside.model.abstractmodel;

import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.model.interfacemodel.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBaseEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private StatusEnum status = StatusEnum.ACTIVE;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
