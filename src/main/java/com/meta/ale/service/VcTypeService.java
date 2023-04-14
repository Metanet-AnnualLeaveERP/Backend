package com.meta.ale.service;

import com.meta.ale.domain.VcTypeDto;

import java.util.List;
/* 휴가유형관리 */
public interface VcTypeService {

    /* 휴가유형 추가 */
    void insertVcType(VcTypeDto vcTypeDto);

    /* 휴가유형 조회 */
    List<VcTypeDto> getListVcType();

    /* 휴가유형 수정 */
    boolean updateVcType(VcTypeDto vcTypeDto);

    /* 휴가유형 삭제 */
    boolean deleteVcType(Long typeId);

    public VcTypeDto getVcType(String typeName);
}
