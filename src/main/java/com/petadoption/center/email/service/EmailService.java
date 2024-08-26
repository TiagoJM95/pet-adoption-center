package com.petadoption.center.email.service;

import com.petadoption.center.email.dto.EmailDto;
import jakarta.validation.Valid;

public interface EmailService {
    Object sendMail(@Valid EmailDto emailDto);
}
