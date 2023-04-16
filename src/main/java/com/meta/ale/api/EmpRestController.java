package com.meta.ale.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmpRestController {

    private final EmpService empService;

    // 사용자 계정 생성
    @PostMapping("/admin/emp/create")
    public ResponseEntity<?> createEmp(@RequestBody ObjectNode objectNode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.treeToValue(objectNode.get("userDto"), UserDto.class);
        EmpDto empDto = objectMapper.treeToValue(objectNode.get("empDto"), EmpDto.class);

        if (empService.register(userDto, empDto)) {
            return ResponseEntity.ok().body(empDto); // body(employeeDto) 바꿔야햐ㅏㅁ. 테스트중
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }

    // 관리자 사용자 계정 수정
    // 부서, 직책 수정
    @PutMapping("/admin/emp/modify")
    public ResponseEntity<?> modify(@RequestBody EmpDto empDto) throws Exception {
        if (empService.modifyEmp(empDto)) {
            return ResponseEntity.ok().body(empDto); // body(employeeDto) 바꿔야햐ㅏㅁ. 테스트중
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }

    // 사원 상세 내역 조회
    @GetMapping("/admin/emp/{emp_id}")
    public EmpDto getEmpInfo(@PathVariable("emp_id") Long empId) throws Exception {
        return empService.getEmpInfo(empId);
    }

    // 사원 내역 조회 (paging)
    @GetMapping("/admin/emp")
    public Map<String, Object> getEmpList(@RequestParam(required = false, defaultValue = "all") String keyword,
                                          Criteria criteria) throws Exception {
        criteria.setKeyword(keyword);
        return empService.getEmpList(criteria);

    }

    // 팀원 사용자 계정 수정
    // 비밀번호, 개인 이메일 수정
    @PutMapping("/emp/modify")
    public ResponseEntity<?> modifyInfo(@RequestBody ObjectNode objectNode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.treeToValue(objectNode.get("userDto"), UserDto.class);
        EmpDto empDto = objectMapper.treeToValue(objectNode.get("empDto"), EmpDto.class);

        if (empService.modifyInfo(userDto, empDto)) {
            return ResponseEntity.ok().body(empDto);
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }

    // 상사 정보 조회
    @GetMapping("/emp/manager-info/{mgr_id}")
    public ResponseEntity managerInfo(@PathVariable(value = "mgr_id") Long mgrId) {
        EmpDto empDto = empService.getEmpByMgrId(mgrId);
        return ResponseEntity.ok().body(empDto);
    }

    // userId로 emp 정보 리턴
    @GetMapping("/emp/my-info")
    public ResponseEntity myInfo(@AuthenticationPrincipal UserDto userDto) {
        Long userId = userDto.getUserId();
        EmpDto empDto = empService.findEmpByUserId(userId);
        return ResponseEntity.ok().body(empDto);
    }
}
