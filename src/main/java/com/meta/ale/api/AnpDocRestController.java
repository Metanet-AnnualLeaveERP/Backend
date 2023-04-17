package com.meta.ale.api;

import com.meta.ale.domain.AnpDocDto;
import com.meta.ale.domain.Criteria;
import com.meta.ale.service.AnpDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AnpDocRestController {


    private final AnpDocService anpDocService;

    // 연차사용요청서 추가 //
    @PostMapping("/annual-promote")
    public void insertAnpDoc(@RequestBody AnpDocDto anpDocDto) {
        try {
            anpDocService.insertAnpDocManually(anpDocDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 연차사용요청서 전체조회 //
    @GetMapping("/annual-promote")
    public Map<String, Object> getListAnpDoc(Criteria criteria) {
        return anpDocService.getListAnpDoc(criteria);
    }

    // 사용요청서 삭제 //
    @DeleteMapping("/annual-promote/{docId}")
    public ResponseEntity<String> deleteAnpDoc(@PathVariable("docId") Long docId) {
        boolean result = anpDocService.deleteAnpDoc(docId);
        if (result) {
            return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 상세조회 //
    @GetMapping("/annual-promote/{docId}")
    public AnpDocDto getAnpDoc(@PathVariable("docId") Long docId) {
        return anpDocService.getAnpDoc(docId);
    }
}
