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
        return input != null && input.matches("^(?=.{2,50}$)([\\da-z\\-_.]{2,})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2,3})?$");
    }

    public static boolean isPasswordValid(String input) {
        return input != null && input.matches("^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{8,50})$");
    }

    public static boolean isNameValid(String input) {
        return input != null && input.matches("^([a-zA-Z가-힣]{2,50})$");
    }

    //로그인
    public LoginResult login(UserEntity user) {
        if (user == null
                || !UserService.isEmailValid(user.getEmail())
                || !UserService.isPasswordValid(user.getPassword())) {
            return LoginResult.FAILURE;
        }
        UserEntity dbUser = this.userMapper.selectByEmail(user.getEmail());
        if (dbUser == null) {
            return LoginResult.FAILURE;
        }
        if (!Bcrypt.isMatch(user.getPassword(), dbUser.getPassword())) {
            return LoginResult.FAILURE;
        }
        user.setPassword(dbUser.getPassword());
        user.setEmail(dbUser.getEmail());
        user.setRole(dbUser.getRole());
        user.setName(dbUser.getName());
        user.setJoinedAt(dbUser.getJoinedAt());
        user.setModifiedAt(dbUser.getModifiedAt());
        return LoginResult.SUCCESS;
    }

    //회원가입
    public RegisterResult register(UserEntity user) {

        if (user == null) {
            return RegisterResult.FAILURE;
        }
        if (!isEmailValid(user.getEmail())) {
            System.out.println("올바르지 않은 이메일");
            return RegisterResult.FAILURE_INVALID_EMAIL;
        }
//        if (this.userMapper.selectCountByEmail(user.getEmail())>0) {
//            System.out.println("이메일 중복");
//            return RegisterResult.FAILURE_DUPLICATE_EMAIL;
//        }
        if (!isPasswordValid(user.getPassword())) {
            System.out.println("올바르지 않은 비밀번호");
            return RegisterResult.FAILURE_INVALID_PASSWORD;
        }
        if (!isNameValid(user.getName())) {
            System.out.println("올바르지 않은 이름");
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
        System.out.println("success");
        return RegisterResult.SUCCESS;
    }
}
