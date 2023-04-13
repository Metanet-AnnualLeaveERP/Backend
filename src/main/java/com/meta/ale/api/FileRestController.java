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
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api")
@AllArgsConstructor
public class FileRestController {

    FileService fileService;

    @PostMapping("/files/upload")
    public ResponseEntity uploadFile(MultipartFile[] uploadFiles) {
        System.out.println("---------------------- 파일 업로드 api 호출 -----------------------");
        System.out.println(uploadFiles);

        fileService.upload(uploadFiles);

        return ResponseEntity.status(HttpStatus.CREATED).body("파일 업로드에 성공하였습니다.");
    }

}
