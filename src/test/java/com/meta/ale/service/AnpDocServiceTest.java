package com.meta.ale.service;

import com.meta.ale.domain.Criteria;
import com.meta.ale.domain.PagenationDTO;
import com.meta.ale.domain.UserDto;
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

    @Autowired
    private AnpDocMapper mapper;
//    @Test
//    void getListAnpDocTest() throws Exception {
//
//        Criteria cri = new Criteria();
//        cri.setPageNum(1);
//        cri.setAmount(10);
//        cri.setKeyword("이름");
//        HashMap<String, Object> dto = new HashMap<>();
//        dto.put("pageNum", cri.getPageNum());
//        dto.put("amount", cri.getAmount());
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(0L);
//        userDto.setRole("ROLE_ADMIN");
//
//        String keyWordName = cri.getKeyword();
//        dto.put("keyWordName", keyWordName);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("paging", new PagenationDTO(cri, 10));
//        map.put("anpDocs",mapper.getListAnpDoc(dto));
//        System.out.println(anpDocService.getListAnpDoc(userDto, cri));
//        ;
//    }

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
