package com.jhj.teamproject.annual.mappers;

import com.jhj.teamproject.annual.entities.AnnualEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnualMapper {
    AnnualEntity selectByIndex();
}
