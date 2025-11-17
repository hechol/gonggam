package com.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.config.TestSecurityConfig;
import com.web.constant.BoardCategory;
import com.web.constant.Role;
import com.web.dto.BoardDto;
import com.web.entity.Member;
import com.web.repository.MemberRepository;
import com.web.service.BoardService;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
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

    MockHttpSession session;

    @Autowired
    MemberRepository userRepository;

    Member createMember(){
        Member member = new Member();
        member.setRole(Role.USER);

        return userRepository.save(member);
    }

    void login(Member member){

        session = new MockHttpSession();
        session.setAttribute("user", member);

        UserDetails userDetails = User.builder()
                .username(member.getId().toString())
                .password("")
                .roles(member.getRole().toString())
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    void logout(){
        SecurityContextHolder.clearContext();
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        session.invalidate();
    }

    BoardDto createBoard(){
        BoardDto boardDTO = new BoardDto();
        boardDTO.setCategory(BoardCategory.gonggam.name());
        boardDTO.setTitle("this is a title");
        boardDTO.setContent("this is a content");
        return boardDTO;
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void register() {

        assert(boardService.list(BoardCategory.gonggam.name()).isEmpty());

        Member member = createMember();
        login(member);
        BoardDto boardDTO = createBoard();
        
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
    void removeFailAnotherUser() throws Exception {
        Member member = createMember();
        login(member);
        BoardDto boardDTO = createBoard();
        Member member2 = createMember();
        boardDTO.setWriter(member2);
        Long register = boardService.register(boardDTO);

        mockMvc.perform(delete("/board/remove/" + register));
        assert(boardService.list(BoardCategory.gonggam.name()).size() == 1);
    }

    @Test
    void removeFailLogout() throws Exception {
        Member member = createMember();
        login(member);
        BoardDto boardDTO = createBoard();
        boardDTO.setWriter(member);
        Long register = boardService.register(boardDTO);

        logout();
        ServletException exception = assertThrows(ServletException.class, () -> mockMvc.perform(delete("/board/remove/" + register)));
        exception.getCause().printStackTrace();
        assert(boardService.list(BoardCategory.gonggam.name()).size() == 1);
    }

    @Test
    void removeSuccess() throws Exception {
        Member member = createMember();
        login(member);
        BoardDto boardDTO = createBoard();
        boardDTO.setWriter(member);
        Long register = boardService.register(boardDTO);

        assert(boardService.list(BoardCategory.gonggam.name()).size() == 1);

        mockMvc.perform(delete("/board/remove/" + register));

        assert(boardService.list(BoardCategory.gonggam.name()).isEmpty());
    }
}