package com.jhj.teamproject.admin.services;

import com.jhj.teamproject.admin.entities.RequestsEntity;
import com.jhj.teamproject.admin.entities.UsersEntity;
import com.jhj.teamproject.admin.mappers.AdminMapper;
import com.jhj.teamproject.admin.results.UpdateResult;
import com.jhj.teamproject.user.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public RequestsEntity[] getAllRequests() {
        return this.adminMapper.AllRequests();
    }

    public UpdateResult updateRequests(UserEntity user, String action) {
        if (action == null || user.getId() <= 0) {
            return UpdateResult.FAILURE;
        }
        String status;
        switch (action) {
            case "승인":
                status = "APPROVED";
                break;
            case "취소":
                status = "REJECTED";
                break;
            default:
                return UpdateResult.FAILURE;
        }
        return this.adminMapper.updateRequests(user ,status) > 0 ? UpdateResult.SUCCESS : UpdateResult.FAILURE;
    }
}
