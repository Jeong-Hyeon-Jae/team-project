package com.jhj.teamproject.annual.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnualSummaryEntity {
    private int id;
    private int userId;
    private String name;
    private String email;
    private int totalDays;
    private int usedDays;
}
