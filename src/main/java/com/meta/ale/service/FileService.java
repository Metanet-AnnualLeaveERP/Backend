package com.meta.ale.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
public interface FileService {

    /*파일 저장*/
    public Path upload(MultipartFile uploadFile);

    /*파일 zip 저장*/
    public Path uploadZip(MultipartFile[] uploadFiles) throws IOException;

    /*파일 전체 다운로드*/
    public Stream<Path> downloadAll();

    /*파일 다운로드*/
    public Path download(String filename);

    /*파일 전체 삭제*/
    public void deleteAll();

    /*다중파일 zip 폴더로 압축*/

    /*경로 폴더 생성*/
    public String makeFolder();

}
