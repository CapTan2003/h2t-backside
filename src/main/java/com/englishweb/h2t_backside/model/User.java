package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User extends AbstractBaseEntity {
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
    @Comment("Role of the User (Admin, Student, or Teacher)")
    @Builder.Default
    private RoleEnum roleEnum = RoleEnum.STUDENT;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Comment("Level of the User (e.g. Bachelor, Doctor, Master, or Professor)")
    private LevelEnum levelEnum;
}
