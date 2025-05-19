package com.jhj.annual.annual.entities;

import com.jhj.annual.annual.results.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnualEntity {
    private int id;
    private int userId;
    private int totalDays;
    private Integer usedDays;
    public int getRemainingDays () {
        return totalDays - usedDays;
    }
}
