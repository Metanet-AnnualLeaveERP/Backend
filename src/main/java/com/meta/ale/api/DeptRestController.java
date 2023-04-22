package com.meta.ale.api;

import com.meta.ale.domain.DeptDto;
import com.meta.ale.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
@Api(tags = "부서",description = "부서정보 관련 api")
public class DeptRestController {

    private final DeptService deptService;

    /* 부서조회 */
    @GetMapping("/dept")
    @ApiOperation("부서 조회 api")
    public ResponseEntity<List<DeptDto>> getListDept() {
        List<DeptDto> deptList = deptService.getListDept();
        return new ResponseEntity<>(deptList, HttpStatus.OK);
    }

    /* 부서생성 */
    @PostMapping("/dept")
    @ApiOperation("부서 생성 api")
    public ResponseEntity<String> insertDept(@RequestBody DeptDto deptDto) {
        try {
            deptService.insertDept(deptDto);
            return new ResponseEntity<>("DATA INSERT SUCCESS", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 부서수정 */
    @PutMapping("/dept/{deptId}")
    @ApiOperation("부서 수정 api")
    public ResponseEntity<String> updateDept(
            @PathVariable("deptId") Long deptId, @RequestBody DeptDto deptDto) {
        deptDto.setDeptId(deptId);
        boolean result = deptService.updateDept(deptDto);
        if (result) {
            return new ResponseEntity<>("UPDATE SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
