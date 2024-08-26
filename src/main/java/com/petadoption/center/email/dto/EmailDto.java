package com.petadoption.center.email.dto;


import jakarta.mail.internet.InternetAddress;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private InternetAddress recipient;
    private String subject;
    private String body;
}
