package com.jhj.teamproject.annual.services;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.annual.mappers.LeaveRequestMapper;
import com.jhj.teamproject.annual.results.LeaveResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnnualService {
    private final LeaveRequestMapper leaveRequestMapper;

    @Autowired
    public AnnualService(LeaveRequestMapper leaveRequestMapper) {
        this.leaveRequestMapper = leaveRequestMapper;
    }


}
