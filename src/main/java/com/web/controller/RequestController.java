package com.web.controller;

import com.web.dto.RequestDto;
import com.web.dto.RequestFormDto;
import com.web.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/request")
@Controller
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping(value = "/new")
    public String newRequest(@Valid RequestFormDto requestFormDto, BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "request/requestForm";
        }

        try {
            requestService.save(requestFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "요청 등록 중 에러가 발생하였습니다.");
            return "/request/requestForm";
        }

        redirectAttributes.addAttribute("title", requestFormDto.getTitle());
        return "redirect:/";
    }

    @PostMapping(value = "/delete")
    public String deleteRequest(@PathVariable("requestId") Long requestId, BindingResult bindingResult, Model model){

        try {
            requestService.delete(requestId);
        } catch (Exception e){
            model.addAttribute("errorMessage", "요청 삭제 중 에러가 발생하였습니다.");
            return "/request/requestForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/{requestId}")
    public String requestInfo(Model model, @PathVariable("requestId") Long requestId){
        RequestDto requestDto = requestService.getRequestInfo(requestId);
        model.addAttribute("request", requestDto);
        return "request/requestInfo";
    }

    @GetMapping(value = "/new")
    public String requestForm(Model model){
        model.addAttribute("requestFormDto", new RequestFormDto());
        return "request/requestForm";
    }
}