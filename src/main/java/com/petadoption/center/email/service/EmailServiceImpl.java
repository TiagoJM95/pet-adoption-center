package com.petadoption.center.email.service;

import com.petadoption.center.email.dto.EmailDto;
import com.petadoption.center.email.dto.Response;
import com.petadoption.center.email.enums.ResponseMessage;
import com.petadoption.center.model.User;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static com.petadoption.center.util.Messages.*;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public Object sendMail(EmailDto emailDto) {

        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(emailDto.getRecipient());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody());

            javaMailSender.send(mimeMessage);

            log.info("Message Sent Successfully to: {}", emailDto.getRecipient());
            return new Response(EMAIL_SUCCESS, emailDto.getRecipient(), ResponseMessage.SUCCESS);
        }
        catch (Exception e) {
            System.out.println(e.getCause().getMessage());
            log.error("sendEmail() | Error : {}", e.getMessage());
            return new Response(EMAIL_ERROR, emailDto.getRecipient(), ResponseMessage.FAILURE);
        }

    }

    public Object sendWelcomeMail(User user) throws AddressException {

        InternetAddress recipient = new InternetAddress(user.getEmail());
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(WELCOME_EMAIL_SUBJECT);
            mimeMessageHelper.setText("Welcome " + user.getFirstName() + "!");

            log.info("Welcome Email Sent Successfully to: {}", recipient);
            return new Response(EMAIL_SUCCESS, recipient, ResponseMessage.SUCCESS);
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
            log.error("sendWelcomeMail() | Error : {}", e.getMessage());
            return new Response(EMAIL_ERROR, recipient, ResponseMessage.FAILURE);
        }

    }

}

