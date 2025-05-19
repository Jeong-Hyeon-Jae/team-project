package com.jhj.annual.user.controllers;

import com.jhj.annual.user.entities.UserEntity;
import com.jhj.annual.user.results.CommonResult;
import com.jhj.annual.user.results.RegisterResult;
import com.jhj.annual.user.results.ResultTuple;
import com.jhj.annual.user.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //로그인
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getLogin(@SessionAttribute(value = "email", required = false) String email) {
        if (email == null) {
            System.out.println("로그인 실패");
            return "/user/login";
        }
        System.out.println("로그인 성공");
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postLogin(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password
            , HttpSession session) {
        ResultTuple<UserEntity> result = this.userService.login(email, password);
        if (result.getResult() == CommonResult.SUCCESS) {
            session.setAttribute("email", email);
        }
        JSONObject response = new JSONObject();
        response.put("result", result.getResult().toStringLower());
        response.put("admin", result.getPayload() != null && result.getPayload().isAdmin());
        return response.toString();
    }

    //회원가입
    @RequestMapping(value = "/register", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getRegister() {
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRegister(UserEntity user) {
        RegisterResult result = this.userService.register(user);
        JSONObject response = new JSONObject();
        response.put("result", result.toString().toLowerCase());
        return response.toString();
    }

    //계정찾기
    @RequestMapping(value = "/find/find-info", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getFindInfo() {
        return "user/find/find-info";
    }

    //아이디 찾기
    @RequestMapping(value = "/find/find-id", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getFindId() {
        return "user/find/find-id";
    }

    @RequestMapping(value = "/find/find-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postFindId(@RequestParam(value = "name") String name,
                             @RequestParam(value = "contactMvno") String contactMvno,
                             @RequestParam(value = "contactFirst") String contactFirst,
                             @RequestParam(value = "contactSecond") String contactSecond,
                             @RequestParam(value = "contactThird") String contactThird) {
        ResultTuple<UserEntity> resultTuple = this.userService.findId(name, contactMvno, contactFirst, contactSecond, contactThird);
        JSONObject response = new JSONObject();
        response.put("result", resultTuple.getResult().toStringLower());
        if (resultTuple.getResult() == CommonResult.SUCCESS && resultTuple.getPayload() != null) {
            response.put("findEmail", resultTuple.getPayload().getEmail());
        }
        return response.toString();
    }

    //비밀번호 변경
    @RequestMapping(value = "/find/change-password", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getFindPassword() {
        return "user/find/change-password";
    }

    @RequestMapping(value = "/find/change-password", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchChangePassword(UserEntity user) {
        ResultTuple<UserEntity> resultTuple = this.userService.changePassword(user);
        JSONObject response = new JSONObject();
        response.put("result", resultTuple.getResult().toStringLower());
        return response.toString();
    }

    @RequestMapping(value = "/find/change-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postChangePassword(UserEntity user) {
        ResultTuple<UserEntity> resultTuple = this.userService.confirmInfo(user);
        JSONObject response = new JSONObject();
        response.put("result", resultTuple.getResult().toStringLower());
        System.out.println(resultTuple.getResult().toStringLower());
        return response.toString();
    }


}
