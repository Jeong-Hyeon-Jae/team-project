package com.jhj.teamproject.annual.mappers;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LeaveRequestMapper {
    int insert (@Param(value = "leaveRequest")LeaveRequestEntity leaveRequest);
}
