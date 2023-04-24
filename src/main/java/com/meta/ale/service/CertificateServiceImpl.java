package com.meta.ale.service;

import com.meta.ale.domain.*;
import com.meta.ale.mapper.CertificateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateMapper certificateMapper;
    @Override
    public Map<String, Object> getCertList(Criteria cri, Long userId) {
        // Mapper에 들어갈 파라미터 map으로 변환
        HashMap<String, Object> vo = new HashMap<String, Object>();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("userId", userId);

        // 페이징 처리를 위해 map으로 데이터 리턴
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("paging", new PagenationDTO(cri, getCertCount(userId)));
        map.put("certificates", certificateMapper.getCertList(vo));
        return map;
    }

    @Override
    public CertificateDto getCertCompared(Long certId, Long currUserId) {
        // getVcReqCompared와 동일 로직
        CertificateDto dto = certificateMapper.getCert(certId);
        // 관리자면
        if (currUserId == 0){
            return dto;
        }
        if (dto == null) {
            return null;
        }
        EmpDto dbEmp = dto.getEmpDto();

        Long dbUserId = dbEmp.getUserDto().getUserId();
        return currUserId == dbUserId ? dto : null;
    }

    @Override
    public void createCert(CertificateDto dto) {
        certificateMapper.insertCert(dto);
    }


    /* ------------서비스 내부에서 쓸 메소드 -------------- */

    /*휴가 신청 개수 (페이징 처리용)*/
    private int getCertCount(Long userId) {
        return certificateMapper.getCertCount(userId).intValue();
    }

}
