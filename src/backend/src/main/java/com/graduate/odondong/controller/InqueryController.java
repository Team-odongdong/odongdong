package com.graduate.odondong.controller;

import com.graduate.odondong.service.InqueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class InqueryController {

    private final InqueryService inqueryService;

    @PostMapping("/api/mail/send")
    public void addInquery(@RequestBody String contents) {
        inqueryService.createInquery(contents);
    }
}
