package com.davvo.visakarta.client.view;

import java.util.HashMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.davvo.visakarta.client.presenter.MapPresenter.Display;
import com.davvo.visakarta.client.ui.HasValueImpl;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.MapControl;
import com.davvo.visakarta.shared.MapType;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.InfoWindow;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.EventCallback;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class MapView implements Display {

    private Map<Integer, Marker> markers = new HashMap<Integer, Marker>();
    private Map<Integer, InfoWindow> infoWindows = new HashMap<Integer, InfoWindow>();
    private MapWidget mapWidget;
    
    HasValue<LatLon> center;
    HasValue<Integer> zoom;
    HasValue<MapType> mapType;
    HasValue<Collection<MapControl>> controls;
    
    HasValueChangeHandlers<Integer> markerHandler = new MarkerHandler();
    
    public MapView(HasWidgets parent) {
        final MapOptions options = new MapOptions();
        mapWidget = new MapWidget(options);
        mapWidget.setSize("100%", "100%");
        parent.add(mapWidget);
        
        center = new HasValueImpl<LatLon>();
        center.addValueChangeHandler(new ValueChangeHandler<LatLon>() {
            @Override
            public void onValueChange(ValueChangeEvent<LatLon> event) {
                mapWidget.getMap().setCenter(asLatLng(event.getValue()));
            }
        });
        
        zoom = new HasValueImpl<Integer>();
        zoom.addValueChangeHandler(new ValueChangeHandler<Integer>() {

            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                mapWidget.getMap().setZoom(event.getValue());
            }
        });
        
        mapType = new HasValueImpl<MapType>();
        mapType.addValueChangeHandler(new ValueChangeHandler<MapType>() {

            @Override
            public void onValueChange(ValueChangeEvent<MapType> event) {
                mapWidget.getMap().setMapTypeId(asGoogleMapType(event.getValue()));
            }
        });
     
        controls = new HasValueImpl<Collection<MapControl>>();
        controls.addValueChangeHandler(new ValueChangeHandler<Collection<MapControl>>() {

            @Override
            public void onValueChange(ValueChangeEvent<Collection<MapControl>> event) {
                setControls(event.getValue());
            }
        });
        
        
        bind();
    }
    
    private void bind() {
        Event.addListener(mapWidget.getMap(), "bounds_changed", new EventCallback() {
            
            @Override
            public void callback() {
                center.setValue(asLatLon(mapWidget.getMap().getCenter()), true);
                zoom.setValue(mapWidget.getMap().getZoom(), true);
            }
        });
        
        Event.addListener(mapWidget.getMap(), "maptypeid_changed", new EventCallback() {
            
            @Override
            public void callback() {
                mapType.setValue(fromGoogleMapType(mapWidget.getMap().getMapTypeId()));
            }
        });
    }
    
    @Override
    public HasValue<LatLon> getCenter() {
        return center;
    }

    @Override
    public HasValue<Integer> getZoom() {
        return zoom;
    }

    @Override
    public HasValue<MapType> getMapType() {
        return mapType;
    }
    
    @Override
    public HasValue<Collection<MapControl>> getControls() {
        return controls;
    }
    
    @Override
    public void setMarkers(Collection<VKMarker> markers) {
        // Remove old
        retainMarkers(markers);
        
        // Add new
        for (VKMarker m: markers) {
            setMarker(m);
        }
    }
    
    @Override
    public void setMarker(VKMarker marker) {
        if (this.markers.containsKey(marker.getId())) {
            updateMarker(marker);
        } else {
            addMarker(marker);
        }
    }

    @Override
    public HasValueChangeHandlers<Integer> getMarkerHandler() {
        return markerHandler;
    }
    
    @Override
    public LatLon getMarkerPosition(int id) {        
        return asLatLon(markers.get(id).getPosition());
    }
    
    @Override
    public Widget asWidget() {
        return mapWidget;
    }
            
    private void retainMarkers(Collection<VKMarker> ms) {
        Iterator<Integer> it = markers.keySet().iterator();
        while (it.hasNext()) {
            int id = it.next();
            boolean keep = false;
            for (VKMarker m: ms) {
                if (m.getId() == id) {
                    keep = true;
                    break;
                }
            }
            if (!keep) {
                markers.get(id).setVisible(false);
                infoWindows.remove(id);
                it.remove();
            }
        }
    }
    
    private void addMarker(VKMarker marker) {
        
        MarkerOptions options = new MarkerOptions();        
        options.setDraggable(true);
        options.setVisible(true);
        options.setMap(mapWidget.getMap());
        options.setPosition(asLatLng(marker.getPos()));
        
        if (marker.isInfoWindow()) {
            InfoWindow infoWindow = new InfoWindow();
            infoWindow.setContent(marker.getInfoWindowContent());
            infoWindows.put(marker.getId(), infoWindow);
        }
        
        final Marker m = new Marker(options);
        
        final int id = marker.getId();
        
        Event.addListener(m, "drag", new EventCallback() {
            
            @Override
            public void callback() {
                ValueChangeEvent.fire(markerHandler, id);
            }
        });
        
        Event.addListener(m, "click", new EventCallback() {
            
            @Override
            public void callback() {
                if (infoWindows.containsKey(id)) {
                    infoWindows.get(id).open(mapWidget.getMap(), m);
                }
                //mapEventHandler.fireEvent(new MarkerClickedEvent(id));
            }
        });
        
        markers.put(id, m);
    }

    private void updateMarker(VKMarker marker) {
        Marker m = markers.get(marker.getId());
        if (m != null) {
            m.setPosition(asLatLng(marker.getPos()));
            if (marker.isInfoWindow()) {
                InfoWindow infoWindow = infoWindows.get(marker.getId());
                if (infoWindow == null) {
                    infoWindow = new InfoWindow();
                    infoWindows.put(marker.getId(), infoWindow);
                }
                infoWindow.setContent(marker.getInfoWindowContent());
            } else {
                InfoWindow infoWindow = infoWindows.get(marker.getId());
                if (infoWindow != null) {
                    infoWindow.close();
                }
                infoWindows.remove(marker.getId());
            }
        }
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
            
    private void setControls(Collection<MapControl> mapControls) {
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
    
    private class MarkerHandler implements HasValueChangeHandlers<Integer> {

        HandlerManager mgm = new HandlerManager(null);
        
        @Override
        public void fireEvent(GwtEvent<?> event) {
            mgm.fireEvent(event);
        }

        @Override
        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
            return mgm.addHandler(ValueChangeEvent.getType(), handler);
        }
        
    }
    
}
