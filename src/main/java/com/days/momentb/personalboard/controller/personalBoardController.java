package com.days.momentb.personalboard.controller;

import com.days.momentb.common.dto.PageRequestDTO;
import com.days.momentb.personalboard.dto.PersonalBoardDTO;
import com.days.momentb.personalboard.service.PersonalBoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/personalboard")
public class personalBoardController {

    private final PersonalBoardService personalBoardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("responseDTO", personalBoardService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(PersonalBoardDTO personalBoardDTO, RedirectAttributes redirectAttributes){

        Long pbNo = personalBoardService.register(personalBoardDTO);

        redirectAttributes.addFlashAttribute("",pbNo);

        return "redirect:/personalboard/list";
    }


    @GetMapping("/read")
    public void read(Long pbNo, PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("dto", personalBoardService.read(pbNo));

    }



}
