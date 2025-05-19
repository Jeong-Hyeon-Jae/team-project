package com.jhj.annual.user.services;

import com.jhj.annual.annual.entities.AnnualEntity;
import com.jhj.annual.user.entities.UserEntity;
import com.jhj.annual.user.mappers.UserMapper;
import com.jhj.annual.user.results.CommonResult;
import com.jhj.annual.user.results.RegisterResult;
import com.jhj.annual.user.results.ResultTuple;
import com.jhj.annual.user.utils.Bcrypt;
import jakarta.jws.soap.SOAPBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        if (dbUserEntity == null || dbUserEntity.getIsDeleted().equals("Y")) {
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
        if (name == null ||
                contactMvno == null ||
                contactFirst == null ||
                contactSecond == null ||
                contactThird == null ||
                !UserService.isNameValid(name) ||
                !UserService.isContactSecondValid(contactSecond) ||
                !UserService.isContactThirdValid(contactThird)
        ) {
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }
        UserEntity dbUser = this.userMapper.selectInfoByName(name);
        if (dbUser == null ||
                !dbUser.getName().equals(name) ||
                !dbUser.getContactSecond().equals(contactSecond) ||
                !dbUser.getContactThird().equals(contactThird)) {
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }
        return ResultTuple.<UserEntity>builder()
                .result(CommonResult.SUCCESS)
                .payload(dbUser)
                .build();
    }

    //비밀번호 바꾸기 위한 인증.
    public ResultTuple<UserEntity> confirmInfo(UserEntity user) {
        // 1. 기본 유효성 검사
        if (user == null ||
                !UserService.isEmailValid(user.getEmail()) ||
                !UserService.isNameValid(user.getName()) ||
                !UserService.isContactSecondValid(user.getContactSecond()) ||
                !UserService.isContactThirdValid(user.getContactThird())) {
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }

        // 2. 사용자 조회
        System.out.println(user.getEmail());
        UserEntity dbUser = this.userMapper.selectByEmail(user.getEmail());
        if (dbUser == null || dbUser.getIsDeleted().equals("Y")) {
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }

        // 3. 연락처 이름 검증 (이메일 외 추가 검증)
        boolean isSameIdentity =
                dbUser.getName().equals(user.getName()) &&
                        dbUser.getContactSecond().equals(user.getContactSecond()) &&
                        dbUser.getContactThird().equals(user.getContactThird());

        if (!isSameIdentity) {
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }

        return ResultTuple.<UserEntity>builder()
                .result(CommonResult.SUCCESS)
                .payload(dbUser)
                .build();
    }

    //비밀번호 바꾸기.
    public ResultTuple<UserEntity> changePassword(UserEntity user) {

        UserEntity dbUser = this.userMapper.selectByEmail(user.getEmail());
        // 4. 기존 비밀번호 재사용 방지
        if (Bcrypt.isMatch(user.getPassword(), dbUser.getPassword())) {
            System.out.println("기존과 동일");
            return ResultTuple.<UserEntity>builder()
                    .result(RegisterResult.FAILURE_DUPLICATE)
                    .build();
        }
        // 5. 비밀번호 변경
        dbUser.setPassword(Bcrypt.encrypt(user.getPassword()));
        dbUser.setModifiedAt(LocalDate.now());

        int updatedRows = this.userMapper.updatePassword(dbUser);
        if (updatedRows < 1) {
            System.out.println(5);
            return ResultTuple.<UserEntity>builder()
                    .result(CommonResult.FAILURE)
                    .build();
        }
        return ResultTuple.<UserEntity>builder()
                .result(CommonResult.SUCCESS)
                .build();
    }


}
