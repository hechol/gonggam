package com.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.config.TestSecurityConfig;
import com.web.constant.BoardCategory;
import com.web.constant.Role;
import com.web.dto.BoardDto;
import com.web.entity.Member;
import com.web.repository.BoardRepository;
import com.web.repository.MemberRepository;
import com.web.service.BoardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockHttpSession session;

    @Autowired
    MemberRepository userRepository;

    BoardDto  boardDTO;

    @BeforeEach
    void setUp() {
        Member member = new Member();
        member.setRole(Role.USER);
        Member savedMember = userRepository.save(member);
        session.setAttribute("user", savedMember);

        UserDetails user = User.builder()
                .username(savedMember.getId().toString())
                .password("")
                .roles(savedMember.getRole().toString())
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        boardDTO = new BoardDto();
        boardDTO.setCategory(BoardCategory.gonggam.name());
        boardDTO.setTitle("this is a title");
        boardDTO.setContent("this is a content");
        boardDTO.setWriter(savedMember);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void register() {
        assert(boardService.list(BoardCategory.gonggam.name()).isEmpty());

        try {
            mockMvc.perform(post("/board/register").contentType(MediaType.APPLICATION_JSON)
                    .session(session)
                    .content(objectMapper.writeValueAsString(boardDTO)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assert(boardService.list(BoardCategory.gonggam.name()).size() == 1);
    }

    @Test
    void remove() {
        Long register = boardService.register(boardDTO);

        assert(boardService.list(BoardCategory.gonggam.name()).size() == 1);

        try {
            mockMvc.perform(delete("/board/remove/" + register));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assert(boardService.list(BoardCategory.gonggam.name()).isEmpty());
    }
}