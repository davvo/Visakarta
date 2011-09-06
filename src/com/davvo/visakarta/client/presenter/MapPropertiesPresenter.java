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
import com.davvo.visakarta.shared.NavControl;
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
        int getZoom();
        void setZoom(int zoom);
        MapType getMapType();
        void setMapType(MapType mapType);
        NavControl getNavControl();
        void setNavControl(NavControl navControl);
        List<MapControl> getMapControls();
        void setMapControls(List<MapControl> mapControls);
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
                LatLon center = new LatLon(view.getLat().getValue(), view.getLon().getValue());
                Map.getInstance().setCenter(center);
                Map.getInstance().setZoom(view.getZoom());
                Map.getInstance().setTitle(view.getTitle().getValue());
                Map.getInstance().setMapType(view.getMapType());
                Map.getInstance().setNavControl(view.getNavControl());
                Map.getInstance().setMapControls(view.getMapControls());
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
    
    private void populateView() {
        view.getLat().setValue(Map.getInstance().getCenter().getLat());
        view.getLon().setValue(Map.getInstance().getCenter().getLon());
        view.setZoom(Map.getInstance().getZoom());
        view.setMapType(Map.getInstance().getMapType());
        view.setNavControl(Map.getInstance().getNavControl());
        view.setMapControls(Map.getInstance().getMapControls());
    }
            
}
