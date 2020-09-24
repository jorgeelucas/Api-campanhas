package com.desafio.campanhas.service.exception;
public class DataParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataParseException(String msg) {
        super(msg);
    }

    public DataParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

}