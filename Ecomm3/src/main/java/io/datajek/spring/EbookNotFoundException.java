package io.datajek.spring;

public class EbookNotFoundException extends RuntimeException{

    public EbookNotFoundException(String message){
        super(message);
    }
}