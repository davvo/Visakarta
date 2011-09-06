package com.davvo.visakarta.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LatLon implements Serializable {

    private double lat;
    
    private double lon;
    
    public LatLon() {
        
    }
    
    public LatLon(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }
    
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }
    
    public void setLon(double lon) {
        this.lon = lon;
    }
    
    public boolean equals(LatLon pos) {
        return pos.lat == lat && pos.lon == lon;
    }
    
}
