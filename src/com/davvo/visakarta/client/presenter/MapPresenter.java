package com.davvo.visakarta.client.presenter;

import java.util.Collection;

import com.davvo.visakarta.client.event.MarkerAddedEvent;
import com.davvo.visakarta.client.event.MarkerAddedHandler;
import com.davvo.visakarta.client.event.MarkerDeletedEvent;
import com.davvo.visakarta.client.event.MarkerDeletedHandler;
import com.davvo.visakarta.client.event.MarkerUpdatedEvent;
import com.davvo.visakarta.client.event.MapPropertiesChangedEvent;
import com.davvo.visakarta.client.event.MapPropertiesChangedHandler;
import com.davvo.visakarta.client.event.MarkerUpdatedHandler;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.MapControl;
import com.davvo.visakarta.shared.MapType;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class MapPresenter implements Presenter {

    private EventBus eventBus;
    private Display view;
    
    public interface Display {
        HasValue<LatLon> getCenter();
        HasValue<Integer> getZoom();
        HasValue<MapType> getMapType();
        HasValue<Collection<MapControl>> getControls();
        
        void setMarkers(Collection<VKMarker> markers);
        void setMarker(VKMarker marker);
        
        HasValueChangeHandlers<Integer> getMarkerHandler();
        LatLon getMarkerPosition(int id);
        
        Widget asWidget();
    }
    
    public MapPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
    }

    @Override
    public void go() {
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
                view.setMarkers(Map.getInstance().getMarkers());
            }
            
        });
        
        eventBus.addHandler(MarkerDeletedEvent.TYPE, new MarkerDeletedHandler() {
            
            @Override
            public void onMarkerDeleted(MarkerDeletedEvent event) {
                view.setMarkers(Map.getInstance().getMarkers());
            }
            
        });
        
        eventBus.addHandler(MarkerUpdatedEvent.TYPE, new MarkerUpdatedHandler() {

            @Override
            public void onMarkerUpdated(MarkerUpdatedEvent event) {
                if (event.getSource() != MapPresenter.this) {
                    view.setMarker(Map.getInstance().getMarker(event.getId()));
                }
            }
            
        });
        
        view.getCenter().addValueChangeHandler(new ValueChangeHandler<LatLon>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<LatLon> event) {
                Map.getInstance().setCenter(view.getCenter().getValue());
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPresenter.this);
            }
        });
        
        view.getZoom().addValueChangeHandler(new ValueChangeHandler<Integer>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                Map.getInstance().setZoom(view.getZoom().getValue());
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPresenter.this);
            }
        });
        
        view.getMarkerHandler().addValueChangeHandler(new ValueChangeHandler<Integer>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                VKMarker marker = Map.getInstance().getMarker(event.getValue());
                marker.setPos(view.getMarkerPosition(marker.getId()));
                eventBus.fireEventFromSource(new MarkerUpdatedEvent(marker.getId()), MapPresenter.this);
            }
        });
                
    }

    private void populateView() {
        view.getCenter().setValue(Map.getInstance().getCenter());
        view.getZoom().setValue(Map.getInstance().getZoom());        
        view.getMapType().setValue(Map.getInstance().getMapType());
        view.getControls().setValue(Map.getInstance().getControls());
    }

}
