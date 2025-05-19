package com.jhj.annual.user.mappers;

import com.jhj.annual.annual.entities.AnnualEntity;
import com.jhj.annual.user.entities.UserEntity;
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