package com.davvo.visakarta.shared;


public class MapPosition {

    private LatLon center;
    private int zoom;
    
    public MapPosition(LatLon center, int zoom) {
        this.center = center;
        this.zoom = zoom;
    }
    
    public LatLon getCenter() {
        return center;
    }

    public int getZoom() {
        return zoom;
    }
    
    public boolean equals(MapPosition pos) {
        return center.equals(pos.center) && zoom == pos.zoom;
    }
    
    public String toString() {
        return new StringBuilder().append(center).append(", zoom: ").append(zoom).toString();
    }

}
