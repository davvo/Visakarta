package com.davvo.visakarta.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VKMarker implements Serializable {

    private static int nextId = 0;
    
    private int id;
    private LatLon pos;

    public VKMarker() {
        this(new LatLon(0, 0));
    }
    
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
