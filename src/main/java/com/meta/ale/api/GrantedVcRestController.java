package com.meta.ale.api;

import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.service.GrantedVcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    // 작성중
}
