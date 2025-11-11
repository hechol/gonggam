package com.web.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class RequestRepositoryTest {

    @Autowired
//    private RequestService requestService;

    @Test
    @DisplayName("저장 테스트")
    public void order() throws Exception {
//        RequestFormDto requestFormDto = new RequestFormDto();
//        requestFormDto.setRequestCategory(RequestCategory.DEV);
//        requestFormDto.setTitle("test");
//        requestFormDto.setContent("test");
//        Long id = requestService.save(requestFormDto);
//        RequestDto requestDto = requestService.getRequestInfo(id);
//
//        assertEquals(100, 100);
    }
}
