package com.davvo.visakarta.client.presenter;

import java.util.List;

import com.davvo.visakarta.client.event.HasMapHandlers;
import com.davvo.visakarta.client.event.MapTypeChangedHandler;
import com.davvo.visakarta.client.event.MarkerAddedEvent;
import com.davvo.visakarta.client.event.MarkerAddedHandler;
import com.davvo.visakarta.client.event.MarkerClickedEvent;
import com.davvo.visakarta.client.event.MarkerClickedHandler;
import com.davvo.visakarta.client.event.MarkerDeletedEvent;
import com.davvo.visakarta.client.event.MarkerDeletedHandler;
import com.davvo.visakarta.client.event.MarkerMovedEvent;
import com.davvo.visakarta.client.event.MarkerMovedHandler;
import com.davvo.visakarta.client.event.MarkerUpdatedEvent;
import com.davvo.visakarta.client.event.MapMovedHandler;
import com.davvo.visakarta.client.event.MapPropertiesChangedEvent;
import com.davvo.visakarta.client.event.MapPropertiesChangedHandler;
import com.davvo.visakarta.client.event.MarkerUpdatedHandler;
import com.davvo.visakarta.client.event.ShowMarkerDetailsEvent;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.MapControl;
import com.davvo.visakarta.shared.MapType;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;

public class MapPresenter {

    private EventBus eventBus;
    private Display view;
    
    public interface Display {
        LatLon getCenter();
        void setCenter(LatLon center);
        int getZoom();
        void setZoom(int zoom);
        HasMapHandlers getMapHandler();
        void addMarker(VKMarker marker);
        void updateMarker(VKMarker marker);
        void deleteMarkers(List<Integer> index);
        void setMapType(MapType mapType);        
        MapType getMapType();
        void setControls(List<MapControl> controls);
        List<MapControl> getControls();
        Widget asWidget();   
    }
    
    public MapPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
        bind();
        populateView();
    }
        
    private void bind() {
        
        eventBus.addHandler(MapPropertiesChangedEvent.TYPE, new MapPropertiesChangedHandler() {
            
            @Override
            public void onMapPropertiesChanged(MapPropertiesChangedEvent event) {                
                if (event.getSource() != MapPresenter.this) {
                    populateView();
                }
            }

        });
                
        eventBus.addHandler(MarkerAddedEvent.TYPE, new MarkerAddedHandler() {
            
            @Override
            public void onMarkerAdded(MarkerAddedEvent event) {
                view.addMarker(Map.getInstance().getMarker(event.getId()));
            }
            
        });
        
        eventBus.addHandler(MarkerDeletedEvent.TYPE, new MarkerDeletedHandler() {
            
            @Override
            public void onMarkerDeleted(MarkerDeletedEvent event) {
                view.deleteMarkers(event.getIds());
            }
            
        });
        
        eventBus.addHandler(MarkerUpdatedEvent.TYPE, new MarkerUpdatedHandler() {
            
            @Override
            public void onMarkerUpdated(MarkerUpdatedEvent event) {
                view.updateMarker(Map.getInstance().getMarker(event.getId()));
            }
            
        });
        
        view.getMapHandler().addMapMovedHandler(new MapMovedHandler() {
            
            @Override
            public void onMapMoved(MapMovedEvent event) {
                Map.getInstance().setCenter(view.getCenter());
                Map.getInstance().setZoom(view.getZoom());
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPresenter.this);
            }            
        });
        
        view.getMapHandler().addMapTypeChanged(new MapTypeChangedHandler() {

            @Override
            public void onMapTypeChanged(MapTypeChangedEvent event) {
                Map.getInstance().setMapType(view.getMapType());
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPresenter.this);
            }
            
        });
        
        view.getMapHandler().addMarkerMovedHandler(new MarkerMovedHandler() {
            
            @Override
            public void onMarkerMoved(MarkerMovedEvent event) {
                Map.getInstance().getMarker(event.getId()).setPos(event.getPos());
                eventBus.fireEventFromSource(new MarkerUpdatedEvent(event.getId()), MapPresenter.this);
            }
        });
        
        view.getMapHandler().addMarkerClickedHandler(new MarkerClickedHandler() {
            
            @Override
            public void onMarkerClicked(MarkerClickedEvent event) {
                eventBus.fireEvent(new ShowMarkerDetailsEvent(event.getId()));
            }
        });
        
    }

    private void populateView() {
        view.setCenter(Map.getInstance().getCenter());
        view.setZoom(Map.getInstance().getZoom());        
        view.setMapType(Map.getInstance().getMapType());
        view.setControls(Map.getInstance().getControls());
    }
    
}
