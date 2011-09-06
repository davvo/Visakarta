package com.davvo.visakarta.client.presenter;

import java.util.List;

import com.davvo.visakarta.client.event.MapPropertiesChangedEvent;
import com.davvo.visakarta.client.event.MapPropertiesChangedHandler;
import com.davvo.visakarta.client.event.ShowMapPropertiesEvent;
import com.davvo.visakarta.client.event.ShowMapPropertiesHandler;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.MapControl;
import com.davvo.visakarta.shared.MapType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class MapPropertiesPresenter  {

    private final EventBus eventBus;
    private final Display view;
    
    public interface Display {
        HasClickHandlers getSaveButton();
        HasClickHandlers getCancelButton();
        HasValue<String> getTitle();
        HasValue<Double> getLat();
        HasValue<Double> getLon();
        List<MapControl> getControls();
        void setControls(List<MapControl> controls);
        
        int getZoom();
        void setZoom(int zoom);
        MapType getMapType();
        void setMapType(MapType mapType);
        void show();
        void hide();
        Widget asWidget();
    }
    
    public MapPropertiesPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
        bind();
    }
    
    private void bind() {
        eventBus.addHandler(MapPropertiesChangedEvent.TYPE, new MapPropertiesChangedHandler() {
            
            @Override
            public void onMapPropertiesChanged(MapPropertiesChangedEvent event) {
                if (event.getSource() != MapPropertiesPresenter.this) {
                    populateView();
                }
            }        
        });
        
        eventBus.addHandler(ShowMapPropertiesEvent.TYPE, new ShowMapPropertiesHandler() {
            
            @Override
            public void onShowMapProperties(ShowMapPropertiesEvent event) {
                populateView();
                view.show();
            }
        });
        
        view.getSaveButton().addClickHandler(new ClickHandler() {  
            
            @Override
            public void onClick(ClickEvent event) {
                populateMap();
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPropertiesPresenter.this);
            }            
        });
        
        view.getCancelButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                view.hide();
            }
        });
        
    }

    private void populateMap() {
        Map map = Map.getInstance();
        map.setCenter(new LatLon(view.getLat().getValue(), view.getLon().getValue()));
        map.setZoom(view.getZoom());
        map.setTitle(view.getTitle().getValue());
        map.setMapType(view.getMapType());
        map.setControls(view.getControls());
    }
    
    private void populateView() {
        Map map = Map.getInstance();
        view.getLat().setValue(map.getCenter().getLat());
        view.getLon().setValue(map.getCenter().getLon());
        view.setZoom(map.getZoom());
        view.setMapType(map.getMapType());
        view.setControls(map.getControls());
    }
            
}
