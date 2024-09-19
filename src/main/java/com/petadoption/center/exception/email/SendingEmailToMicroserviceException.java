package com.petadoption.center.exception.email;

public class SendingEmailToMicroserviceException extends EmailException{
    public SendingEmailToMicroserviceException(String message) {
        super(message);
    }
}
