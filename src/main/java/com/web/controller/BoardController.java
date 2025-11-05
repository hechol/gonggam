package com.web.controller;

import com.web.dto.BoardDto;
import com.web.dto.RequestFormDto;
import com.web.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/register")
    public String registerGET(){
        return "register";
    }

    @PostMapping("/register")
//    public String registerPost(BoardDto boardDto, BindingResult bindingResult, Model model){
    public String registerPost(BoardDto boardDto, Model model){

//            if(bindingResult.hasErrors()){
//            return "register";
//        }

        Long id;

        try {
            id  = boardService.register(boardDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "요청 등록 중 에러가 발생하였습니다.");
            return "register";
        }

        return "redirect:/";
    }

    @GetMapping("/read/{id}")
    public String read(@PathVariable("id") Long id, Model model){

        BoardDto boardDto = boardService.readOne(id);

        model.addAttribute("dto", boardDto);

        return "read";
    }

    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id) {

        boardService.remove(id);

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
