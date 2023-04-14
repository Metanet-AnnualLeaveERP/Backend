package com.meta.ale.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmpService {

    @Test
    public void getDate() {
        LocalDate now = LocalDate.now();
        int kk = now.getYear();
        System.out.println(kk);
    }
}
