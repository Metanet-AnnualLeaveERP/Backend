package com.meta.ale.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.ale.domain.*;
import com.meta.ale.mapper.VcReqMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VcReqServiceImpl implements VcReqService {

    private final VcReqMapper vcReqMapper;

    private final FileService fileService;

    private final EmpService empService;

    private final VcTypeTotalService totalService;

    private final GrantedVcService vcService;

    private final MailService mailService;
    /*휴가 신청 내역 조회*/
    @Override
    public Map<String, Object> getVcReqList(Criteria cri, Long userId) {
        // Mapper에 들어갈 파라미터 map으로 변환
        HashMap<String, Object> vo = new HashMap<String, Object>();
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("userId", userId);

        // 페이징 처리를 위해 map으로 데이터 리턴
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("paging", new PagenationDTO(cri, getVcReqCount(userId)));
        map.put("vcReqs", vcReqMapper.getVcReqList(vo));
        return map;
    }

    /*휴가 신청 내역 상세 (user id 비교)*/
    @Override
    public VcReqDto getVcReqCompared(Long reqId, Long currUserId) {
        // 현재 로그인한 userId와 reqId로 가져온 휴가 신청의 userId가 동일하지 않으면 null 반환
        VcReqDto dto = vcReqMapper.getVcReq(reqId);
        if(currUserId ==0){
            return dto;
        }
        EmpDto dbEmp = dto.getEmpDto();
        Long dbUserId = dbEmp.getUserDto().getUserId();
        return currUserId == dbUserId ? dto : null;
    }

    /*휴가 신청 내역 상세*/
    @Override
    public VcReqDto getVcReq(Long reqId) {
        return vcReqMapper.getVcReq(reqId);
    }


    /*휴가 신청*/
    @Override
    @Transactional
    public void createVcReq(VcReqDto dto, MultipartFile[] uploadFiles) throws Exception {
        //메소드로 빼둠
        dto.setFilePath(fileUpload(uploadFiles));
        dto.setAprvDate(null);

        ObjectMapper objectMapper = new ObjectMapper();

        List<Long> vcType = new ArrayList();
        vcType.add(1L); // 연차
        vcType.add(2L); // 오전반차
        vcType.add(3L); // 오후반차
        // 연차 / 반차가 아닌 경우
        if (!vcType.contains(dto.getVcTypeDto().getTypeId())) {
            VcTypeTotalDto totalDto = totalService.getVcTotalByTypeAndEmpId(dto);
            totalDto.setCnt((long) (totalDto.getCnt() - dto.getReqDays()));
            totalService.updateVcTypeTotalByTotalId(totalDto);
        }else{
            // 반차가 들어올 수도 있어서 연차로 변환
            VcReqDto vcReqDto = objectMapper.convertValue(dto, VcReqDto.class);
            vcReqDto.getVcTypeDto().setTypeId(1L);
            // 올해에 대한 연차 정보 조회
            GrantedVcDto gvDto= vcService.findByExpiredDateAndEmpIdAndTypeId(vcReqDto);
            gvDto.setRemainDays(gvDto.getRemainDays() - dto.getReqDays());
            // 프론트에서 넘길때 같이 넘겨야하는데 잊어먹었을 경우 여기서 한 번 더 체크
            dto.setStatus("자동승인");
            vcService.updateAnnualCnt(gvDto); //Granted_Vc Table의 RemainDays를 차감한다.
       }
        vcReqMapper.insertVcReq(dto);
    }

    /*휴가 신청 상태 변경*/
    @Override
    public boolean updateVcReqStatus(VcReqDto dto) {
        return vcReqMapper.updateVcReqStatus(dto) != 0 ? true : false;
    }

    /*휴가 결재(승인/반려)*/
    @Override
    @Transactional
    public boolean approvalVcRequestStatus(UserDto userDto, Long vcReqId, String status, String comment) {
        VcReqDto vcReq = vcReqMapper.getVcReq(vcReqId);
        Date date = new Date();
        if (vcReq.getReqId() == null) {
            return false;
        }
        //상태변경
        vcReq.setStatus(status);
        vcReq.setAprvDate(date);
        // 반려시 반려 사유
        vcReq.setDeniedComments(comment);
        vcReqMapper.updateVcReqStatus(vcReq);
        if (status.equals("반려")) {
            VcTypeTotalDto vcTotal = totalService.getVcTotalByTypeAndEmpId(vcReq);
            Long cnt = vcTotal.getCnt();
            cnt += vcReq.getReqDays().longValue();
            vcTotal.setCnt(cnt);
            totalService.updateVcTypeTotalByTotalId(vcTotal);
        }
        if(status.equals("승인")){
            mailService.sendToCEmail(vcReq.getEmpDto(),"<MetaNet>휴가를 정상 처리하였습니다.",
                    "휴가신청이 승인되었습니다.",
                    "자사 홈페이지를 통해 확인해주시면 감사하겠습니다.");
        }
        return true;

    }

    /*휴가 결재 내역 조회*/
    @Override
    public Map<String, Object> getApprovalVcRequestList(UserDto userDto, Criteria cri) {

        // 권한으로 팀장 - 매니저 구분 (role이 manger 일 경우 dept를 조회)
        String role = userDto.getRole();
        HashMap<String, Object> vo = new HashMap<String, Object>();
        Long managerDeptId = null;
        //팀장일 경우 자신의 팀원의 내용만 볼 수 있음 ( 관리자의 경우 managerId가 null)
        if (role.equals("ROLE_MANAGER")) {
            Long userId = userDto.getUserId();
            EmpDto managerDto = empService.findEmpByUserId(userId);
            managerDeptId = managerDto.getDeptDto().getDeptId();
        }
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("keyword", cri.getKeyword());
        vo.put("deptId", managerDeptId);

        // 페이징 처리를 위한 전체 count 조회
        int count = approvalVcRequestCountByAdmin(vo);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("paging", new PagenationDTO(cri, count));
        map.put("vcReqs", vcReqMapper.getVcReqListByMgr(vo));

        return map;
    }

    /*팀 휴가 승인된 내역 조회*/
    @Override
    public List<VcReqDto> findMyTeamVacation(Long userId) {
        return vcReqMapper.getVcReqByDept(userId);
    }


    /* ------------서비스 내부에서 쓸 메소드 -------------- */

    /*휴가 신청 개수 (페이징 처리용)*/
    private int getVcReqCount(Long userId) {
        return vcReqMapper.getVcReqCount(userId).intValue();
    }

    /*휴가결재내역 조회 개수(페이징 처리용)*/
    private int approvalVcRequestCountByAdmin(HashMap<String, Object> hashMap) {

        return vcReqMapper.getVcReqCountByMgr(hashMap).intValue();
    }

    private String fileUpload(MultipartFile[] uploadFiles) throws IOException {
        Path filePath = null;
        if (uploadFiles == null || uploadFiles.length == 0) {
            System.out.println(" 파일 업로드 안 했음");
            filePath = null;
        } else {
            // 파일 업로드 요청이 있는 경우 파일 업로드 서비스 호출
            if (uploadFiles.length > 1) {
                filePath = fileService.uploadZip(uploadFiles);
            } else if (uploadFiles.length == 1) {
                filePath = fileService.upload(uploadFiles[0]);
            }
        }
        // 업로드된 파일이 있으면 파일 경로가 생성된다
        if (filePath != null) {
            return filePath.toString();
        }
        return null;
    }

    private List<VcReqDto> getEntireReqsByTeam(UserDto userDto){
        return vcReqMapper.getEntireReqsByTeam(userDto);
    }
}
