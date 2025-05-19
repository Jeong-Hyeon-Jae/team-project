package com.jhj.teamproject.annual.services;

import com.jhj.teamproject.annual.entities.AnnualSummaryEntity;
import com.jhj.teamproject.annual.mappers.AnnualMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnnualService {
    private final AnnualMapper annualMapper;

    @Autowired
    public AnnualService(AnnualMapper annualMapper) {
        this.annualMapper = annualMapper;
    }

    public AnnualSummaryEntity selectAny(String email) {
        return this.annualMapper.selectNameAndDateByEmail(email);
    }
}
