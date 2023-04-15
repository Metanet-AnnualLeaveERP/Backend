package com.meta.ale.mapper;

import com.meta.ale.domain.DeptDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper {
    public DeptDto selectByDeptName(String deptName);
    public DeptDto selectByempId(Long empId);

    List<DeptDto> getListDept();

    int updateDept(DeptDto deptdto);

    void insertDept(DeptDto deptDto);
}
