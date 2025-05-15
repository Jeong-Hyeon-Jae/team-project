package com.jhj.teamproject.annual.controllers;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.user.entities.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
public class AnnualController {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getIndex(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {

        return "/annual/index";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET}, produces = MediaType.TEXT_HTML_VALUE)
    public String getLogout(HttpSession session) {
        session.setAttribute("email",null);

        return "redirect:/user/login";

    }

}