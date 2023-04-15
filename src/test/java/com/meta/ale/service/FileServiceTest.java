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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Test
    void download() throws IOException {
        /*
  		Paths.get를 통해 파일 정보를 가져온후 파일의 내용을 바이트 배열로 읽어 들인 다음 파일을 닫는다.
        */
        String filePath = "d:\\ALE_downloaded_files\\2023_04_14\\7d6f8f04-914e-40fe-a572-03c0e440ef55.zip";
        System.out.println(filePath);
//        String[] split = filePath.split("\\\\");
        String fileName = filePath.split("\\\\")[3];
//        for (String s : split ){
//            System.out.println(s);
//        }
        System.out.println(fileName);

//        byte[] data = null;
//        try {
//            Path path = Paths.get(filePath);
//            System.out.println(path);
//            data = Files.readAllBytes(path);
//            System.out.println(data);
//        } catch (IOException ex) {
//            throw new IOException("IOE Error Message= " + ex.getMessage());
//        }

    }
}
