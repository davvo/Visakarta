package com.davvo.visakarta.server;

@SuppressWarnings("serial")
public class DuplicateMapException extends RuntimeException {

    private String id;
    
    public DuplicateMapException(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
}
