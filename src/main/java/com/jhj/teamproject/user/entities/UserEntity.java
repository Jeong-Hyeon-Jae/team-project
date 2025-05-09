package com.jhj.teamproject.user.entities;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class UserEntity {
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDateTime createAt;
}
