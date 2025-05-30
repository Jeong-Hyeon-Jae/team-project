package com.jhj.annual.admin.entities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class RequestsEntity {
    private int id;
    private int userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int days;
    private String content;
    private String status;
    private int reviewedBy;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
    private String category;
    private String name;
    private String reviewedByName;
}
