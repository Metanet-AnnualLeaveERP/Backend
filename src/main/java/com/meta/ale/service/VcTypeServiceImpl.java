package com.meta.ale.service;

import com.meta.ale.domain.VcTypeDto;
import com.meta.ale.mapper.VcTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/* 휴가유형관리 */
@Service
public class VcTypeServiceImpl implements VcTypeService{

    @Autowired
    VcTypeMapper vcTypeMapper;

    /* 휴가유형 추가 */
    @Override
    public boolean insertVcType(VcTypeDto vcTypeDto) {
        int result = vcTypeMapper.insertVcType(vcTypeDto);
        if (result == 0) {
            return false;
        }
        return result == 1;
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

    @Override
    public boolean deleteVcType(Long typeId) {
        int result = vcTypeMapper.deleteVcType(typeId);
        if(result == 0){
            return false;
        }
        return result == 1;
    }

    /* 휴가유형 삭제 */

}
