package com.davvo.visakarta.client.presenter;

import java.util.Collection;

import com.davvo.visakarta.client.event.MapPropertiesChangedEvent;
import com.davvo.visakarta.client.event.MapPropertiesChangedHandler;
import com.davvo.visakarta.client.event.ShowMapPropertiesEvent;
import com.davvo.visakarta.client.event.ShowMapPropertiesHandler;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.MapControl;
import com.davvo.visakarta.shared.MapType;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class MapPropertiesPresenter implements Presenter {

    private final EventBus eventBus;
    private final Display view;
    
    public interface Display {
        HasValue<LatLon> getCenter();
        HasValue<Integer> getZoom();
        HasValue<MapType> getMapType();
        HasValue<Collection<MapControl>> getControls();
        
        void showMe();
        void hide();
        Widget asWidget();
    }
    
    public MapPropertiesPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
    }
    
    public void go() {
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
                if (event.getSource() != MapPropertiesPresenter.this) { 
                    if (event.isVisible()) {
                        populateView();
                        view.showMe();
                    } else {
                        view.hide();
                    }
                }
            }
        });

        view.getCenter().addValueChangeHandler(new ValueChangeHandler<LatLon>() {

            @Override
            public void onValueChange(ValueChangeEvent<LatLon> event) {
                populateMap();
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPropertiesPresenter.this);
            }
        });
        
        view.getControls().addValueChangeHandler(new ValueChangeHandler<Collection<MapControl>>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<Collection<MapControl>> event) {
                populateMap();
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPropertiesPresenter.this);
            }
        });
        
        view.getMapType().addValueChangeHandler(new ValueChangeHandler<MapType>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<MapType> event) {
                populateMap();
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPropertiesPresenter.this);
            }
        });
        
        view.getZoom().addValueChangeHandler(new ValueChangeHandler<Integer>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                populateMap();
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), MapPropertiesPresenter.this);
            }
        });

    }

    private void populateMap() {
        Map map = Map.getInstance();
        if (view.getCenter().getValue() != null) {
            map.setCenter(view.getCenter().getValue());
        }
        map.setZoom(view.getZoom().getValue());
        map.setMapType(view.getMapType().getValue());
        map.setControls(view.getControls().getValue());
    }
    
    private void populateView() {
        Map map = Map.getInstance();
        view.getCenter().setValue(map.getCenter());
        view.getZoom().setValue(map.getZoom());
        view.getMapType().setValue(map.getMapType());
        view.getControls().setValue(map.getControls());
    }
            
}
