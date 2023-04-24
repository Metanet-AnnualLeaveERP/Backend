package com.meta.ale.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.ale.domain.*;
import com.meta.ale.mapper.CancelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CancelServiceImpl implements CancelService {

    private final CancelMapper cancelMapper;

    private final VcReqService vcReqService;
    private final EmpService empService;
    private final VcTypeTotalService totalService;
    private final GrantedVcService gvService;

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
    public CancelDto getCancel(Long cancelId, UserDto userDto) {
        // 현재 로그인한 userId와 reqId로 가져온 휴가 신청의 userId가 동일하지 않으면 null 반환
        CancelDto dto = cancelMapper.getCancel(cancelId);
        Long currUserId = userDto.getUserId();
        String role = userDto.getRole();
        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_MGR")) {
            return dto;
        }
        if (dto != null) {

            EmpDto dbEmp = dto.getVcReqDto().getEmpDto();
            Long dbUserId = dbEmp.getUserDto().getUserId();
            return currUserId == dbUserId ? dto : null;
        }
        return null;
    }

    /*휴가 취소*/
    @Override
    @Transactional
    public void createCancel(CancelDto cancelDto, Long reqId) {
        VcReqDto vcReqDto = vcReqService.getVcReq(reqId);

        // 현재 날짜와 휴가 요청의 시작일을 비교한다.
        Date sysdate = new Date(); // 현재일
        int compare = vcReqDto.getStartDate().compareTo(sysdate);

        // 시작일이 현재일 이후이라면 (아직 안 지남)
        if (compare > 0) {
            cancelDto.setCancelStatus("자동취소");
            /*자동 취소 시 차감일 복구 */
            ObjectMapper objectMapper = new ObjectMapper();
            List<Long> vcType = new ArrayList();
            vcType.add(1L); // 연차
            vcType.add(2L); // 오전반차
            vcType.add(3L); // 오후반차
            // 연차 / 반차가 아닌 경우
            if (!vcType.contains(vcReqDto.getVcTypeDto().getTypeId())) {
                VcTypeTotalDto totalDto = totalService.getVcTotalByTypeAndEmpId(vcReqDto);
                totalDto.setCnt((long) (totalDto.getCnt() + vcReqDto.getReqDays()));
                totalService.updateVcTypeTotalByTotalId(totalDto);
            } else {
                // 반차가 들어올 수도 있어서 연차로 변환
                vcReqDto.getEmpDto().setUserDto(null);
                VcReqDto newVcReqDto = objectMapper.convertValue(vcReqDto, VcReqDto.class);
                newVcReqDto.getVcTypeDto().setTypeId(1L);
                // 올해에 대한 연차 정보 조회
                GrantedVcDto gvDto = gvService.findByExpiredDateAndEmpIdAndTypeId(newVcReqDto);
                // 신청일만큼 Granted_Vc Table의 RemainDays를 복구
                gvDto.setRemainDays(gvDto.getRemainDays() + vcReqDto.getReqDays());
                gvService.updateAnnualCnt(gvDto);
            }
        } // 시작일이 현재일이거나 이후라면 (지났다면)
        else if (compare == 0 || compare < 0) {
            cancelDto.setCancelStatus("대기중");
        }
        vcReqDto.setStatus("취소");
        cancelDto.setVcReqDto(vcReqDto);

        vcReqService.updateVcReqStatus(vcReqDto); // 해당 휴가 요청의 상태를 '취소'로 변경
        cancelMapper.insertCancel(cancelDto); // 휴가 취소 테이블에 추가
    }

    /*휴가취소 승인 / 휴가취소 반려*/
    @Override
    @Transactional
    public boolean approvalCancel(Long cancelId, String status, String comment) {
        CancelDto cancelDto = cancelMapper.getCancel(cancelId);
        if ((status.equals("")) || cancelDto == null) {
            return false;
        }
        cancelDto.setCancelStatus(status);
        cancelDto.setResDate(new Date());
        cancelDto.setResComm(comment);
        // (연차,반차)/나머지 따라 로직변경
        VcReqDto vcReqDto = cancelDto.getVcReqDto();
        Long typeId = vcReqDto.getVcTypeDto().getTypeId();
        List<Long> types = new ArrayList<>();
        types.add(1L); // 연차
        types.add(2L); // 오전반차
        types.add(3L); // 오후반차
        if (status.equals("승인")) {
            if (types.contains(typeId)) {
                typeId = 1L;
                vcReqDto.getVcTypeDto().setTypeId(typeId);
                GrantedVcDto gvDto = gvService.findByExpiredDateAndEmpIdAndTypeId(vcReqDto);
                gvDto.setRemainDays(gvDto.getRemainDays() + vcReqDto.getReqDays());
                gvService.updateAnnualCnt(gvDto);
            } else {
                VcTypeTotalDto total = totalService.getVcTotalByTypeAndEmpId(vcReqDto);
                total.setCnt(total.getCnt() + vcReqDto.getReqDays().longValue());
                totalService.updateVcTypeTotalByTotalId(total);
            }
        }

        return cancelMapper.updateCancelStatus(cancelDto) != 0;

    }

    /*휴가취소 승인 / 휴가취소 반려 관리자 조회*/
    @Override
    public Map<String, Object> getApprovalCancelList(UserDto userDto, Criteria cri) {
        String role = userDto.getRole();
        Long mgrDeptId = null;
        if (role.equals("ROLE_MGR")) {
            Long userId = userDto.getUserId();
            EmpDto managerDto = empService.findEmpByUserId(userId);
            mgrDeptId = managerDto.getDeptDto().getDeptId();
        }
        HashMap<String, Object> vo = new HashMap();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("keyword", cri.getKeyword());
        vo.put("deptId", mgrDeptId);

        int count = getCancelCountByMgr(vo);
        Map<String, Object> map = new HashMap();
        map.put("paging", new PagenationDTO(cri, count));
        map.put("cancel", cancelMapper.getCancelListByMgr(vo));

        return map;
    }

    /*휴가 취소 상태 변경*/
    @Override
    public boolean updateCancelStatus(CancelDto dto) {
        return cancelMapper.updateCancelStatus(dto) != 0 ? true : false;
    }
    /* ------------서비스 내부에서 쓸 메소드 -------------- */

    /*휴가 신청 개수 (페이징 처리용)*/
    private int getCancelCount(Long userId) {
        return cancelMapper.getCancelCount(userId).intValue();
    }

    /*휴가취소 신청 개수 [관리자, 매니저 ] (페이징 처리용)*/
    private int getCancelCountByMgr(HashMap<String, Object> hashMap) {
        return cancelMapper.getCancelCountByMgr(hashMap).intValue();
    }
}
