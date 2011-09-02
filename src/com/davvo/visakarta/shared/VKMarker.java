package com.davvo.visakarta.shared;

public class VKMarker {

    private static int nextId = 0;
    
    private int id;
    private LatLon pos;

    public VKMarker(LatLon pos) {
        this.id = nextId++;
        this.pos = pos;
    }

    public int getId() {
        return id;
    }
    
    public LatLon getPos() {
        return pos;
    }

    public void setPos(LatLon pos) {
        this.pos = pos;
    }
    
    public boolean equals(VKMarker marker) {
        return marker.id == id;
    }
    
}
