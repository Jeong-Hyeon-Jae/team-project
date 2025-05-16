package com.jhj.teamproject.annual.controllers;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.annual.results.Result;
import com.jhj.teamproject.annual.services.AnnualService;
import com.jhj.teamproject.user.entities.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class AnnualController {
    private final AnnualService annualService;

    @Autowired
    public AnnualController(AnnualService annualService) {
        this.annualService = annualService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getIndex(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {

        return "/annual/index";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET}, produces = MediaType.TEXT_HTML_VALUE)
    public String getLogout(HttpSession session) {
        session.setAttribute("email",null);

        return "redirect:/user/login";

    }

    @RequestMapping(value = "/request/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getRequestInfo(@SessionAttribute(value = "email") String email) {
        return null;
    }

}