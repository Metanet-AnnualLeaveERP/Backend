package com.meta.ale.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService {

    // application.yml에 설정한 파일 다운로드 경로
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    /*파일 저장*/
    @Override
    public Path upload(MultipartFile uploadFile) {
        System.out.println("----------- file Service upload method ----------");

        String originalName = uploadFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);
        System.out.println("fileName = " + fileName);

        // 날짜 폴더 생성
        String folderPath = makeFolder();

        // UUID
        String uuid = UUID.randomUUID().toString();

        // 저장할 파일 이름 중간에 "_"를 이용하여 구분
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
        Path savePath = Paths.get(saveName); // Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)

        try {
            if (uploadFile.isEmpty()) {
                throw new Exception("ERROR : File is empty.");
            }

            // ---------- 파일 업로드 -----------
            try {
                // uploadFile에 파일을 업로드 하는 메소드 transferTo(file)
                uploadFile.transferTo(savePath);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        // 저장 경로 리턴
        System.out.println("파일 저장 경로 = " + savePath);
        System.out.println("최종 파일 네임 = " + saveName);

        return savePath;
    }

    /*---------------------------------------------------*/
    /*ZipOutputStream과 ZipEntry를 통해 압축파일을 만드는 로직*/
    /*---------------------------------------------------*/
    @Override
    public Path uploadZip(MultipartFile[] uploadFiles) throws IOException {
        System.out.println("----------- file Service ZIP 폴더 upload method ----------");

        // 날짜 폴더 생성
        String folderPath = makeFolder();

        // UUID
        String uuid = UUID.randomUUID().toString();

        // zip 폴더 파일명을 uuid로 설정
        String zipName = uuid + ".zip";

        // zip 폴더가 생성될 경로 설정
        // 저장할 파일 이름 중간에 "_"를 이용하여 구분
        String zipPath = uploadPath + File.separator + folderPath + File.separator + zipName;
        Path savePath = Paths.get(zipPath);
        System.out.println("zip 파일 경로 = " + savePath);

        // Zip파일을 저장할 FileOutputStream과 ZipOutputStream을 생성합니다.
        // zip 파일 경로 및 파일명 설정
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));

        for (MultipartFile uploadFile : uploadFiles) {
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);
            System.out.println("fileName = " + fileName);

            File file = multipartToFile(uploadFile, fileName);
//            System.out.println("108line");
//            System.out.println(file.length());
            FileInputStream in = new FileInputStream(file);
            try {
                if (uploadFile.isEmpty()) {
                    throw new Exception("ERROR : File is empty.");
                }
//                Path root = Paths.get(uploadPath);
//                System.out.println("uploadroot = " + root);

                // ---------- zipEntry를 생성하여 zipPath에 파일 업로드 -----------
                try {
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    out.putNextEntry(zipEntry);

                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.closeEntry(); // zipOutputStream close zipEntry
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
        }
        out.close(); // zipOutputStream close
        return savePath;
    }

    /*multipartFile 을 File 로 변환하는 메소드*/
    public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    /*파일 저장 경로에 오늘 날짜 폴더 생성*/
    @Override
    public String makeFolder() {

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
    public void deleteAll() {

    }
}
