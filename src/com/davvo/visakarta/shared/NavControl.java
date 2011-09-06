package com.davvo.visakarta.shared;

public enum NavControl {

    NONE("None"),
    
    LARGE("Large"),
    LARGE_3D("Large 3D"),
    SMALL("Small"),
    SMALL_ZOOM("Small zoom"),
    SMALL_ZOOM_3D("Small zoom 3D");
    
    private String name;
    
    NavControl(String name) {
        this.name = name;
    }
    
    public String toString() {
        return this.name;
    }
    
}
