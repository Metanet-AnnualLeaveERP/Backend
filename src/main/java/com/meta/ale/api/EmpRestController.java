package com.meta.ale.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.UserDto;
import com.meta.ale.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmpRestController {

    @Autowired
    private EmpService empService;

    @PostMapping("/admin/emp/create")
    public ResponseEntity<?> createEmp(@RequestBody ObjectNode objectNode) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.treeToValue(objectNode.get("userDto"), UserDto.class);
        EmpDto empDto = objectMapper.treeToValue(objectNode.get("empDto"), EmpDto.class);

        if (empService.register(userDto, empDto)) {
            return ResponseEntity.ok().body(empDto); // body(employeeDto) 바꿔야햐ㅏㅁ. 테스트중
        }else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }

    @PutMapping("/admin/emp/modify")
    public ResponseEntity<?> modify(@RequestBody EmpDto empDto) throws Exception {
        if (empService.modifyEmp(empDto)) {
            return ResponseEntity.ok().body(empDto); // body(employeeDto) 바꿔야햐ㅏㅁ. 테스트중
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }

    @GetMapping("/admin/emp/{emp_id}")
    public ResponseEntity<?> getEmpInfo(@PathVariable("emp_id") Long empId) throws Exception {
        EmpDto empDto = empService.getEmpInfo(empId);
        if (empDto == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        } else {
            return ResponseEntity.ok().body(empDto);
        }
    }
}
