package com.jhj.teamproject.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jhj.teamproject.entities.UserEntity;
import com.jhj.teamproject.results.user.RegisterResult;
import com.jhj.teamproject.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getRegister() {

        return "user/register";
    }

    @RequestMapping (value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRegister(UserEntity user) {
        RegisterResult register = this.userService.register(user);
        JSONObject res = new JSONObject();
        res.put("result", register.toString().toLowerCase());

        return res.toString();
    }
}
