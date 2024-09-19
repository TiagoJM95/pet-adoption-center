package com.petadoption.center.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.center.dto.email.EmailDto;
import com.petadoption.center.exception.email.SendingEmailToMicroserviceException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    @Value("${express.service.url}")
    private String expressServiceUrl;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String EXCHANGE = "emailExchange";
    private static final String ROUTING_KEY = "emailRoutingKey";

    public void sendEmail(EmailDto dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, json);
            System.out.println("Email sent to RabbitMQ: " + json);
            sendEmailToExpressService(dto);
        } catch (Exception e) {
            throw new SendingEmailToMicroserviceException("Something went wrong sending email to microservice.");
        }
    }

    private void sendEmailToExpressService(EmailDto dto) {
        String url = expressServiceUrl + "send-email";

        ResponseEntity<String> response = restTemplate.postForEntity(url, dto, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Email sent to Express.js microservice successfully.");
        } else {
            System.out.println("Failed to send email to Express.js microservice: " + response.getStatusCode());
        }
    }

}
