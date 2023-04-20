package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.PagenationDTO;
import com.meta.ale.mapper.AnpDocMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class AnpDocServiceTest {

    @Autowired
    private AnpDocService anpDocService;

    @Test
    void getListAnpDocTest() throws Exception {

        Criteria criteria = new Criteria();
        criteria.setPageNum(1);
        criteria.setAmount(10);

        anpDocService.getListAnpDoc(criteria);
    }

    @Test
    void deleteAnpDocTest() throws Exception {
        Long docId = 10L;

        boolean res = anpDocService.deleteAnpDoc(docId);
    }

    @Test
    void getAnpDocTest() throws Exception {
        Long docId = 10L;

        anpDocService.getAnpDoc(docId);
    }

}
