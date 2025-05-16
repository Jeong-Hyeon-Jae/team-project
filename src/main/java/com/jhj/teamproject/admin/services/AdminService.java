package com.jhj.teamproject.admin.services;

import com.jhj.teamproject.admin.entities.RequestsEntity;
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

    public RequestsEntity[] getAllRequests(String sort) {
        return this.adminMapper.AllRequests(sort);
    }

    public UpdateResult updateRequests(String signedUser, String status, int id) {
        if (signedUser == null || status == null || id <= 0) {
            return UpdateResult.FAILURE;
        };
        if (!status.equals("APPROVED") && !status.equals("REJECTED")) {
            return UpdateResult.FAILURE;
        }
        UserEntity user = this.adminMapper.selectByEmail(signedUser);
        if (user == null) {
            return UpdateResult.FAILURE;
        }

        return this.adminMapper.updateRequests(user, status, id) > 0 ? UpdateResult.SUCCESS : UpdateResult.FAILURE;
    }

    public UserEntity getRequestByEmail(String email) {
        return this.adminMapper.selectByEmail(email);
    }
}
