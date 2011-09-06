package com.davvo.visakarta.shared;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Serialized;

@SuppressWarnings("serial")
@PersistenceCapable
public class Map implements Serializable {

    private static Map map;
    
    @PrimaryKey
    private String id;

    @Serialized
    private List<VKMarker> markers = new ArrayList<VKMarker>();
    
    @Persistent
    private String title;
    
    @Serialized
    private LatLon center = new LatLon(59.327, 18.072);
    
    @Persistent
    private int zoom = 6;

    @Persistent
    private MapType mapType = MapType.NORMAL;
        
    @Serialized
    private List<MapControl> controls = Arrays.asList(
        MapControl.MAP_TYPE,
        MapControl.NAVIGATION        
    );
    
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
    
    public List<VKMarker> getMarkers() {
        return this.markers;
    }
    
    public VKMarker getMarker(int id) {
        for (VKMarker marker: markers) {
            if (marker.getId() == id) {
                return marker;
            }
        }
        return null;
    }
    
    public List<VKMarker> getMarkersAtIndex(List<Integer> index) {
        List<VKMarker> markerList = new ArrayList<VKMarker>(index.size());
        for (int i: index) {
            markerList.add(markers.get(i));
        }
        return markerList;
    }
    
    public List<Integer> getMarkerIdsAtIndex(List<Integer> index) {
        List<Integer> ids = new ArrayList<Integer>(index.size());
        for (int i: index) {
            ids.add(markers.get(i).getId());
        }
        return ids;
    }
    
    public void setControls(List<MapControl> controls) {
        this.controls = controls;
    }
    
    public List<MapControl> getControls() {
        return controls;
    }
    
    public String toString() {
        return "Map(" + id + ") @ " + center;        
    }
    
    public static Map getInstance() {
        if (map == null) {
            map = new Map();
        }
        return map;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public MapType getMapType() {
        return mapType;
    }
    
}
