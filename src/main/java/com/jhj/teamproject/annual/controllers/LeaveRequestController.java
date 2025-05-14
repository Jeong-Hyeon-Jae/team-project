package com.jhj.teamproject.annual.controllers;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.annual.results.LeaveResult;
import com.jhj.teamproject.annual.services.LeaveRequestService;
import com.jhj.teamproject.user.entities.UserEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping(value = "/")
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @RequestMapping(value="/request", method = RequestMethod.POST, produces =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRequest(LeaveRequestEntity leaveRequest,
                              @SessionAttribute(value = "email")String email) {

        UserEntity user = new UserEntity();
        user.setEmail(email);

        LeaveResult result = this.leaveRequestService.insert(leaveRequest, user.getEmail());
        JSONObject res = new JSONObject();
        res.put("result", result.toString().toLowerCase());

        return res.toString();
    }
}
