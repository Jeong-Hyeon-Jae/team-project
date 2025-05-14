package com.jhj.teamproject.user.services;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import com.jhj.teamproject.user.entities.UserEntity;
import com.jhj.teamproject.user.mappers.UserMapper;
import com.jhj.teamproject.user.results.LoginResult;
import com.jhj.teamproject.user.results.RegisterResult;
import com.jhj.teamproject.user.utils.Bcrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final int totalDays = 14;
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
        return input != null && input.matches("^([a-zA-Z가-힣]{2,50})$");
    }

    public LoginResult login(UserEntity user) {
        if (user == null
                || !isEmailValid(user.getEmail())
                || !isPasswordValid(user.getPassword())) {
            return LoginResult.FAILURE;
        }
        UserEntity dbUser = this.userMapper.selectByEmail(user.getEmail());
        if (dbUser == null || !Bcrypt.isMatch(user.getPassword(), dbUser.getPassword())) {
            return LoginResult.FAILURE;
        }
        return LoginResult.SUCCESS;
    }

    public RegisterResult register(UserEntity user) {

        System.out.println("registerService");
        if (user == null) {
            return RegisterResult.FAILURE;
        }
        if (!isEmailValid(user.getEmail())) {
            System.out.println("이메일 오류");
            return RegisterResult.FAILURE_INVALID_EMAIL;
        }
        if (!isPasswordValid(user.getPassword())) {
            System.out.println("비밀번호 오류");
            return RegisterResult.FAILURE_INVALID_PASSWORD;
        }
        if (!isNameValid(user.getName())) {
            System.out.println("이름 오류");
            return RegisterResult.FAILURE_INVALID_NAME;
        }

        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setPassword(Bcrypt.encrypt(user.getPassword()));
        user.setRole(user.getRole());
        user.setJoinedAt(user.getJoinedAt());

        if(this.userMapper.insertUser(user)==0 ){
            return RegisterResult.FAILURE;
        }
        AnnualEntity annual = new AnnualEntity();
        annual.setUserId(user.getId());
        annual.setTotalDays(totalDays);
        annual.setUsedDays(0);

        if(this.userMapper.insertAnnual(annual)==0){
            return RegisterResult.FAILURE;
        }
        return RegisterResult.SUCCESS;
    }
}
