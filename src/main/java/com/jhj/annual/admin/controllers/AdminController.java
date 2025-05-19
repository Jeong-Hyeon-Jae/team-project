package com.jhj.annual.admin.controllers;

import com.jhj.annual.admin.entities.RequestsEntity;
import com.jhj.annual.admin.results.UpdateResult;
import com.jhj.annual.admin.services.AdminService;
import com.jhj.annual.user.entities.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String patchList(@RequestParam(value = "id") int id,
                            @RequestParam(value = "action") String status,
                            @SessionAttribute(value = "email", required = false) String signedUser) {
        UpdateResult result = this.adminService.updateRequests(signedUser, status, id);
        System.out.println(result);
        if (result == UpdateResult.FAILURE) {
            JSONObject response = new JSONObject();
            response.put("result", result.toString().toUpperCase());
            System.out.println(result);
            return response.toString();
        }
        JSONObject response = new JSONObject();
        response.put("result", result.name().toUpperCase());
        return response.toString();
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.TEXT_HTML_VALUE)
    public String getLogout(HttpSession session) {
        session.setAttribute("email", null);
        System.out.println("로그아웃");
        return "redirect:/user/login";

    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getAdmin(@SessionAttribute(value = "email", required = false) String email,
                           Model model) {
        if (email != null) {
            UserEntity user = this.adminService.getRequestByEmail(email);
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        return "/admin/admin";
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getList(@SessionAttribute(value = "email", required = false) String email,
                          Model model, HttpSession session) {

        if (email != null) {
            UserEntity user = this.adminService.getRequestByEmail(email);
            if (user != null) {
                if (user.isAdmin()) {
                    model.addAttribute("user", user);
                    return "/admin/list";
                }
                model.addAttribute("user", user);
            }
        }
        return "redirect:/user/login";
    }


    @RequestMapping(value = "/lists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RequestsEntity[] getLists(@RequestParam(value = "sort", defaultValue = "desc") String sort) {
        if (sort != null) {
            switch (sort) {
                case "desc":
                    sort = "desc";
                    break;
                case "asc":
                    sort = "asc";
                    break;
                default:
                    sort = "desc";
                    break;
            }
        }
        return this.adminService.getAllRequests(sort);
    }
}
