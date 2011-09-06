package com.davvo.visakarta.shared;

public enum MapControl {

    //ZOOM("Zoom"),
    NAVIGATION("Navigation"),
    MAP_TYPE("Map type");
    //SCALE("Scale"),
    //STREETVIEW("Streetview"),
    //OVERVIEW_MAP("Overview map");
    
    String label;
    
    MapControl(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
}
