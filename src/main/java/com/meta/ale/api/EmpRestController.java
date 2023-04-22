package com.meta.ale.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.service.EmpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "사원 관리", description = "사내 사원 관련 api")
public class EmpRestController {

    private final EmpService empService;

    // 사용자 계정 생성
    @PostMapping("/admin/emp/create")
    @ApiOperation("사원 계정 생성 api")
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
    @ApiOperation("관리자용 사용자 계정 수정 (ex: 부서 직책 수정) api")
    public ResponseEntity<?> modify(@RequestBody EmpDto empDto) throws Exception {
        if (empService.modifyEmp(empDto)) {
            return ResponseEntity.ok().body(empDto); // body(employeeDto) 바꿔야햐ㅏㅁ. 테스트중
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }

    // 사원 상세 내역 조회
    @GetMapping("/emp/{emp_id}")
    @ApiOperation("사원 상세 내역 조회 api")
    public EmpDto getEmpInfo(@PathVariable("emp_id") Long empId) throws Exception {
        return empService.getEmpInfo(empId);
    }

    // 사원 내역 조회 (paging)
    @GetMapping("/admin/emp")
    @ApiOperation("사원 내역 조회 api")
    public Map<String, Object> getEmpList(@RequestParam(required = false, defaultValue = "all") String keyword,
                                          Criteria criteria) throws Exception {
        criteria.setKeyword(keyword);
        return empService.getEmpList(criteria);

    }

    // 팀원 사용자 계정 수정
    // 비밀번호, 개인 이메일 수정
    @PutMapping("/emp/modify")
    @ApiOperation("팀원 사용자 계정 수정 (ex: pwd, 개인 email) api")
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
    @ApiOperation("상사 정보 조회 api")
    public ResponseEntity managerInfo(@PathVariable(value = "mgr_id") Long mgrId) {
        EmpDto empDto = empService.getEmpByMgrId(mgrId);
        return ResponseEntity.ok().body(empDto);
    }

    //부서정보로 Emp 조회
    @GetMapping("/admin/dept-emp-info/{deptId}")
    @ApiOperation("부서 정보로 사원 정보 조회 api")
    public List<EmpDto> selectEmpListByDeptId(@PathVariable(value = "deptId") Long deptId){
//        List<EmpDto> empList = empService.selectListByDeptId(deptId);
        return empService.selectListByDeptId(deptId);
    }

    // userId로 emp 정보 리턴
    @GetMapping("/emp/my-info")
    @ApiOperation("유저정보로 사원 정보 조회 api")
    public ResponseEntity myInfo(@AuthenticationPrincipal UserDto userDto) {
        Long userId = userDto.getUserId();
        EmpDto empDto = empService.findEmpByUserId(userId);
        return ResponseEntity.ok().body(empDto);
    }

}
