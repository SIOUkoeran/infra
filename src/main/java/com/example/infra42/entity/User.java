package com.example.infra42.entity;

import com.example.infra42.entity.enums.UserRole;
import com.example.infra42.entity.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import javax.validation.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Nullable
    @Size(min = 8)
    private String password;

    @Email
    private String email;

    private String code;

    private UserStatus user;

    private String poolYear;
    private String poolMonth;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public String getRoleKey(){
        return role.getKey();
    }
}
