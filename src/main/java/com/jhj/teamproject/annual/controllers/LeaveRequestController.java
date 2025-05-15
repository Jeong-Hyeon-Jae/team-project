package com.jhj.teamproject.annual.controllers;

import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.annual.results.Result;
import com.jhj.teamproject.annual.services.LeaveRequestService;
import com.jhj.teamproject.user.entities.UserEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

 /*       UserEntity user = new UserEntity();
        user.setEmail(email);*/

        Result result = this.leaveRequestService.insert(leaveRequest, email);
        JSONObject res = new JSONObject();
        res.put("result", result.toString().toLowerCase());

        return res.toString();
    }

    @RequestMapping(value = "/request/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getRequestList(@SessionAttribute(value = "email")String email) {
        JSONArray events = new JSONArray();
        JSONObject res = new JSONObject();

        if (email == null) {
            res.put("result", Result.FAILURE.toString().toLowerCase());
            return res.toString();
        }

        List<LeaveRequestEntity> leaveList = this.leaveRequestService.selectByEmail(email);

        for (LeaveRequestEntity i : leaveList) {
            res.put("result", Result.SUCCESS.toString().toLowerCase());
            res.put("title", i.getName()); // js의 기본 달력 출력값이 title로 되어있음 -> 이름으로 변경
            res.put("start", i.getStartDate().toString());
            res.put("end", i.getEndDate().toString());
            res.put("allDay", true);
            res.put("content", i.getContent());
            events.put(res);
        }

        return events.toString();
    }
}
