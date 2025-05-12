package com.jhj.teamproject.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private int id;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDateTime createdAt;
}
