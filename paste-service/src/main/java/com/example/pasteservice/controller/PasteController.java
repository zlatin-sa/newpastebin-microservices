package com.example.pasteservice.controller;

import com.example.pasteservice.dto.CreatePasteDto;
import com.example.pasteservice.dto.GetPasteDto;
import com.example.pasteservice.service.PasteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PasteController {

    private final PasteService pasteService;

    @GetMapping("/{url}")
    public GetPasteDto getPasteByUrl(@PathVariable String url){
        return pasteService.getPasteByUrl(url);
    }

    @GetMapping("/public")
    public List<GetPasteDto> getPublicPastes() {
        return pasteService.getPublicPastes();
    }

    @PutMapping("/create/{userId}")
    public String createPaste(@RequestBody CreatePasteDto pasteDto, @PathVariable Long userId){
        return pasteService.createPaste(pasteDto, userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteAllByUserId(@PathVariable Long userId){
        pasteService.deleteAllByUserId(userId);
    }

    @GetMapping("/user/{userId}")
    public List<GetPasteDto> getUserPublicPastes(@PathVariable Long userId){
        return pasteService.getUserPublicPastes(userId);
    }
}
