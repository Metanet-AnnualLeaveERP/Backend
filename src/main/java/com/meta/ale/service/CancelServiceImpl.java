package com.meta.ale.service;

import com.meta.ale.domain.*;
import com.meta.ale.mapper.CancelMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CancelServiceImpl implements CancelService {

    private CancelMapper cancelMapper;

    private VcReqService vcReqService;

    /*휴가 취소 내역 조회*/
    @Override
    public Map<String, Object> getCancelList(Criteria cri, Long userId) {
        // Mapper에 들어갈 파라미터 map으로 변환
        HashMap<String, Object> vo = new HashMap<String, Object>();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("userId", userId);

        // 페이징 처리를 위해 map으로 데이터 리턴
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("paging", new PagenationDTO(cri, getCancelCount(userId)));
        map.put("cancels", cancelMapper.getCancelList(vo));
        return map;
    }

    /*휴가 취소 내역 상세 조회*/
    @Override
    public CancelDto getCancel(Long cancelId, Long currUserId) {
        // 현재 로그인한 userId와 reqId로 가져온 휴가 신청의 userId가 동일하지 않으면 null 반환
        CancelDto dto = cancelMapper.getCancel(cancelId);
        EmpDto dbEmp = dto.getVcReqDto().getEmpDto();
        Long dbUserId = dbEmp.getUserDto().getUserId();
        return currUserId == dbUserId ? dto : null;
    }

    /*휴가 취소*/
    @Override
    @Transactional
    public void createCancel(CancelDto dto) {
        VcReqDto req = dto.getVcReqDto();
        Date sysdate = new Date(); // 현재일

        // 현재 날짜와 휴가 요청의 시작일을 비교한다.
        int compare = req.getStartDate().compareTo(sysdate);

        // 시작일이 현재일 이전이라면
        if (compare > 0) {
            System.out.println("자동 취소 가능");
            dto.setStatus("자동취소");
        } // 시작일이 현재일이거나 이후라면 (지났다면)
        else if (compare == 0 || compare < 0) {
            System.out.println("취소 요청 날려야 함");
            dto.setStatus("대기중");
        }
        req.setStatus("취소");

        vcReqService.updateVcReqStatus(req);
        cancelMapper.insertCancel(dto);
    }

    /*휴가 취소 결재(승인/반려)*/

    /*휴가취소 승인 / 휴가취소 반려*/

    /*휴가 취소 상태 변경*/
    @Override
    public boolean updateCancelStatus(CancelDto dto) {
        return cancelMapper.updateCancelStatus(dto) != 0 ? true: false;
    }

    /* ------------서비스 내부에서 쓸 메소드 -------------- */

    /*휴가 신청 개수 (페이징 처리용)*/
    private int getCancelCount(Long userId) {
        return cancelMapper.getCancelCount(userId).intValue();
    }
}
