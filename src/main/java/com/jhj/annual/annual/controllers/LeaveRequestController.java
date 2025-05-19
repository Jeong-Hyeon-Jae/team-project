package com.jhj.annual.annual.controllers;

import com.jhj.annual.annual.entities.LeaveRequestEntity;
import com.jhj.annual.annual.results.Result;
import com.jhj.annual.annual.services.LeaveRequestService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        JSONArray event = new JSONArray();

        if (email == null) {
            event.put(Result.FAILURE.toString().toLowerCase());
            return event.toString();
        }

        List<LeaveRequestEntity> leaveList = this.leaveRequestService.selectByEmail(email);

        for (LeaveRequestEntity i : leaveList) {
            LocalDate start = i.getStartDate();
            LocalDate end = i.getEndDate();
            LocalDate current = start;

            while (!current.isAfter(end)) {
                DayOfWeek day = current.getDayOfWeek();
                if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                    // 외부에서 인스턴스 생성시 다른 사용자의 요청이 들어오면 중첩되어 날라감
                    JSONObject events = new JSONObject();
                    events.put("id", i.getUserId());
                    events.put("title", i.getName());
                    // start.toString 이나 end.plusDays를 하면 반복문에 고정된 값이 출력
                    events.put("start", current.toString());
                    // FullCalendar에서는 +1을 해야 하루 표시
                    // ex) 19일~20일로 제출하면 current.toString()으로 응답결과를 보내면 19일만 찍힘
                    events.put("end", current.plusDays(1).toString());
                    events.put("allDay", true);
                    events.put("content", i.getContent());
                    events.put("status", i.getStatus());
                    // category를 추가하고 allDay Key로 false를 넣으면 반차
                    /*events.put("category", i.getCategory());*/
                    events.put("result", Result.SUCCESS.toString().toLowerCase());
                    event.put(events);
                }
                current = current.plusDays(1);
            }
        }

        return event.toString();
    }

    // SRP(단일 책임 원칙)에 어긋남
    @RequestMapping(value = "/request/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getRequestListAll(@SessionAttribute(value = "email")String email) {
        JSONArray event = new JSONArray();
        System.out.println("요청 들어옴 post");
        if (email == null) {
            event.put(Result.FAILURE.toString());
            return event.toString();
        }

        List<LeaveRequestEntity> leaveList = this.leaveRequestService.selectByEmail(null);

        for (LeaveRequestEntity i : leaveList) {
            LocalDate start = i.getStartDate();
            LocalDate end = i.getEndDate();
            LocalDate current = start;

            while (!current.isAfter(end)) {
                DayOfWeek day = current.getDayOfWeek();
                if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                    JSONObject events = new JSONObject();
                    events.put("id", i.getUserId());
                    events.put("title", i.getName());
                    events.put("start", current.toString());
                    events.put("end", current.plusDays(1).toString());
                    events.put("allDay", true);
                    events.put("content", i.getContent());
                    events.put("status", i.getStatus());
                    events.put("result", Result.SUCCESS.toString().toLowerCase());
                    event.put(events);
                }
                current = current.plusDays(1);
            }
        }

        return event.toString();
    }
}
