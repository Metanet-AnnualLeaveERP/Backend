package com.meta.ale.mapper;

import com.meta.ale.domain.DeptDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptMapper {
    public DeptDto selectByDeptName(String deptName);
}
