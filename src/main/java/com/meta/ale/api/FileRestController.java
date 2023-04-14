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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api")
@AllArgsConstructor
public class FileRestController {

    FileService fileService;

    @PostMapping("/files/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(MultipartFile[] uploadFiles) throws IOException {
        System.out.println("---------------------- 파일 업로드 api 호출 -----------------------");
        System.out.println("uploadFiles 컨트롤러로 넘어온 개수 = " + uploadFiles.length);

        Path filePath = null;

        if (uploadFiles.length > 1) {
            filePath = fileService.uploadZip(uploadFiles);
        } else {
            filePath = fileService.upload(uploadFiles[0]);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("status", "성공");
        map.put("filePath", filePath);
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

}
