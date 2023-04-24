package com.meta.ale.api;

import com.meta.ale.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "파일(업로드,다운로드)" ,description = "파일 관련 api")
public class FileRestController {

    private final FileService fileService;

    @PostMapping("/files/upload")
    @ApiOperation("파일 업로드 api ")
    public ResponseEntity<Map<String, Object>> uploadFile(MultipartFile[] uploadFiles) throws IOException {
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

    @PostMapping("/files/download")
    @ApiOperation("파일 다운로드 api")
    public ResponseEntity download(@RequestBody String filePath) throws IOException {
        String decodedPath = URLDecoder.decode(filePath, "UTF-8");
        // 파일 경로에서 마지막에 있는 패딩 문자 제거
        if (decodedPath.endsWith("=")) {
            decodedPath = decodedPath.substring(0, decodedPath.length() - 1);
        }

        // 디코딩된 파일 경로를 콘솔에 출력
        Path path = Paths.get(decodedPath);
        String fileName = decodedPath.split("\\\\")[3];

        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // 응답 데이터가 binary 데이터
                .cacheControl(CacheControl.noCache())
                // 브라우저가 파일을 다운받게 하는 설정
                .header(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8")+"\"")
                // 바디 : 파일 uri
                .body(resource);
    }


}
