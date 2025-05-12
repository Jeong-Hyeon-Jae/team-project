package com.jhj.teamproject.mappers;

import com.jhj.teamproject.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int insertUser(@Param(value = "user") UserEntity user);
/*    int selectCountByEmail(@Param(value = "email") String email);
    int selectCountByName(@Param(value = "name") String name);*/
}
