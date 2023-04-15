package com.meta.ale.api;

import com.meta.ale.domain.AnpDocDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.service.AnpDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AnpDocRestController {

    @Autowired
    AnpDocService anpDocService;

    // 연차사용요청서 추가 //
    @PostMapping("/annual-promote/")
    public void insertAnpDoc(@RequestBody AnpDocDto anpDocDto){
        try{
            anpDocService.insertAnpDocManually(anpDocDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 연차사용요청서 전체조회 //
    @GetMapping("/annual-promote/")
    public Map<String, Object> getListAnpDoc(@RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageNum,
                                             Criteria criteria) {
        criteria.setPageNum(page);
        criteria.setAmount(pageNum);

        return anpDocService.getListAnpDoc(criteria);
    }

    // 사용요청서 삭제 //
    @DeleteMapping("/annual-promote/{docId}")
    public ResponseEntity<String> deleteAnpDoc(@PathVariable("docId") Long docId){
        boolean result = anpDocService.deleteAnpDoc(docId);
        if(result){
            return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 상세조회 //
    @GetMapping("/annual-promote/{docId}")
    public AnpDocDto getAnpDoc(@PathVariable("docId") Long docId){
        return anpDocService.getAnpDoc(docId);
    }
}
