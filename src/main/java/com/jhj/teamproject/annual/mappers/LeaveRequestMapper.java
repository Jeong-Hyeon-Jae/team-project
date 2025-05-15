package com.jhj.teamproject.annual.mappers;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LeaveRequestMapper {
    int insert (@Param(value = "leaveRequest") LeaveRequestEntity leaveRequest);
    List<LeaveRequestEntity> select(@Param(value = "email") String email);
    boolean selectCountByEmailAndDate(@Param(value = "email") String email,
                                   @Param(value = "leaveRequest") LeaveRequestEntity reaveRequest);
}
