package com.davvo.visakarta.client.presenter;

import java.util.List;

import com.davvo.visakarta.client.event.HasMapHandlers;
import com.davvo.visakarta.client.event.MarkerAddedEvent;
import com.davvo.visakarta.client.event.MarkerAddedHandler;
import com.davvo.visakarta.client.event.MarkerDeletedEvent;
import com.davvo.visakarta.client.event.MarkerDeletedHandler;
import com.davvo.visakarta.client.event.MarkerMovedEvent;
import com.davvo.visakarta.client.event.MarkerMovedHandler;
import com.davvo.visakarta.client.event.MarkerUpdatedEvent;
import com.davvo.visakarta.client.event.MapMovedEvent;
import com.davvo.visakarta.client.event.MapMovedHandler;
import com.davvo.visakarta.client.event.MapPropertiesChangedEvent;
import com.davvo.visakarta.client.event.MapPropertiesChangedHandler;
import com.davvo.visakarta.client.event.MarkerUpdatedHandler;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class MapPresenter implements Presenter {

    private EventBus eventBus;
    private Display view;
    
    public interface Display {
        LatLon getCenter();
        void setCenter(LatLon center, int zoom);
        int getZoom();
        void setZoom(int zoom);
        HasMapHandlers getMapHandler();
        void addMarker(VKMarker marker);
        void updateMarker(VKMarker marker);
        void deleteMarkers(List<Integer> index); 
        Widget asWidget();   
    }
    
    public MapPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
    }
    
    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(view.asWidget());
    }
    
    private void bind() {
        
        eventBus.addHandler(MapPropertiesChangedEvent.TYPE, new MapPropertiesChangedHandler() {
            
            @Override
            public void onMapPropertiesChanged(MapPropertiesChangedEvent event) {                
                if (event.getSource() != MapPresenter.this) {
                    view.setCenter(Map.getInstance().getCenter(), Map.getInstance().getZoom());
                }
            }

        });
                
        /**
         * 
         */
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
        
        view.getMapHandler().addMarkerMovedHandler(new MarkerMovedHandler() {
            
            @Override
            public void onMarkerMoved(MarkerMovedEvent event) {
                Map.getInstance().getMarker(event.getId()).setPos(event.getPos());
                eventBus.fireEventFromSource(new MarkerUpdatedEvent(event.getId()), MapPresenter.this);
            }
        });
        
    }

}
