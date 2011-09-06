package com.davvo.visakarta.shared;

public enum MapType {

    NORMAL("Normal"), 
    SATELLITE("Satellite"), 
    HYBRID("Hybrid"),
    TERRAIN("Terrain");
    
    private String name;
    
    MapType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
    
}
