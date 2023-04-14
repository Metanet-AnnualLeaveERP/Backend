package com.meta.ale.service;

import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.mapper.VcTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VcTypeServiceImpl implements VcTypeService{
    private final VcTypeMapper vcTypeMapper;

    @Override
    public VcTypeDto getVcType(String typeName) {
        return vcTypeMapper.findVcTypeByTypeName(typeName);
    }
}
