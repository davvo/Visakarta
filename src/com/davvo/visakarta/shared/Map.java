package com.davvo.visakarta.shared;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Serialized;

@SuppressWarnings("serial")
@PersistenceCapable
public class Map implements Serializable {

    @PrimaryKey
    private String id;
    
    @Persistent
    private String title;
    
    @Serialized
    private LatLon center;
    
    @Persistent
    private int zoom;
    
    public Map() {
        
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getZoom() {
        return zoom;
    }
    
    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
    
    public LatLon getCenter() {
        return center;
    }

    public void setCenter(LatLon center) {
        this.center = center;
    }
    
    public String toString() {
        return "Map(" + id + ") @ " + center;        
    }
        
}
