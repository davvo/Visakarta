package com.davvo.visakarta.client.presenter;

import com.davvo.visakarta.client.event.MapPropertiesChangedEvent;


import com.davvo.visakarta.client.event.MapPropertiesEventHandler;
import com.davvo.visakarta.client.event.ShowMapPropertiesEvent;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.MapPosition;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;

public class MapPropertiesPresenter  {

    private final EventBus eventBus;
    private final Display view;
    
    private MapPosition mapPos;
    
    private MapPropertiesEventHandler eventHandler;
    
    public interface Display {
        HasClickHandlers getSaveButton();
        HasClickHandlers getCancelButton();
        String getTitle();
        void setTitle(String title);
        LatLon getCenter();
        void setCenter(LatLon center);
        int getZoom();
        void setZoom(int zoom);
        void show();
        void hide();
        Widget asWidget();
    }
    
    public MapPropertiesPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
        eventHandler = new MapPropertiesEventHandlerImpl();
        bind();
    }
    
    private void bind() {
        eventBus.addHandler(MapPropertiesChangedEvent.TYPE, eventHandler);
        
        eventBus.addHandler(ShowMapPropertiesEvent.TYPE, eventHandler);
        
        view.getSaveButton().addClickHandler(new ClickHandler() {  
            
            @Override
            public void onClick(ClickEvent event) {
                MapPosition newPos = new MapPosition(view.getCenter(), view.getZoom());
                if (mapPos == null || !mapPos.equals(newPos)) {
                    mapPos = newPos;
                    eventBus.fireEventFromSource(new MapPropertiesChangedEvent(newPos), MapPropertiesPresenter.this);
                }
            }            
        });
        
        view.getCancelButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                view.hide();
            }
        });
        
    }
    
    private class MapPropertiesEventHandlerImpl implements MapPropertiesEventHandler {
            
        @Override
        public void onShowMapProperties(ShowMapPropertiesEvent event) {
            view.setCenter(mapPos.getCenter());
            view.setZoom(mapPos.getZoom());
            view.show();
        }
            
        @Override
        public void onMapPropertiesChanged(MapPropertiesChangedEvent event) {
            if (event.getSource() != MapPropertiesPresenter.this) {
                mapPos = event.getMapPosition();
                view.setCenter(mapPos.getCenter());
                view.setZoom(mapPos.getZoom());
            }
        }        
    }
        
}
