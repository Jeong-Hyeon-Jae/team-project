package com.jhj.teamproject.user.entities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UserEntity {
    private int id;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDate joinedAt;
    private LocalDate modifiedAt;
}
