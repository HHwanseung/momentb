package com.days.momentb.miniboard.controller;

import com.days.momentb.common.dto.PageRequestDTO;
import com.days.momentb.miniboard.dto.MiniBoardDTO;
import com.days.momentb.miniboard.service.MiniBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/miniboard")
public class MiniBoardController {


    private final MiniBoardService miniBoardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("responseDTO", miniBoardService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(MiniBoardDTO miniBoardDTO, RedirectAttributes redirectAttributes){

        Long mbNo = miniBoardService.register(miniBoardDTO);

        redirectAttributes.addFlashAttribute("", mbNo);

        return "redirect:/miniboard/list";
    }


    @GetMapping("/read")
    public void read(Long mbNo, PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("dto", miniBoardService.read(mbNo));
    }



}
