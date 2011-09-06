package com.davvo.visakarta.shared;

public enum MapType {

    NORMAL("Normal"), 
    SATELLITE("Satellite"), 
    HYBRID("Hybrid");
    
    private String name;
    
    MapType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
    
}
