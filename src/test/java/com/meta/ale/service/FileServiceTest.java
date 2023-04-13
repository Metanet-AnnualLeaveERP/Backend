package com.meta.ale.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SpringBootTest
public class FileServiceTest {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Autowired
    private FileService fileService;

    /*ZipOutputStream과 ZipEntry를 통해 압축파일을 만드는 로직*/
    @Test
    void zipTest() throws IOException {

        String folderPath = fileService.makeFolder();

        //UUID
        String uuid = UUID.randomUUID().toString();

        // zip 폴더 파일명을 uuid로 설정
        String zipName = uuid + ".zip";

        // zip 폴더가 생성될 경로 설정
        String zipPath = uploadPath + File.separator + folderPath + File.separator + zipName;

        // Zip파일을 저장할 FileOutputStream과 ZipOutputStream을 생성합니다.
        // zip 파일 경로 및 파일명 설정
//        FileOutputStream zipFileOutputStream = new FileOutputStream(uploadPath + "/files.zip");
        FileOutputStream zipFileOutputStream = new FileOutputStream(zipPath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(zipFileOutputStream);

        // ZipEntry를 통해 Zip파일에 저장할 파일들을 구별합니다.
        ZipEntry zipEntry = new ZipEntry("test.txt");

        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write("하하하하하\n쓰자".getBytes(StandardCharsets.UTF_8));

        ZipEntry zipEntry2 = new ZipEntry("test2.txt");

        zipOutputStream.putNextEntry(zipEntry2);
        zipOutputStream.write("두번째에여...".getBytes(StandardCharsets.UTF_8));

        zipOutputStream.close();
        zipFileOutputStream.close();
    }
}
