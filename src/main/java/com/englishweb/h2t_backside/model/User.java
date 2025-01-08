package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("Unique identifier for the User, generated based on the role")
    private Long id;

    @Column(nullable = false, length = 255)
    @Comment("Full name of the User")
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    @Comment("Email address of the User")
    private String email;

    @Column(nullable = false, length = 255)
    @Comment("Password for the User account")
    private String password;

    @Lob
    @Column(columnDefinition = "TEXT COMMENT 'Avatar image URL for the User'")
    @Comment("Avatar image URL for the User")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Comment("Status of the User (e.g. ACTIVE or INACTIVE)")
    private StatusEnum status = StatusEnum.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Comment("Role of the User (Admin, Student, or Teacher)")
    private RoleEnum roleEnum = RoleEnum.STUDENT;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Comment("Level of the User (e.g. Bachelor, Doctor, Master, or Professor)")
    private LevelEnum levelEnum;

    @CreationTimestamp
    @Column(updatable = false)
    @Comment("Start date of the User account")
    private LocalDateTime startDate;

    @Column
    @Comment("End date of the User account (if applicable)")
    private LocalDateTime endDate;

    @CreationTimestamp
    @Column(updatable = false)
    @Comment("Timestamp when the User was created")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Comment("Timestamp when the User was last updated")
    private LocalDateTime updatedAt;
}
