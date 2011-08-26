package com.davvo.visakarta.client.view;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import com.davvo.visakarta.client.event.HasMapHandlers;
import com.davvo.visakarta.client.event.MarkerMovedEvent;
import com.davvo.visakarta.client.event.MarkerMovedHandler;
import com.davvo.visakarta.client.event.MapMovedEvent;
import com.davvo.visakarta.client.event.MapMovedHandler;
import com.davvo.visakarta.client.presenter.MapPresenter.Display;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.event.MapMoveHandler;
import com.google.gwt.maps.client.event.MarkerDragHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.Widget;

public class MapView implements Display {

    private MapEventHandler mapEventHandler = new MapEventHandler();    
    private MapWidget mapWidget;
    private List<Marker> markers = new ArrayList<Marker>();
    
    public MapView() {
        createMapWidget();
        bind();
    }

    private void createMapWidget() {
        mapWidget = new MapWidget(LatLng.newInstance(0, 0), 2);
        mapWidget.setSize("100%", "100%");

        // Add some controls
        mapWidget.addControl(new SmallMapControl());        
    }
    
    private void bind() {
        
        mapWidget.addMapMoveHandler(new MapMoveHandler() {

            @Override
            public void onMove(MapMoveEvent event) {
                mapEventHandler.fireEvent(new MapMovedEvent());    
            }
            
        });
        
    }
    
    @Override
    public LatLon getCenter() {
        return asLatLon(mapWidget.getCenter());
    }

    @Override
    public void setCenter(LatLon center, int zoom) {
        mapWidget.setCenter(asLatLng(center), zoom);
    }

    @Override
    public int getZoom() {
        return mapWidget.getZoomLevel();
    }

    @Override
    public void setZoom(int zoom) {
        mapWidget.setZoomLevel(zoom);
    }

    @Override
    public HasMapHandlers getMapHandler() {
        if (mapEventHandler == null) {
            mapEventHandler = new MapEventHandler();
        }
        return mapEventHandler;
    }

    @Override
    public void addMarker(VKMarker marker) {
        
        MarkerOptions options = MarkerOptions.newInstance();
        options.setDraggable(true);
        
        Marker m = new Marker(asLatLng(marker.getPos()), options);
        
        m.addMarkerDragHandler(new MarkerDragHandler() {
            
            @Override
            public void onDrag(MarkerDragEvent event) {
                int index = markers.indexOf(event.getSender());
                mapEventHandler.fireEvent(new MarkerMovedEvent(index, asLatLon(event.getSender().getLatLng())));                
            }
        });
        
        mapWidget.addOverlay(m);
        markers.add(m);
    }

    @Override
    public void deleteMarkers(List<Integer> index) {
        Collection<Marker> toRemove = new ArrayList<Marker>();
        
        for (int i: index) {
            Marker m = markers.get(i);
            toRemove.add(m);
            mapWidget.removeOverlay(m);
        }
        
        markers.removeAll(toRemove);
    }
    
    @Override
    public Widget asWidget() {
        return mapWidget;
    }
        
    private LatLon asLatLon(LatLng pos) {
        return new LatLon(pos.getLatitude(), pos.getLongitude());
    }

    private LatLng asLatLng(LatLon pos) {
        return LatLng.newInstance(pos.getLat(), pos.getLon());
    }
        
    private class MapEventHandler extends HandlerManager implements HasMapHandlers {
        
        public MapEventHandler() {
            super(null);
        }

        @Override
        public HandlerRegistration addMapMoveHandler(MapMovedHandler handler) {
            return addHandler(MapMovedEvent.TYPE, handler);
        }
        
        @Override
        public HandlerRegistration addMarkerMovedHandler(MarkerMovedHandler handler) {
            return addHandler(MarkerMovedEvent.TYPE, handler);
        }
    }


}
