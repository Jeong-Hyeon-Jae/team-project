package com.jhj.annual.user.entities;

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
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private String isDeleted;
    private boolean isAdmin;
    private String contactMvno;
    private String contactFirst;
    private String contactSecond;
    private String contactThird;
    private String addressPostal;
    private String addressPrimary;
    private String addressSecondary;
}
