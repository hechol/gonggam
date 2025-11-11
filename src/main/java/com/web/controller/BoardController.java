package com.web.controller;

import com.web.dto.BoardDto;
import com.web.entity.Member;
import com.web.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final HttpSession httpSession;

    @GetMapping("/register")
    public String registerGET(){
        return "register";
    }

    @ResponseBody
    @PostMapping("/register")
//    public String registerPost(BoardDto boardDto, BindingResult bindingResult, Model model){
    public String registerPost(@RequestBody BoardDto boardDto, Principal principal, Model model){

//            if(bindingResult.hasErrors()){
//            return "register";
//        }

        Member user = (Member) httpSession.getAttribute("user");
        boardDto.setWriter(user);

        try {
            boardService.register(boardDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "요청 등록 중 에러가 발생하였습니다.");
        }

        return "ok";
    }

    @GetMapping("/read/{id}")
    public String read(@PathVariable("id") Long id, Model model){

        BoardDto boardDto = boardService.readOne(id);

        model.addAttribute("dto", boardDto);

        return "read";
    }

    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id, Principal principal) {

        BoardDto boardDto = boardService.readOne(id);
        if(principal.getName().equals(boardDto.getWriter().getId().toString())){
            boardService.remove(id);
        }else{
//            boardService.remove(id);
        }

    }

/*
    @PostMapping("/modify")
    public String modify(@Valid BoardDTO boardDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO);

//        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }*/


}
