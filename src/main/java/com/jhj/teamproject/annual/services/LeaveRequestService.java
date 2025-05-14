package com.jhj.teamproject.annual.services;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.annual.mappers.LeaveRequestMapper;
import com.jhj.teamproject.annual.results.LeaveCategory;
import com.jhj.teamproject.annual.results.LeaveResult;
import com.jhj.teamproject.annual.results.LeaveStatus;
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

    public LeaveResult insert(LeaveRequestEntity leaveRequest,
                              String email) {
        if (leaveRequest.getStartDate() == null ||
            leaveRequest.getEndDate() == null ||
            leaveRequest.getContent() == null ||
            leaveRequest.getStatus() == null) {

            return LeaveResult.FAILURE;
        }

        LeaveRequestEntity leave = new LeaveRequestEntity();

        if (leaveRequest.getCategory().equals(LeaveCategory.LEAVE)) {
            leave.setCategory(LeaveCategory.LEAVE);
        } else if (leaveRequest.getCategory().equals(LeaveCategory.HALF)) {
            leave.setCategory(LeaveCategory.HALF);
        } else {
            return LeaveResult.FAILURE;
        }
        if (leaveRequest.getStatus().equals(LeaveStatus.PENDING)) {
            leave.setStatus(LeaveStatus.PENDING);
        } else {
            return LeaveResult.FAILURE;
        }
        leave.setUserId(this.userMapper.selectIdByEmail(email));
        leave.setStartDate(leaveRequest.getStartDate());
        leave.setEndDate(leaveRequest.getEndDate());
        leave.setDays(leaveRequest.getDays());
        leave.setContent(leaveRequest.getContent());
        leave.setCreatedAt(LocalDateTime.now());
        /*leave.setCategory(LeaveCategory.valueOf(leaveRequest.getCategory()));
        leave.setStatus(leaveRequest.getStatus());*/


        return this.leaveRequestMapper.insert(leave) > 0 ? LeaveResult.SUCCESS : LeaveResult.FAILURE;
    }
}
