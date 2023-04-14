package com.meta.ale.api;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.service.GrantedVcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("api/admin/vacations/")
@RestController
public class GrantedVcRestController {

    @Autowired
    private GrantedVcService grantedVcService;

    /* 임의휴가생성 */
    @PostMapping("granted")
    public ResponseEntity<String> insertGrantedVc(@RequestBody GrantedVcDto grantedVcDto){
        try{
            grantedVcService.insertGrantedVc(grantedVcDto);
            return new ResponseEntity<>("DATA INSERT SUCCESS", HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /* 임의휴가내역 전체조회 */
    @GetMapping("granted")
    public Map<String, Object> getListGrantedVc(@RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "10") int pageNum, Criteria criteria){
        criteria.setPageNum(page);
        criteria.setAmount(pageNum);

        return grantedVcService.getListGrantedVc(criteria);
    }

    /* 휴가내역 삭제 */
    @DeleteMapping("granted/{vcId}")
    public ResponseEntity<String> deleteGrantedVc(@PathVariable("vcId") Long vcId){
        boolean result = grantedVcService.deleteGrantedVc(vcId);
        if(result){
            return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 작성중
}
