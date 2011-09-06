package com.davvo.visakarta.shared;

public enum MapControl {

    MAP_TYPE("Map type"),
    OVERVIEW("Overview"),
    SCALE("Scale");
    
    private String name;
    
    MapControl(String name) {
        this.name = name;
    }
    
    public String toString() {
        return this.name;
    }
    
}
