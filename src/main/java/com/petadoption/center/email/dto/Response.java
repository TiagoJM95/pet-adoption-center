package com.petadoption.center.email.dto;

import com.petadoption.center.email.enums.ResponseMessage;
import jakarta.mail.internet.InternetAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String message;
    private InternetAddress recipient;
    private ResponseMessage status;
}
