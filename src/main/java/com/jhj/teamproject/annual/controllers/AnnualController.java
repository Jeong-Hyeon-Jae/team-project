package com.jhj.teamproject.annual.controllers;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class AnnualController {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getIndex(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {

        return "/annual/index";
    }

    @RequestMapping(value="/request", method = RequestMethod.POST, produces =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRequest(LeaveRequestEntity leaveRequest) {


        return null;
    }
}
