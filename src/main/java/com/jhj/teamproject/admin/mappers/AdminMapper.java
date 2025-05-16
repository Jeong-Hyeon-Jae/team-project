package com.jhj.teamproject.admin.mappers;

import com.jhj.teamproject.admin.entities.RequestsEntity;
import com.jhj.teamproject.user.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
 RequestsEntity[] AllRequests();

 int updateRequests(@Param(value = "user") UserEntity user,
                    @Param(value = "status")String status,
                    @Param(value = "id")int id);

 UserEntity selectByEmail(@Param("email") String email);
}
