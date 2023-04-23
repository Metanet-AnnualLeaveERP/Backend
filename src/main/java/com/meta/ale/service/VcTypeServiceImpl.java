package com.meta.ale.service;

import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.domain.VcTypeTotalDto;
import com.meta.ale.mapper.VcTypeMapper;
import com.meta.ale.mapper.VcTypeTotalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/* 휴가유형관리 */
@Service
@RequiredArgsConstructor
public class VcTypeServiceImpl implements VcTypeService{

    private final VcTypeMapper vcTypeMapper;

    private final VcTypeTotalMapper vcTypeTotalMapper;


    /* 휴가유형 추가 */
    @Override
    @Transactional
    public void insertVcType(VcTypeDto vcTypeDto) {
        vcTypeMapper.insertVcType(vcTypeDto);
        VcTypeTotalDto vcTypeTotalDto = new VcTypeTotalDto();
        vcTypeTotalDto.setVcTypeDto(vcTypeDto);
        vcTypeTotalMapper.insertVcTypeTotalByEmpIds(vcTypeTotalDto);
    }

    /* 휴가유형 조회 */
    @Override
    public List<VcTypeDto> getListVcType() {
        return vcTypeMapper.getListVcType();
    }

    /* 휴가유형 수정 */
    @Override
    public boolean updateVcType(VcTypeDto vcTypeDto) {
        return vcTypeMapper.updateVcType(vcTypeDto) > 0;
    }

    /* 휴가유형 삭제 */
    @Override
    public boolean deleteVcType(Long typeId) {
        return vcTypeMapper.deleteVcType(typeId) > 0;
    }

    @Override
    public VcTypeDto getVcType(String typeName) {
        return vcTypeMapper.findVcTypeByTypeName(typeName);
    }
}
