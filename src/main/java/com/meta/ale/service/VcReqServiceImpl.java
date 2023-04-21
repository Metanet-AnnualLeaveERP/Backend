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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static com.meta.ale.service.EmpServiceImpl.getDatesBetweenTwoDates;

@Service
@RequiredArgsConstructor
public class VcReqServiceImpl implements VcReqService {

    private final VcReqMapper vcReqMapper;

    private final FileService fileService;

    private final EmpService empService;

    private final VcTypeTotalService totalService;

    private final GrantedVcService vcService;

    private final MailService mailService;

    private final DeptService deptService;


    /*휴가 신청 내역 조회*/
    @Override
    public Map<String, Object> getVcReqList(Criteria cri, UserDto user) {
        // Mapper에 들어갈 파라미터 map으로 변환
        Long userId = user.getUserId();
        HashMap<String, Object> vo = new HashMap();
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
    public VcReqDto getVcReqCompared(Long reqId, UserDto userDto) {
        // 현재 로그인한 userId와 reqId로 가져온 휴가 신청의 userId가 동일하지 않으면 null 반환
        VcReqDto dto = vcReqMapper.getVcReq(reqId);
        String role = userDto.getRole();
        Long currUserId = userDto.getUserId();

        if(role.equals("ROLE_ADMIN") || role.equals("ROLE_MGR")){
            return dto;
        }
        if (dto == null)
            return null;
        EmpDto dbEmp = dto.getEmpDto();
        if (dbEmp == null)
            return null;
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
        } else {
            // 반차가 들어올 수도 있어서 연차로 변환
            VcReqDto vcReqDto = objectMapper.convertValue(dto, VcReqDto.class);
            vcReqDto.getVcTypeDto().setTypeId(1L);
            // 올해에 대한 연차 정보 조회
            GrantedVcDto gvDto = vcService.findByExpiredDateAndEmpIdAndTypeId(vcReqDto);
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
        if (status.equals("승인")) {
            mailService.sendToCEmail(vcReq.getEmpDto(), "<MetaNet>휴가를 정상 처리하였습니다.", "휴가신청이 승인되었습니다.", "자사 홈페이지를 통해 확인해주시면 감사하겠습니다.");
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
        if (role.equals("ROLE_MGR")) {
            Long userId = userDto.getUserId();
            EmpDto managerDto = empService.findEmpByUserId(userId);
            managerDeptId = managerDto.getDeptDto().getDeptId();
        }
        vo.put("pageNum", cri.getPageNum());
        vo.put("amount", cri.getAmount());
        vo.put("keyword", cri.getKeyword());
        System.out.println(managerDeptId);
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
    public List<VcReqDto> findMyTeamVacation(UserDto userDto) {
        return vcReqMapper.getVcReqByDept(userDto);
    }

    /*휴가 신청 일자별로 잔여 TO 계산*/
    @Override
    public List<RemainVcTo> calcRemainTOByVcReqs(UserDto userDto) throws Exception {
        Long calcTO = deptService.calculateVcToByDept(userDto.getUserId());
        List<VcReqDto> vcReqDtoList = getEntireReqsByTeam(userDto);

        List<LocalDate> dates = new ArrayList<>();
        List<Long> finalTO = new ArrayList<>();

        System.out.println("========날짜와 TO 배열 계산========");
        for (VcReqDto vcReqDto : vcReqDtoList) {
            // Date 객체를 LocalDate로 변환
            LocalDate startDate = vcReqDto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = vcReqDto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            List<LocalDate> dateBetweenTwoDates = new ArrayList<>(getDatesBetweenTwoDates(startDate, endDate));
            List<Long> calcVcTO = new ArrayList<>();

            // startDate와 endDate의 날짜 개수 차이가 0이면 (휴가 1일 사용 시) 하나만 넣어 준다
            if (dateBetweenTwoDates.size() == 0) {
                calcVcTO.add(calcTO);
            } else {
                // startDate와 endDate의 날짜 개수 만큼 To 배열에 잔여 TO를 넣는다
                for (int j = 0; j < dateBetweenTwoDates.size(); j++) {
                    calcVcTO.add(calcTO);
                }
            }
            System.out.print(dateBetweenTwoDates.toString() + " ");
            System.out.println();
            System.out.print(calcVcTO.toString() + " ");
            System.out.println();

            /* vcReq의 시작일부터 종료일까지 To를 하나씩 차감하는 로직.
             * if dates.contains(날짜) == false -> dates.add , caclTos.add()
             *   else dates.contains(날짜) == true -> index를 구하고 calcTos 해당 index의 값을 -1 한다 */
            System.out.println("========vcReqDto 안의 날짜 하나마다 to 계산하는 반복문 실행========");

            /* 두 날짜 사이의 수가 0인 경우 ( 휴가 1일 사용 시 )
             * forEach문을 돌리지 않고 바로 add 해 준다 */
            if (dateBetweenTwoDates.size() == 0) {
                dates.add(startDate);
                finalTO.add(calcTO - 1);
            } else {
                // vcReqDto 하나마다 to 차감
                for (LocalDate date : dateBetweenTwoDates) {
                    int index;

                    // 날짜 배열에 있으면
                    if (dates.contains(date)) {
                        // index를 찾는다
                        index = dates.indexOf(date);
                        // 해당 index의 TO를 한 개 차감한다
                        Long currTO = finalTO.get(index);
                        finalTO.set(index, currTO - 1);
                    } else {
                        // 날짜 배열에 없으면
                        dates.add((date));
                        // 하나 빠진 값을 넣는다
                        finalTO.add(calcTO - 1);
                    }
                }
            }
        }
        System.out.println("========모든 반복문 계산 끝========");
        System.out.println("최종 날짜 배열 -> " + dates.toString());
        System.out.println("최종 TO 배열 -> " + finalTO.toString());

        System.out.println("======== remainToDtoList 가공 =======");
        List<RemainVcTo> remainVcToList = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            // LocalDate 객체를 ZonedDateTime 객체로 변환
            ZonedDateTime zonedDateTime = dates.get(i).atStartOfDay(ZoneId.systemDefault());
            // ZonedDateTime 객체를 Date 객체로 변환
            Date date = Date.from(zonedDateTime.toInstant());
            remainVcToList.add(new RemainVcTo(date, finalTO.get(i)));
        }
        remainVcToList.forEach(remainVcTo -> System.out.println(remainVcTo.toString()));
        return remainVcToList;
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

    /*파일 업로드*/
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

    /*자신 포함 + 부서 전체의 휴가 신청 리스트*/
    private List<VcReqDto> getEntireReqsByTeam(UserDto userDto) {
        return vcReqMapper.getEntireReqsByTeam(userDto);
    }


}
