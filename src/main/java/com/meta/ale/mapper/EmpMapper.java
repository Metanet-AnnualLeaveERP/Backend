package com.meta.ale.mapper;

import com.meta.ale.domain.EmpDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmpMapper {
    public void insertEmp(EmpDto empDto);
}
