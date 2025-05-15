package com.jhj.teamproject.admin.entities;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UsersEntity {
    private int id;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
