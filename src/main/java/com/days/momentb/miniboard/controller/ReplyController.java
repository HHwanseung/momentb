package com.days.momentb.miniboard.controller;

import com.days.momentb.common.dto.PageRequestDTO;
import com.days.momentb.common.dto.PageResponseDTO;
import com.days.momentb.miniboard.dto.ReplyDTO;
import com.days.momentb.miniboard.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/list/{mbNo}")
    public PageResponseDTO<ReplyDTO> getListOfBoard(@PathVariable("mbNo") Long mbNo, PageRequestDTO pageRequestDTO){

        return replyService.getListOfBoard(mbNo, pageRequestDTO);

    }

    @PostMapping("")
    public PageResponseDTO<ReplyDTO> register(@RequestBody ReplyDTO replyDTO){

        log.info("registerController로그..."+replyDTO);
        log.info("registerController로그..."+ replyDTO.getMbNo());
        replyService.register(replyDTO);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(-1).build();

        return replyService.getListOfBoard(replyDTO.getMbNo(), pageRequestDTO);
    }

    @DeleteMapping("/{mbNo}/{mbReNo}")
    public PageResponseDTO<ReplyDTO> remove(
            @PathVariable("mbNo")Long mbNo,
            @PathVariable("mbReNo")Long mbReNo,
            PageRequestDTO pageRequestDTO){

        return replyService.remove(mbNo, mbReNo, pageRequestDTO);
    }

}
