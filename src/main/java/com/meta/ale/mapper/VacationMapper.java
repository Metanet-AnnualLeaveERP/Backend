package com.meta.ale.mapper;

import com.meta.ale.domain.VcTypeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VacationMapper {
    int insertVcType(VcTypeDto vcType);
}
