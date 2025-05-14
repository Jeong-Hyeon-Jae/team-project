package com.jhj.teamproject.user.controllers;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import com.jhj.teamproject.user.entities.UserEntity;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getLogin(@SessionAttribute(value = "signedUser", required = false) UserEntity user) {
        if (user == null) {
            System.out.println("로그인 실패");
            return "/user/login";
        }
        System.out.println("로그인 성공");
        return "/annual/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postLogin(@RequestParam(value = "email") String email, @RequestParam(value="password") String password
            , HttpSession session) {
        LoginResult result = this.userService.login(email,password);
        if (result == LoginResult.SUCCESS) {
            session.setAttribute("email", email);
            session.setAttribute("password", password);
        }
        JSONObject response = new JSONObject();
        response.put("result", result.toString().toLowerCase());
        return response.toString();
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getRegister() {
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRegister(UserEntity user, AnnualEntity annual) {
        System.out.println("postRegister");
        RegisterResult result = this.userService.register(user);
        JSONObject response = new JSONObject();
        response.put("result", result.toString().toLowerCase());
        return response.toString();
    }
}
