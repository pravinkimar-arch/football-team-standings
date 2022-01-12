package com.sports.footballstand.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ControllerException extends RuntimeException{

    private String erroCode;
    private String errorMessage;

    public String getErroCode() {
        return erroCode;
    }

    public void setErroCode(String erroCode) {
        this.erroCode = erroCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ControllerException(String erroCode, String errorMessage) {
        super();
        this.erroCode = erroCode;
        this.errorMessage = errorMessage;
    }
    public ControllerException() {
    }
}
