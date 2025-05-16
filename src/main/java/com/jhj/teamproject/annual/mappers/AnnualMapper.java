package com.jhj.teamproject.annual.mappers;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnualMapper {
    AnnualEntity selectByIndex();
    public List<AnnualEntity> selectNameAndDateByEmail(String email);
}
