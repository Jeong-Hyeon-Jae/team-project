package com.jhj.teamproject.services;

import com.jhj.teamproject.entities.UserEntity;
import com.jhj.teamproject.mappers.UserMapper;
import com.jhj.teamproject.results.user.RegisterResult;
import com.jhj.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public static boolean isEmailValid(String input) {
        return input != null && input.matches("^(?=.{8,50}$)([\\da-z\\-_.]{4,})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2,3})?$");
    }

    public static boolean isPasswordValid(String input) {
        return input != null && input.matches("^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{8,50})$");
    }

    public static boolean isNameValid(String input) {
        return input != null && input.matches("^([\\da-zA-Z가-힣]{2,10})$");
    }



    public RegisterResult register(UserEntity user) {
        if (user == null ||
                !UserService.isEmailValid(user.getEmail()) ||
                !UserService.isPasswordValid(user.getPassword()) ||
                !UserService.isNameValid(user.getName())) {
            return RegisterResult.FAILURE;
        }

/*        if (this.userMapper.selectCountByEmail(user.getEmail()) > 0) {
            return RegisterResult.FAILURE_EMAIL_NOT_AVAILABLE;
        }
        if (this.userMapper.selectCountByName(user.getName()) > 0) {
            return RegisterResult.FAILURE_NAME_NOT_AVAILABLE;
        }*/

        user.setEmail(CryptoUtils.hashSha512(user.getEmail()));
        user.setPassword(user.getPassword());
        user.setName(user.getName());
        /*user.setRole(user.getRole());*/
        user.setCreatedAt(LocalDateTime.now());

        return this.userMapper.insertUser(user) > 0 ? RegisterResult.SUCCESS : RegisterResult.FAILURE;
    }
}
