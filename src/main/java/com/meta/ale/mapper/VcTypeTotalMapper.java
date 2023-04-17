package com.meta.ale.mapper;

import com.meta.ale.domain.VcReqDto;
import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.domain.VcTypeTotalDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcTypeTotalMapper {
    void insertVcTypeTotal(VcTypeTotalDto vcTypeTotalDto);

    int plusVcTypeTotal(VcTypeTotalDto vcTypeTotalDto);

    int minusVcTypeTotal(VcTypeTotalDto vcTypeTotalDto);

    int updateVcTypeTotal(VcTypeTotalDto vcTypeTotalDto);
    int updateVcTypeTotalByTotalId(VcTypeTotalDto vcTypeTotalDto);

    void insertVcTypeTotalByEmpIds(VcTypeTotalDto vcTypeTotalDto);

    VcTypeTotalDto getVcTotalByTypeAndEmpId(VcReqDto vcReqDto);

    List<VcTypeTotalDto> findAllMyVacation(Long empId);
}
