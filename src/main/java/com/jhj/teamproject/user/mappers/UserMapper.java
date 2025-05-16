package com.jhj.teamproject.user.mappers;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import com.jhj.teamproject.user.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserEntity selectByEmail(@Param(value = "email") String email);

    int insertUser(@Param(value = "user") UserEntity user);

    int insertAnnual(@Param(value = "annual") AnnualEntity annual);

    int selectCountByEmail(@Param(value = "email") String email);

    int selectIdByEmail(@Param(value = "email") String email);

    int update(@Param(value = "user") UserEntity user);

    int updatePassword(@Param(value="user") UserEntity user);

    UserEntity selectInfoByName(@Param(value = "name") String name);
}