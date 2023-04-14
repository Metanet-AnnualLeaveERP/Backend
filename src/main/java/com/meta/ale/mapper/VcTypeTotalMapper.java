package com.meta.ale.mapper;

import com.meta.ale.domain.VcTypeTotalDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcTypeTotalMapper {
    void insertVcTypeTotal(VcTypeTotalDto vcTypeTotalDto);

    int plusVcTypeTotal(VcTypeTotalDto vcTypeTotalDto);

    int minusVcTypeTotal(VcTypeTotalDto vcTypeTotalDto);

    int updateVcTypeTotal(VcTypeTotalDto vcTypeTotalDto);

    void insertVcTypeTotalByEmpIds(VcTypeTotalDto vcTypeTotalDto);
}
