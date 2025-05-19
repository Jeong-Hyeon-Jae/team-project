package com.jhj.annual.admin.entities;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class LeavesEntity {
    private int id;
    private int userId;
    private int totalDays;
    private int usedDays;
}
