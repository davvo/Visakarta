package com.davvo.visakarta.client.presenter;

import com.davvo.visakarta.client.event.SaveMapEvent;

import com.davvo.visakarta.client.event.SaveMapHandler;
import com.davvo.visakarta.client.event.ShowMapPropertiesEvent;
import com.davvo.visakarta.client.event.ShowMapPropertiesHandler;
import com.davvo.visakarta.client.event.ShowMarkersEvent;
import com.davvo.visakarta.client.event.ShowMarkersHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class ToolBarPresenter implements Presenter {

    private EventBus eventBus;
    private Display view;
    
    public interface Display {
        HasValue<Boolean> getMapPropertiesButton();
        HasValue<Boolean> getMarkersButton();
        HasValue<Boolean> getSaveMapButton();
        void show();
        void hide();
        Widget asWidget();
    }
    
    public ToolBarPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
    }
    
    @Override
    public void go() {
        bind();
        view.show();
    }
            
    private void bind() {
        view.getMapPropertiesButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                eventBus.fireEventFromSource(new ShowMapPropertiesEvent(event.getValue()), ToolBarPresenter.this);
            }
        });
            
        view.getMarkersButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                eventBus.fireEventFromSource(new ShowMarkersEvent(event.getValue()), ToolBarPresenter.this);
            }
        });
        
        view.getSaveMapButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                eventBus.fireEventFromSource(new SaveMapEvent(event.getValue()), ToolBarPresenter.this);
            }
        });
        
        
        eventBus.addHandler(ShowMapPropertiesEvent.TYPE, new ShowMapPropertiesHandler() {
            
            @Override
            public void onShowMapProperties(ShowMapPropertiesEvent event) {
                if (event.getSource() != ToolBarPresenter.this) {
                    view.getMapPropertiesButton().setValue(event.isVisible());
                }
            }
        });

        eventBus.addHandler(ShowMarkersEvent.TYPE, new ShowMarkersHandler() {
            
            @Override
            public void onShowMarkers(ShowMarkersEvent event) {
                if (event.getSource() != ToolBarPresenter.this) {
                    view.getMarkersButton().setValue(event.isVisible());
                }
            }
        });
        
        eventBus.addHandler(SaveMapEvent.TYPE, new SaveMapHandler() {
            
            @Override
            public void onSaveMap(SaveMapEvent event) {
                if (event.getSource() != ToolBarPresenter.this) {
                    view.getSaveMapButton().setValue(event.isVisible());
                }
            }
        });
    
    
    }

}
