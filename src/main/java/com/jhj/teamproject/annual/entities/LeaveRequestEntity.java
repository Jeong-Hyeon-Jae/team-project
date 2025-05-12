package com.jhj.teamproject.annual.entities;

import com.jhj.teamproject.annual.results.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestEntity {
    private int id;
    private int userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int days;
    private String content;
    private LeaveStatus status;
    private int reviewedBy;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
}
