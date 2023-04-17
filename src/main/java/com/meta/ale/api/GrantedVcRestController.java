package com.meta.ale.api;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.service.GrantedVcService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("admin/vacations/")
@RestController
@RequiredArgsConstructor
public class GrantedVcRestController {

    private final GrantedVcService grantedVcService;

    /* 임의휴가생성 */
    @PostMapping("granted")
    public ResponseEntity<String> insertGrantedVc(@RequestBody GrantedVcDto grantedVcDto) {
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
    public Map<String, Object> getListGrantedVc(Criteria criteria) {
        return grantedVcService.getListGrantedVc(criteria);
    }

    /* 임의휴가내역 상세조회 */
    @GetMapping("granted/{vcId}")
    public ResponseEntity<Object> getGrantedVc(@PathVariable("vcId") Long vcId) {
        GrantedVcDto gvDto = grantedVcService.getGrantedVc(vcId);
        if (gvDto != null) {
            System.out.println(gvDto);
            return ResponseEntity.status(HttpStatus.OK).body(gvDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다");
    }

    /* 임의휴가내역 삭제 */
    @DeleteMapping("granted/{vcId}")
    public ResponseEntity<String> deleteGrantedVc(@PathVariable("vcId") Long vcId) {
        boolean result = grantedVcService.deleteGrantedVc(vcId);
        if (result) {
            return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 작성중
    /* 임의휴가내역 수정 */
    @PutMapping("/granted/{vcId}")
    public ResponseEntity<String> updateGrantedVc(@PathVariable("vcId") Long vcId,
                                                  @RequestBody GrantedVcDto gvDto) {

        gvDto.setVcId(vcId);

        return null;
    }
}
