package com.meta.ale.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    // application.yml에 설정한 파일 다운로드 경로
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    /*파일 저장*/
    @Override
    public String upload(MultipartFile uploadFile) {
        System.out.println("----------- file Service upload method ----------");

        String originalName = uploadFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);

        System.out.println("fileName = " + fileName);

        //날짜 폴더 생성
        String folderPath = makeFolder();

        //UUID
        String uuid = UUID.randomUUID().toString();

        //저장할 파일 이름 중간에 "_"를 이용하여 구분
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

        //Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
        Path savePath = Paths.get(saveName);

        try {
            if (uploadFile.isEmpty()) {
                throw new Exception("ERROR : File is empty.");
            }
            Path root = Paths.get(uploadPath);
            System.out.println("uploadroot = " + root);

            // 파일 업로드
            try {
                uploadFile.transferTo(savePath);
                //uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
            } catch (IOException e) {
                e.printStackTrace();
                //printStackTrace()를 호출하면 로그에 Stack trace가 출력됩니다.
            }
//            // 파일 스트림 가져옴
//            try (InputStream inputStream = uploadFile.getInputStream()) {
//
//                Files.copy(inputStream, root.resolve(saveName),
//                        StandardCopyOption.REPLACE_EXISTING);
//            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        // 저장 경로 리턴
        System.out.println("파일 저장 경로 = " + savePath);
        return savePath.toString();
    }

    /*파일 저장 경로에 오늘 날짜 폴더 생성*/
    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        //LocalDate를 문자열로 포멧
//        String folderPath = str.replace("/", File.separator);
        String folderPath = str.replace("/", "_");
//        System.out.println("folderPath = " + folderPath);
        //만약 Data 밑에 exam.jpg라는 파일을 원한다고 할때,
        //윈도우는 "Data\\"eaxm.jpg", 리눅스는 "Data/exam.jpg"라고 씁니다.
        //그러나 자바에서는 "Data" +File.separator + "exam.jpg" 라고 쓰면 됩니다.

        //make folder ==================
        File uploadPathFolder = new File(uploadPath, folderPath);
        //File newFile= new File(dir,"파일명");
        //->부모 디렉토리를 파라미터로 인스턴스 생성

        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
            //만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
            //mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
            //mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
        }
        return folderPath;
    }

    @Override
    public Stream<Path> downloadAll() {
        return null;
    }

    @Override
    public Path download(String filename) {
        return null;
    }

    @Override
    public Resource downloadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
