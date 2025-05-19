package com.jhj.annual.annual.mappers;

import com.jhj.annual.annual.entities.LeaveRequestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LeaveRequestMapper {
    int insert (@Param(value = "leaveRequest") LeaveRequestEntity leaveRequest);
    List<LeaveRequestEntity> select(@Param(value = "email") String email);
    boolean selectCountByEmailAndDate(@Param(value = "email") String email,
                                   @Param(value = "leaveRequest") LeaveRequestEntity leaveRequest);
    boolean existsByMonth(@Param(value = "userId") int userId,
                          @Param(value = "year") int year,
                          @Param(value = "month") int month);
}
