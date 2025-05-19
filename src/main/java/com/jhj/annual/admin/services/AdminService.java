package com.jhj.annual.admin.services;

import com.jhj.annual.admin.entities.RequestsEntity;
import com.jhj.annual.admin.mappers.AdminMapper;
import com.jhj.annual.admin.results.UpdateResult;
import com.jhj.annual.user.entities.UserEntity;
import jakarta.jws.soap.SOAPBinding;
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
        if (!user.isAdmin()) {
            return UpdateResult.FAILURE;
        }
        if (status.equals("APPROVED")) {
            RequestsEntity request = this.adminMapper.selectRequestById(id);
            if (request != null) {
                this.adminMapper.incrementUsedDays(request.getUserId(), request.getDays());
            }
        }

        return this.adminMapper.updateRequests(user, status, id) > 0 ? UpdateResult.SUCCESS : UpdateResult.FAILURE;
    }

    public UserEntity getRequestByEmail(String email) {
        return this.adminMapper.selectByEmail(email);
    }

}
