package com.petadoption.center.email.controller;

import com.petadoption.center.email.dto.EmailDto;
import com.petadoption.center.email.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@Slf4j
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Object> sendMail(@Valid @RequestBody EmailDto emailDto) {
        log.info("HIT /send POST | Dto : {}", emailDto);
        return ResponseEntity.ok(emailService.sendMail(emailDto));
    }
}
