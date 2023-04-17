package com.meta.ale.mapper;

import com.meta.ale.domain.VcTypeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcTypeMapper {

    int insertVcType(VcTypeDto vcTypeDto);

    List<VcTypeDto> getListVcType();

    int updateVcType(VcTypeDto vcTypeDto);

    int deleteVcType(Long typeId);

    public VcTypeDto findVcTypeByTypeName(String typeName);

    VcTypeDto findVcTypeDtoByTypeId(Long typeId);
}
