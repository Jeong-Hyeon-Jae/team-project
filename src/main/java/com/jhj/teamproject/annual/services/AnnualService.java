package com.jhj.teamproject.annual.services;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.annual.mappers.LeaveRequestMapper;
import com.jhj.teamproject.user.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnnualService {
    private final LeaveRequestMapper leaveRequestMapper;
    private final UserMapper userMapper;

    @Autowired
    public AnnualService(LeaveRequestMapper leaveRequestMapper, UserMapper userMapper) {
        this.leaveRequestMapper = leaveRequestMapper;
        this.userMapper = userMapper;
    }
}
