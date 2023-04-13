package com.meta.ale.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
public interface FileService {

    /*파일 저장*/
    String upload(MultipartFile file);

    /*파일 전체 다운로드*/
    Stream<Path> downloadAll();

    /*파일 다운로드*/
    Path download(String filename);

    Resource downloadAsResource(String filename);

    /*파일 전체 삭제*/
    void deleteAll();

}
