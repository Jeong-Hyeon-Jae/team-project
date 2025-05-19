package com.jhj.annual.annual.services;

import com.jhj.annual.annual.entities.LeaveRequestEntity;
import com.jhj.annual.annual.mappers.LeaveRequestMapper;
import com.jhj.annual.annual.results.LeaveCategory;
import com.jhj.annual.annual.results.LeaveStatus;
import com.jhj.annual.annual.results.Result;
import com.jhj.annual.user.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveRequestService {
    private final LeaveRequestMapper leaveRequestMapper;
    private final UserMapper userMapper;

    @Autowired
    public LeaveRequestService(LeaveRequestMapper leaveRequestMapper, UserMapper userMapper) {
        this.leaveRequestMapper = leaveRequestMapper;
        this.userMapper = userMapper;
    }

    public Result insert(LeaveRequestEntity leaveRequest,
                         String email) {
        if (leaveRequest.getStartDate() == null ||
            leaveRequest.getEndDate() == null ||
            leaveRequest.getContent() == null ||
            leaveRequest.getStatus() == null) {

            return Result.FAILURE;
        }
        boolean exists = leaveRequestMapper.selectCountByEmailAndDate(email, leaveRequest);

        if (email != null && exists) {
            // 오늘 날 보다 신청 날짜가 이전일 경우
            return Result.FAILURE_DUPLICATE_DATE;
        }

        int year = leaveRequest.getStartDate().getYear();
        int month = leaveRequest.getStartDate().getMonthValue();

        boolean existsInSameMonth = leaveRequestMapper.existsByMonth(this.userMapper.selectIdByEmail(email), year, month);
        if (existsInSameMonth) {
            // 한달에 두 번 이상 연차 신청을 했을 경우
            return Result.FAILURE_DUPLICATE_MONTH;
        }


        if (isDateOnWeekend(leaveRequest.getStartDate(), leaveRequest.getEndDate())) {
            // 신청일과 종료일이 주말인 경우
            return Result.FAILURE_WEEKEND_NOT_ALLOWED;
        }

        LeaveRequestEntity leave = new LeaveRequestEntity();

        if (leaveRequest.getCategory().equals(LeaveCategory.LEAVE)) {
            leave.setCategory(LeaveCategory.LEAVE);
        } else if (leaveRequest.getCategory().equals(LeaveCategory.HALF)) {
            leave.setCategory(LeaveCategory.HALF);
        } else {
            return Result.FAILURE;
        }
        if (leaveRequest.getStatus().equals(LeaveStatus.PENDING)) {
            leave.setStatus(LeaveStatus.PENDING);
        } else {
            return Result.FAILURE;
        }
        leave.setUserId(this.userMapper.selectIdByEmail(email));
        leave.setStartDate(leaveRequest.getStartDate());
        leave.setEndDate(leaveRequest.getEndDate());
        // 연차 사용일수 주말제외 로직
        int realDays = calculateBusinessDays(leaveRequest.getStartDate(), leaveRequest.getEndDate());
        leave.setDays(realDays);
        leave.setContent(leaveRequest.getContent());
        leave.setCreatedAt(LocalDateTime.now());
        /*leave.setCategory(LeaveCategory.valueOf(leaveRequest.getCategory()));
        leave.setStatus(leaveRequest.getStatus());*/



        return this.leaveRequestMapper.insert(leave) > 0 ? Result.SUCCESS : Result.FAILURE;
    }

    public List<LeaveRequestEntity> selectByEmail(String email) {
        return this.leaveRequestMapper.select(email);
    }

    public boolean isDateOnWeekend(LocalDate startDate, LocalDate endDate) {
        DayOfWeek dayS = startDate.getDayOfWeek();
        DayOfWeek dayE = endDate.getDayOfWeek();

        return dayS == DayOfWeek.SATURDAY ||
                dayS == DayOfWeek.SUNDAY ||
                dayE == DayOfWeek.SATURDAY ||
                dayE == DayOfWeek.SUNDAY;
    }

    public int calculateBusinessDays(LocalDate start, LocalDate end) {
        int businessDays = 0;
        LocalDate current = start;

        while (!current.isAfter(end)) {
            DayOfWeek day = current.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                businessDays++;
            }
            current = current.plusDays(1);
        }

        return businessDays;
    }
}
