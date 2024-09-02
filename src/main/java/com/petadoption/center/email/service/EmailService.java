package com.petadoption.center.email.service;

import com.petadoption.center.email.dto.EmailDto;
import com.petadoption.center.model.User;
import jakarta.mail.internet.AddressException;
import jakarta.validation.Valid;

public interface EmailService {
    Object sendMail(@Valid EmailDto emailDto);

     void sendWelcomeMail(User user);
}
