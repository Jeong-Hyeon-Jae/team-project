package com.jhj.annual.annual.mappers;

import com.jhj.annual.annual.entities.AnnualEntity;
import com.jhj.annual.annual.entities.AnnualSummaryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnualMapper {
    AnnualSummaryEntity selectNameAndDateByEmail(String email);
}
