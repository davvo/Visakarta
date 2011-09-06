package com.davvo.visakarta.client.view;

import java.util.HashMap;

import java.util.ArrayList;
import java.util.Collections;
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
import com.davvo.visakarta.shared.NavControl;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.Control;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.control.LargeMapControl3D;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.control.OverviewMapControl;
import com.google.gwt.maps.client.control.ScaleControl;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.control.SmallZoomControl;
import com.google.gwt.maps.client.control.SmallZoomControl3D;
import com.google.gwt.maps.client.event.MapMoveHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.Widget;

public class MapView implements Display {

    private MapEventHandler mapEventHandler = new MapEventHandler(this);    
    private Map<Integer, Marker> markers = new HashMap<Integer, Marker>();
    private MapWidget mapWidget;
    
    private Control curNavControl;
    private List<Control> curMapControls = new ArrayList<Control>();
    
    public MapView() {
        createMapWidget();
        bind();
    }

    private void createMapWidget() {
        mapWidget = new MapWidget(LatLng.newInstance(0, 0), 2);
        mapWidget.setSize("100%", "100%");        
    }
    
    private void bind() {
        
        mapWidget.addMapMoveHandler(new MapMoveHandler() {

            @Override
            public void onMove(MapMoveEvent event) {
                mapEventHandler.fireEvent(new MapMovedEvent());    
            }
            
        });
        
        mapWidget.addMapTypeChangedHandler(new com.google.gwt.maps.client.event.MapTypeChangedHandler() {
            
            @Override
            public void onTypeChanged(MapTypeChangedEvent event) {
                mapEventHandler.fireEvent(new MapTypeChangedHandler.MapTypeChangedEvent());
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
        return mapEventHandler;
    }

    @Override
    public void addMarker(VKMarker marker) {
        
        MarkerOptions options = MarkerOptions.newInstance();
        options.setDraggable(true);
        
        Marker m = new Marker(asLatLng(marker.getPos()), options);        

        final int id = marker.getId();
        
        m.addMarkerDragHandler(new MarkerDragHandler() {
            
            @Override
            public void onDrag(MarkerDragEvent event) {
                mapEventHandler.fireEvent(new MarkerMovedEvent(id, asLatLon(event.getSender().getLatLng())));                
            }
        });
        
        m.addMarkerClickHandler(new MarkerClickHandler() {
            
            @Override
            public void onClick(MarkerClickEvent event) {
                mapEventHandler.fireEvent(new MarkerClickedEvent(id));
            }
        });
        
        mapWidget.addOverlay(m);
        markers.put(marker.getId(), m);
    }

    @Override
    public void updateMarker(VKMarker marker) {
        Marker m = markers.get(marker.getId());
        if (m != null) {
            m.setLatLng(asLatLng(marker.getPos()));
        }
    }
    
    @Override
    public void deleteMarkers(List<Integer> ids) {

        for (int i: ids) {
            Marker m = markers.remove(i);
            mapWidget.removeOverlay(m);
        }
        
    }
    
    @Override
    public void setMapType(MapType mapType) {
        mapWidget.setCurrentMapType(asGoogleMapType(mapType));
    }

    @Override
    public MapType getMapType() {
        return fromGoogleMapType(mapWidget.getCurrentMapType());
    }
    
    
    @Override
    public void setNavControl(NavControl navControl) {
        if (curNavControl != null) {
            mapWidget.removeControl(curNavControl);
        }
        curNavControl = asGoogleNavControl(navControl);
        if (curNavControl != null) {
            mapWidget.addControl(curNavControl);
        }
    }

    @Override
    public NavControl getNavControl() {
        return fromGoogleNavControl(curNavControl);
    }

    @Override
    public void setMapControls(List<MapControl> mapControls) {
        for (Control control: curMapControls) {
            mapWidget.removeControl(control);
        }
        curMapControls.clear();
        for (MapControl mapControl: mapControls) {
            Control control = asGoogleMapControl(mapControl);
            mapWidget.addControl(control);
            curMapControls.add(control);
        }
    }

    @Override
    public List<MapControl> getMapControls() {
        if (curMapControls.isEmpty()) {
            return Collections.emptyList();
        }
        List<MapControl> mapControls = new ArrayList<MapControl>(curMapControls.size());
        for (Control control: curMapControls) {
            mapControls.add(fromGoogleMapControl(control));
        }
        return mapControls;
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
    
    private com.google.gwt.maps.client.MapType asGoogleMapType(MapType mapType) {
        switch (mapType) {
            case HYBRID:
                return com.google.gwt.maps.client.MapType.getHybridMap();
            case SATELLITE:
                return com.google.gwt.maps.client.MapType.getSatelliteMap();
            default:
                return com.google.gwt.maps.client.MapType.getNormalMap();
        }
    }
    
    private MapType fromGoogleMapType(com.google.gwt.maps.client.MapType mapType) {
        if (mapType == com.google.gwt.maps.client.MapType.getHybridMap()) {
            return MapType.HYBRID;
        } else if (mapType == com.google.gwt.maps.client.MapType.getSatelliteMap()) {
            return MapType.SATELLITE;
        }
        return MapType.NORMAL;
    }
        
    private Control asGoogleNavControl(NavControl navControl) {
        switch (navControl) {
            case LARGE:
                return new LargeMapControl();
            case LARGE_3D:
                return new LargeMapControl3D();
            case SMALL:
                return new SmallMapControl();
            case SMALL_ZOOM:
                return new SmallZoomControl();
            case SMALL_ZOOM_3D:
                return new SmallZoomControl3D();
            default:
                return null;
        }
    }
    
    private NavControl fromGoogleNavControl(Control control) {
        if (control instanceof LargeMapControl) {
            return NavControl.LARGE;
        } else if (control instanceof LargeMapControl3D) {
            return NavControl.LARGE_3D;
        } else if (control instanceof SmallMapControl) {
            return NavControl.SMALL;
        } else if (control instanceof SmallZoomControl) {
            return NavControl.SMALL_ZOOM;
        } else if (control instanceof SmallZoomControl3D) {
            return NavControl.SMALL_ZOOM_3D;
        }
        return NavControl.NONE;        
    }
    
    private Control asGoogleMapControl(MapControl mapControl) {
        switch (mapControl) {
            case MAP_TYPE:
                return new MapTypeControl();
            case OVERVIEW:
                return new OverviewMapControl();
            case SCALE:
                return new ScaleControl();
            default:
                return null;
        }
    }
    
    private MapControl fromGoogleMapControl(Control control) {
        if (control instanceof MapTypeControl) {
            return MapControl.MAP_TYPE;
        } else if (control instanceof OverviewMapControl) {
            return MapControl.OVERVIEW;
        } else if (control instanceof ScaleControl) {
            return MapControl.SCALE;
        }
        return null;
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
