package com.jhj.teamproject.user.mappers;

import com.jhj.teamproject.user.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserEntity selectByEmail(@Param(value = "email") String email);

    UserEntity selectByNickname(@Param(value = "nickname") String nickname);

    int insertUser(@Param(value = "user") UserEntity user);

    int selectCountByEmail(@Param(value = "email") String email);

    int selectCountByNickname(@Param(value = "nickname") String nickname);

    int update(@Param(value = "user") UserEntity user);
}
