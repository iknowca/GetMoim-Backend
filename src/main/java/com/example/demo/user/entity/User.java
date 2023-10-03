package com.example.demo.user.entity;

import com.example.demo.user.controller.form.UserInfoResForm;
import com.example.demo.user.controller.form.UserResForm;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Setter
    private String nickname;
    private String email;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Setter
    private Profile profile;
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private UserRole userRole;

    public User(String name, String nickname, String email) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
    }
    public RoleType getRole() {
        return this.userRole.getRole().getRoleType();
    }
    public UserInfoResForm toInfoResForm() {
        return UserInfoResForm.builder()
                .email(email)
                .id(id)
                .roleType(this.getRole())
                .nickname(nickname)
                .build();
    }
    public UserResForm toResForm() {
        return new UserResForm(id, nickname);
    }

}
