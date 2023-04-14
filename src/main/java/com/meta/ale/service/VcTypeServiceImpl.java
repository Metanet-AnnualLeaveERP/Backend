package com.meta.ale.service;

import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.domain.VcTypeTotalDto;
import com.meta.ale.mapper.VcTypeMapper;
import com.meta.ale.mapper.VcTypeTotalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/* 휴가유형관리 */
@Service
public class VcTypeServiceImpl implements VcTypeService{

    @Autowired
    VcTypeMapper vcTypeMapper;

    @Autowired
    VcTypeTotalMapper vcTypeTotalMapper;


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
        int result = vcTypeMapper.updateVcType(vcTypeDto);
        if(result == 0){
            return false;
        }
        return result == 1;
    }

    /* 휴가유형 삭제 */
    @Override
    public boolean deleteVcType(Long typeId) {
        int result = vcTypeMapper.deleteVcType(typeId);
        if(result == 0){
            return false;
        }
        return result == 1;
    }



}
