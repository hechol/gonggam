package com.web.controller;

import com.web.entity.Request;
import com.web.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final RequestService requestService;
    private final Environment env;

    @GetMapping(value = "/")
    public String main(Optional<Integer> page, Model model){

        String temp = env.getProperty("spring.datasource.url");
        log.info("temp={}", temp);

//        temp = env.getProperty("spring.security.oauth2.client.registration.google.client-id");
//        log.info("temp={}", temp);

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 2);
        Page<Request> requests = requestService.getMainPage(pageable);

        model.addAttribute("requests", requests);
        model.addAttribute("maxPage", 5);

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