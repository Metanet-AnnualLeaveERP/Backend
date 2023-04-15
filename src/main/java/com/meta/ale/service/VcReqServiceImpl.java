package com.meta.ale.service;

import com.meta.ale.domain.*;
import com.meta.ale.mapper.VcReqMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VcReqServiceImpl implements VcReqService {

    private final VcReqMapper vcReqMapper;

    private final FileService fileService;

    private final EmpService empService;

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
    public void createVcReq(VcReqDto dto, MultipartFile[] uploadFiles) throws IOException {
        System.out.println("-- 휴가 신청 서비스 --");
//        System.out.println("uploadFiles 서비스로 넘어온 개수 = " + uploadFiles.length);

        Path filePath = null;

        if (uploadFiles == null) {
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

        System.out.println("80line");
        System.out.println(filePath);

        // 업로드된 파일이 있으면 파일 경로가 생성된다
        if (filePath != null) {
            dto.setFilePath(filePath.toString());
        }
        dto.setAprvDate(null);
        // emp 문제 수정한 후 주석 해제
//        vcReqMapper.insertVcReq(dto);
    }

    /*휴가 신청 상태 변경*/
    @Override
    public boolean updateVcReqStatus(VcReqDto dto) {
        return vcReqMapper.updateVcReqStatus(dto) != 0 ? true : false;
    }

    /*휴가 결재(승인/반려)*/
    @Override
    public void approvalVcRequestStatus(String role, Long vcReqId, String status) {
//        보류
        VcReqDto vcReq = new VcReqDto();
        vcReq.setReqId(vcReqId);
        vcReq.setStatus(status);
        vcReqMapper.updateVcReqStatus(vcReq);
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
        System.out.println(cri.getKeyword());
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("keyword", cri.getKeyword());
        vo.put("deptId", managerDeptId);

        // 페이징 처리를 위한 전체 count 조회
        int count = approvalVcRequestCountByAdmin(vo);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("paging", new PagenationDTO(cri, count));
        System.out.println(count);
        PagenationDTO pg = (PagenationDTO) map.get("paging");
        System.out.println(pg.getTotal());
        map.put("vcReqs", vcReqMapper.getVcReqListByMgr(vo));

        return map;
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

    ;
}
