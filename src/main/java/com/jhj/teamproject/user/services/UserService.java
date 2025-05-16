package com.jhj.teamproject.user.services;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import com.jhj.teamproject.user.entities.UserEntity;
import com.jhj.teamproject.user.mappers.UserMapper;
import com.jhj.teamproject.user.results.CommonResult;
import com.jhj.teamproject.user.results.RegisterResult;
import com.jhj.teamproject.user.results.ResultTuple;
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

    public static boolean isContactSecondValid(String input) {
        return input != null && input.matches("^(\\d{3,4})$");
    }

    public static boolean isContactThirdValid(String input) {
        return input != null && input.matches("^(\\d{4})$");
    }

    public static boolean isPasswordValid(String input) {
        return input != null && input.matches("^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{8,50})$");
    }

    public static boolean isNameValid(String input) {
        return input != null && input.matches("^([a-zA-Z가-힣]{2,50})$");
    }

    //로그인
    public ResultTuple<UserEntity> login(String email, String password) {
        if (email == null ||
                password == null ||
                !UserService.isEmailValid(email) ||
                !UserService.isPasswordValid(password)) {
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }
        UserEntity dbUserEntity = this.userMapper.selectByEmail(email);
        if (dbUserEntity == null || dbUserEntity.getIsDeleted() == "y") {
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }
        if (!Bcrypt.isMatch(password, dbUserEntity.getPassword())) {
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }
        return ResultTuple.<UserEntity>builder()
                .result(CommonResult.SUCCESS)
                .payload(dbUserEntity)
                .build();
    }

    //회원가입
    public RegisterResult register(UserEntity user) {
        if (user == null
                || user.getContactMvno() == null
                || user.getAddressPostal() == null
                || user.getAddressPrimary() == null
                || user.getAddressSecondary() == null
                || !UserService.isContactSecondValid(user.getContactSecond())
                || !UserService.isContactThirdValid(user.getContactThird())
                || !UserService.isEmailValid(user.getEmail())
                || !UserService.isNameValid(user.getName())
                || !UserService.isPasswordValid(user.getPassword())
        ) {
            return RegisterResult.FAILURE;
        }
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setPassword(Bcrypt.encrypt(user.getPassword()));
        user.setCreatedAt(user.getCreatedAt());
        user.setIsDeleted("N");
        user.setAdmin(false);
        user.setContactMvno(user.getContactMvno());
        user.setContactFirst(user.getContactFirst());
        user.setContactSecond(user.getContactSecond());
        user.setContactThird(user.getContactThird());
        user.setAddressPostal(user.getAddressPostal());
        user.setAddressPrimary(user.getAddressPrimary());
        user.setAddressSecondary(user.getAddressSecondary());

        if (this.userMapper.insertUser(user) == 0) {
            return RegisterResult.FAILURE;
        }
        AnnualEntity annual = new AnnualEntity();
        annual.setUserId(user.getId());
        annual.setTotalDays(totalDays);
        annual.setUsedDays(0);

        if (this.userMapper.insertAnnual(annual) == 0) {
            return RegisterResult.FAILURE;
        }
        return RegisterResult.SUCCESS;
    }

    //아이디 찾기.
    public ResultTuple<UserEntity> findId(String name, String contactMvno, String contactFirst, String contactSecond, String contactThird) {
        UserEntity dbUser = this.userMapper.selectInfoByName(name);
        System.out.println(dbUser.getEmail());
        return ResultTuple.<UserEntity>builder()
                .result(CommonResult.SUCCESS)
                .payload(dbUser)
                .build();
    }

    //비밀번호 찾기.
    public String findPassword(UserEntity user) {
        return null;
    }
}
