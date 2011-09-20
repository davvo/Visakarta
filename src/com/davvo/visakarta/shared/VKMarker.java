package com.davvo.visakarta.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VKMarker implements Serializable {

    private static int nextId = 0;
    
    private int id;
    private LatLon pos;
    private boolean infoWindow = true;
    private String infoWindowContent;

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
        
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }         
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }        
        return this.id == ((VKMarker) obj).id;
    }

    public void setInfoWindow(boolean infoWindow) {
        this.infoWindow = infoWindow;
    }

    public boolean isInfoWindow() {
        return infoWindow;
    }

    public void setInfoWindowContent(String infoWindowContent) {
        this.infoWindowContent = infoWindowContent;
    }

    public String getInfoWindowContent() {
        if (infoWindowContent == null) {
            return "";
        }
        return infoWindowContent;
    }
    
    public String getInfoWindowContentEscaped() {
        return getInfoWindowContent().replaceAll("\"", "'");
    }
    
}
