package com.jhj.teamproject.user.controllers;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import com.jhj.teamproject.user.entities.UserEntity;
import com.jhj.teamproject.user.results.FindResult;
import com.jhj.teamproject.user.results.LoginResult;
import com.jhj.teamproject.user.results.RegisterResult;
import com.jhj.teamproject.user.services.UserService;
import jakarta.servlet.http.HttpSession;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        LoginResult result = this.userService.login(email, password);
        if (result == LoginResult.SUCCESS) {
            session.setAttribute("email", email);
        }
        JSONObject response = new JSONObject();
        response.put("result", result.toString().toLowerCase());
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
    @RequestMapping(value = "/findInfo", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getFindInfo(){
        return "user/findInfo";
    }

    @RequestMapping(value = "/findInfo/findId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postFindId(UserEntity user){
        return null;
    }
    @RequestMapping(value = "/findInfo/findPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postFindPassword(@RequestParam(value = "email")String email, @RequestParam(value = "name") String name){

        return null;
    }
}
