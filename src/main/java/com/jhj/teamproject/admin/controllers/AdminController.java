package com.jhj.teamproject.admin.controllers;

import com.jhj.teamproject.admin.entities.RequestsEntity;
import com.jhj.teamproject.admin.entities.UsersEntity;
import com.jhj.teamproject.admin.results.UpdateResult;
import com.jhj.teamproject.admin.services.AdminService;
import com.jhj.teamproject.user.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @RequestMapping(value = "/list", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchApproval(UserEntity user,
                                @RequestParam(value = "action")String action) {
        UpdateResult result = this.adminService.updateRequests(user, action);
        return null;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getCalendar() {
        return "/admin/admin";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getList() {
        return "/admin/list";
    }

    @RequestMapping(value = "/lists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RequestsEntity[] getLists() {
        return this.adminService.getAllRequests();
    }
}
