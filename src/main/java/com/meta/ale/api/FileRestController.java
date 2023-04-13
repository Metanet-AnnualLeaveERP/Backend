package com.meta.ale.api;

import com.meta.ale.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FileRestController {

    FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        System.out.println("---------------------- 파일 업로드 api 호출 -----------------------");
        fileService.upload(file);
        return new ResponseEntity<>("저장 성공!", HttpStatus.OK);
    }

}
