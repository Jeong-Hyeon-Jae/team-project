package com.jhj.annual.admin.mappers;

import com.jhj.annual.admin.entities.RequestsEntity;
import com.jhj.annual.user.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
 RequestsEntity[] AllRequests(@Param(value = "sort")String sort);

 int updateRequests(@Param(value = "user") UserEntity user,
                    @Param(value = "status")String status,
                    @Param(value = "id")int id);

 UserEntity selectByEmail(@Param("email") String email);

 RequestsEntity selectRequestById(@Param("id") int id);

 int incrementUsedDays(@Param("userId") int userId,
                       @Param("days") int days);
}
