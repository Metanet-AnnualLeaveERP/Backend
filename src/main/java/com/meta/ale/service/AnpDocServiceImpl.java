package com.meta.ale.service;

import com.meta.ale.domain.*;
import com.meta.ale.mapper.AnpDocMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnpDocServiceImpl implements AnpDocService {

    private final AnpDocMapper anpDocMapper;
    private final GrantedVcService gvService;

    @Override
    @Transactional
    public void insertAnpDocScheduler() throws Exception {
        // 연차 촉진문서를 날리기위해 조회
        List<GrantedVcDto> grantedVcDtoList = gvService.findPromoteAnnualLeave();

        if (grantedVcDtoList.size() != 0) {
            AnpDocDto anpDocDto = new AnpDocDto();
            Date occurDate = new Date();

            for (GrantedVcDto grantedVcDto : grantedVcDtoList) {
                int totalAnv = grantedVcDto.getVcDays().intValue();
                int remainAnv = grantedVcDto.getRemainDays().intValue();
                int usedAnv = totalAnv - remainAnv;
                EmpDto empDto = grantedVcDto.getEmpDto();
                Date annualOccurDate = grantedVcDto.getGrantedDate();

                anpDocDto.setAll(empDto, totalAnv, occurDate, remainAnv, usedAnv, annualOccurDate);

                anpDocMapper.insertAnpDocMapper(anpDocDto);
            }

        } else {
            System.out.println("연차촉진 날릴 사람 없음.");
        }
    }

    // 수동으로 리스트에서 연차촉진문서 생성시 사용 서바스
    @Override
    public void insertAnpDocManually(AnpDocDto anpDocDto) throws Exception {
        anpDocMapper.insertAnpDocMapper(anpDocDto);

    }

    @Override
    public Map<String, Object> getListAnpDoc(Criteria criteria) {
        HashMap<String, Object> dto = new HashMap<>();
        dto.put("pageNum", criteria.getPageNum());
        dto.put("amount", criteria.getAmount());

        Map<String, Object> map = new HashMap<>();
        map.put("paging", new PagenationDTO(criteria, getGrantedVcCount()));
        map.put("anpDocs", anpDocMapper.getListAnpDoc(dto));
        return map;
    }

    @Override
    public boolean deleteAnpDoc(Long docId) {

        int result = anpDocMapper.deleteAnpDoc(docId);
        return result != 0;
    }

    @Override
    public AnpDocDto getAnpDoc(Long docId) {
        return anpDocMapper.getAnpDoc(docId);
    }

    private int getGrantedVcCount(){
        return anpDocMapper.getAnpDocCount().intValue();
    }

}
