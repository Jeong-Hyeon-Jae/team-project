package com.jhj.teamproject.annual.services;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import com.jhj.teamproject.annual.entities.LeaveRequestEntity;
import com.jhj.teamproject.annual.mappers.AnnualMapper;
import com.jhj.teamproject.annual.mappers.LeaveRequestMapper;
import com.jhj.teamproject.annual.results.Result;
import com.jhj.teamproject.user.entities.UserEntity;
import com.jhj.teamproject.user.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnualService {
    private final AnnualMapper annualMapper;

    @Autowired
    public AnnualService(AnnualMapper annualMapper) {
        this.annualMapper = annualMapper;
    }

    public List<AnnualEntity> selectAny(String email) {
        return this.annualMapper.selectNameAndDateByEmail(email);
    }
}
