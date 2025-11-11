package com.web.controller;

import com.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final BoardService boardService;
    private final Environment env;

    @GetMapping(value = "/")
    public String main(Model model){

        model.addAttribute("boards", boardService.list());
        return "main";
    }

   /* @GetMapping(value = "/postlogout")
    public String logout(Model model){
        return "postlogout";
    }

    @GetMapping(value = "/main")
    public String mainmain(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }
*/
}