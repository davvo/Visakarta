package com.davvo.visakarta.client.view;

import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.davvo.visakarta.client.event.HasMapHandlers;
import com.davvo.visakarta.client.event.MapTypeChangedHandler;
import com.davvo.visakarta.client.event.MarkerClickedEvent;
import com.davvo.visakarta.client.event.MarkerClickedHandler;
import com.davvo.visakarta.client.event.MarkerMovedEvent;
import com.davvo.visakarta.client.event.MarkerMovedHandler;
import com.davvo.visakarta.client.event.MapMovedHandler.MapMovedEvent;
import com.davvo.visakarta.client.event.MapMovedHandler;
import com.davvo.visakarta.client.presenter.MapPresenter.Display;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.MapControl;
import com.davvo.visakarta.shared.MapType;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.EventCallback;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.Widget;

public class MapView implements Display {

    private MapEventHandler mapEventHandler = new MapEventHandler(this);    
    private Map<Integer, Marker> markers = new HashMap<Integer, Marker>();
    private MapWidget mapWidget;
    
    private List<MapControl> controls = new ArrayList<MapControl>();
    
    public MapView() {
        createMapWidget();
        bind();
    }

    private void createMapWidget() {
        final MapOptions options = new MapOptions();
        mapWidget = new MapWidget(options);
        mapWidget.setSize("100%", "100%");
    }
    
    private void bind() {
        Event.addListener(mapWidget.getMap(), "bounds_changed", new EventCallback() {
            
            @Override
            public void callback() {
                mapEventHandler.fireEvent(new MapMovedEvent());    
            }
        });
        
        Event.addListener(mapWidget.getMap(), "maptypeid_changed", new EventCallback() {
            
            @Override
            public void callback() {
                mapEventHandler.fireEvent(new MapTypeChangedHandler.MapTypeChangedEvent());
            }
        });
                
    }
    
    @Override
    public LatLon getCenter() {
        return asLatLon(mapWidget.getMap().getCenter());
    }

    @Override
    public void setCenter(LatLon center) {
        mapWidget.getMap().setCenter(asLatLng(center));
    }

    @Override
    public int getZoom() {
        return mapWidget.getMap().getZoom();
    }

    @Override
    public void setZoom(int zoom) {
        mapWidget.getMap().setZoom(zoom);
    }

    @Override
    public HasMapHandlers getMapHandler() {
        return mapEventHandler;
    }

    @Override
    public void addMarker(VKMarker marker) {
        
        MarkerOptions options = new MarkerOptions();        
        options.setDraggable(true);
        options.setVisible(true);
        options.setMap(mapWidget.getMap());
        options.setPosition(asLatLng(marker.getPos()));
        
        final Marker m = new Marker(options);
        
        final int id = marker.getId();
        
        Event.addListener(m, "drag", new EventCallback() {
            
            @Override
            public void callback() {
                mapEventHandler.fireEvent(new MarkerMovedEvent(id, asLatLon(m.getPosition())));                
            }
        });
        
        Event.addListener(m, "click", new EventCallback() {
            
            @Override
            public void callback() {
                mapEventHandler.fireEvent(new MarkerClickedEvent(id));
            }
        });
        
        markers.put(id, m);
    }

    @Override
    public void updateMarker(VKMarker marker) {
        Marker m = markers.get(marker.getId());
        if (m != null) {
            m.setPosition(asLatLng(marker.getPos()));
        }
    }
    
    @Override
    public void deleteMarkers(List<Integer> ids) {

        for (int i: ids) {
            markers.remove(i).setVisible(false);
        }
        
    }
    
    @Override
    public void setMapType(MapType mapType) {
        mapWidget.getMap().setMapTypeId(asGoogleMapType(mapType));
    }

    @Override
    public MapType getMapType() {
        return fromGoogleMapType(mapWidget.getMap().getMapTypeId());
    }
    
    @Override
    public void setControls(List<MapControl> mapControls) {
        MapOptions options = makeMapOptions();
        
        for (MapControl control: MapControl.values()) {
            switch (control) {
                case MAP_TYPE:
                    options.setMapTypeControl(mapControls.contains(control));
                    break;
                case NAVIGATION:
                    options.setNavigationControl(mapControls.contains(control));
                    break;
            }
            mapWidget.getMap().setOptions(options);
        }
    }

    @Override
    public List<MapControl> getControls() {
        return controls;
    }
    
    @Override
    public Widget asWidget() {
        return mapWidget;
    }
        
    private LatLon asLatLon(HasLatLng pos) {
        return new LatLon(pos.getLatitude(), pos.getLongitude());
    }

    private HasLatLng asLatLng(LatLon pos) {
        return new LatLng(pos.getLat(), pos.getLon());
    }
    
    private String asGoogleMapType(MapType mapType) {
        switch (mapType) {
            case HYBRID:
                return new MapTypeId().getHybrid();
            case SATELLITE:
                return new MapTypeId().getSatellite();
            case TERRAIN:
                return new MapTypeId().getTerrain();
            default:
                return new MapTypeId().getRoadmap();
        }
    }
    
    private MapType fromGoogleMapType(String mapTypeId) {
        if (new MapTypeId().getHybrid().equals(mapTypeId)) {
            return MapType.HYBRID;
        }
        if (new MapTypeId().getSatellite().equals(mapTypeId)) {
            return MapType.SATELLITE;
        }
        if (new MapTypeId().getTerrain().equals(mapTypeId)) {
            return MapType.TERRAIN;
        }
        return MapType.NORMAL;
    }
    
    private MapOptions makeMapOptions() {
        MapOptions options = new MapOptions();
        options.setDisableDefaultUI(true);
        options.setZoom(mapWidget.getMap().getZoom());
        options.setCenter(mapWidget.getMap().getCenter());
        options.setMapTypeId(mapWidget.getMap().getMapTypeId());
        options.setDraggable(true);
        return options;
    }
            
    private class MapEventHandler extends HandlerManager implements HasMapHandlers {
        
        public MapEventHandler(Object source) {
            super(source);
        }

        @Override
        public HandlerRegistration addMapMovedHandler(MapMovedHandler handler) {
            return addHandler(MapMovedEvent.TYPE, handler);
        }
        
        @Override
        public HandlerRegistration addMarkerMovedHandler(MarkerMovedHandler handler) {
            return addHandler(MarkerMovedEvent.TYPE, handler);
        }
        
        @Override
        public HandlerRegistration addMarkerClickedHandler(MarkerClickedHandler handler) {
            return addHandler(MarkerClickedEvent.TYPE, handler);
        }

        @Override
        public HandlerRegistration addMapTypeChanged(MapTypeChangedHandler handler) {
            return addHandler(MapTypeChangedHandler.MapTypeChangedEvent.TYPE, handler);
        }
    }

}
