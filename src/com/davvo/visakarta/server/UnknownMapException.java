package com.davvo.visakarta.server;

@SuppressWarnings("serial")
public class UnknownMapException extends RuntimeException {

    private String id;
    
    public UnknownMapException(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
}
