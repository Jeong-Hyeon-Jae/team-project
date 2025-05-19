package com.jhj.annual.annual.controllers;

import com.jhj.annual.annual.entities.AnnualEntity;
import com.jhj.annual.annual.entities.AnnualSummaryEntity;
import com.jhj.annual.annual.entities.LeaveRequestEntity;
import com.jhj.annual.annual.results.Result;
import com.jhj.annual.annual.services.AnnualService;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getIndex(@SessionAttribute(value = "email") String email,
                           Model model) {

        AnnualSummaryEntity result = this.annualService.selectAny(email);
        model.addAttribute("result", result);

        return "/annual/index";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET}, produces = MediaType.TEXT_HTML_VALUE)
    public String getLogout(HttpSession session) {
        session.invalidate();

        return "redirect:/user/login";
    }

/*
    @RequestMapping(value = "/request/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRequestInfo(@SessionAttribute(value = "email") String email,
                                 Model model) {


        return "user/index";
    }
*/

}