package com.meta.ale.api;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.service.GrantedVcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("admin/vacations/")
@RestController
@RequiredArgsConstructor
@Api(tags="휴가 부여 ",description = "휴가 부여 관련 api")
public class GrantedVcRestController {

    private final GrantedVcService grantedVcService;

    /* 임의휴가생성 */
    @PostMapping("granted")
    @ApiOperation("특정 사원 임의 휴가 생성 api")
    public ResponseEntity<String> insertGrantedVc(@RequestBody GrantedVcDto grantedVcDto) {
        System.out.println(grantedVcDto);
        try {
            boolean result = grantedVcService.insertGrantedVc(grantedVcDto);
            if (result) {
                return new ResponseEntity<>("DATA INSERT SUCCESS", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("DATA INSERT FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 임의휴가내역 전체조회 */
    @GetMapping("granted")
    @ApiOperation("임의 휴가내역 전체 조회 api")
    public Map<String, Object> getListGrantedVc(Criteria cri) {
        return grantedVcService.getListGrantedVc(cri);
    }

    /* 임의휴가내역 상세조회 */
    @GetMapping("granted/{vcId}")
    @ApiOperation("임의휴가내역 상세조회")
    public ResponseEntity<Object> getGrantedVc(@PathVariable("vcId") Long vcId) {
        GrantedVcDto gvDto = grantedVcService.getGrantedVc(vcId);
        if (gvDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(gvDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다");
    }
    /* empId 로 연차내역 조회 */
    //연차촉진문서함에서 empId 로 연차정보 찾아서 추가할 때 사용
    @GetMapping("granted-annual/{empId}")
    @ApiOperation("자신의 연차내역 조회 api")
    public ResponseEntity<Object> getAnnualLeaveByEmpId(@PathVariable("empId") Long empId) {
        GrantedVcDto gvDto = grantedVcService.getAnnualLeaveByEmpId(empId);
        if (empId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(gvDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다");
    }

    /* 임의휴가내역 삭제 */
    @DeleteMapping("granted/{vcId}")
    @ApiOperation("임의휴가 삭제")
    public ResponseEntity<String> deleteGrantedVc(@PathVariable("vcId") Long vcId) {
        boolean result = grantedVcService.deleteGrantedVc(vcId);
        if (result) {
            return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    // 작성중
//    /* 임의휴가내역 수정 */
//    @PutMapping("/granted/{vcId}")
//    @ApiOperation("부여된 휴가 내역 수정")
//    public ResponseEntity<String> updateGrantedVc(@PathVariable("vcId") Long vcId,
//                                                  @RequestBody GrantedVcDto gvDto) {
//        gvDto.setVcId(vcId);
//        return null;
//    }
}
