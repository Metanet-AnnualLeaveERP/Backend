package com.meta.ale.mapper;

import com.meta.ale.domain.VcTypeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VcTypeMapper {
    public VcTypeDto findVcTypeByTypeName(String name);
}
