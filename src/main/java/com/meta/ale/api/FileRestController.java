package com.meta.ale.api;

import com.meta.ale.service.FileService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/files/download")
    public ResponseEntity download(@RequestBody String filePath) throws IOException {
        System.out.println("============파일 다운로드 api===========");
        System.out.println("filePath 인코딩 된 상태로 들어온 것 : " + filePath);

        String decodedPath = URLDecoder.decode(filePath, "UTF-8");
        // 파일 경로에서 마지막에 있는 패딩 문자 제거
        if (decodedPath.endsWith("=")) {
            decodedPath = decodedPath.substring(0, decodedPath.length() - 1);
        }

        // 디코딩된 파일 경로를 콘솔에 출력
        System.out.println("Decoded File Path: " + decodedPath);

        Path path = Paths.get(decodedPath);
        System.out.println("final path : " + path);
        String fileName = decodedPath.split("\\\\")[3];
        System.out.println("file name : " + fileName);

        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());

        } catch (Exception e) {
            System.out.println("여기 에러");
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
//        e.printStackTrace();
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
