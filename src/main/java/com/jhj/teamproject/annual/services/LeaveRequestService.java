package com.jhj.teamproject.annual.services;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.annual.mappers.LeaveRequestMapper;
import com.jhj.teamproject.annual.results.LeaveResult;
import com.jhj.teamproject.user.entities.UserEntity;
import com.jhj.teamproject.user.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LeaveRequestService {
    private final LeaveRequestMapper leaveRequestMapper;
    private final UserMapper userMapper;

    @Autowired
    public LeaveRequestService(LeaveRequestMapper leaveRequestMapper, UserMapper userMapper) {
        this.leaveRequestMapper = leaveRequestMapper;
        this.userMapper = userMapper;
    }

    public LeaveResult insert(LeaveRequestEntity leaveRequest) {

        LeaveRequestEntity leave = new LeaveRequestEntity();
        /*UserEntity user = this.userMapper.selectBy;
        leave.setUserId();*/
        leave.setStartDate(leaveRequest.getStartDate());
        leave.setEndDate(leaveRequest.getEndDate());
        leave.setDays(leaveRequest.getDays());
        leave.setContent(leaveRequest.getContent());
        leave.setStatus(leaveRequest.getStatus());
        leave.setCategory(leaveRequest.getCategory());
        leave.setCreatedAt(LocalDateTime.now());

        return this.leaveRequestMapper.insert(leave) > 0 ? LeaveResult.SUCCESS : LeaveResult.FAILURE;
    }
}
