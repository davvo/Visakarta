package com.davvo.visakarta.shared;

public class VKMarker {

    private LatLon pos;

    public VKMarker(LatLon pos) {
        this.pos = pos;
    }
    
    public LatLon getPos() {
        return pos;
    }

    public void setPos(LatLon pos) {
        this.pos = pos;
    }
    
}
