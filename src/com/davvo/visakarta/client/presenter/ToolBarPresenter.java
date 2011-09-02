package com.davvo.visakarta.client.presenter;

import com.davvo.visakarta.client.event.SaveMapEvent;
import com.davvo.visakarta.client.event.ShowMapPropertiesEvent;
import com.davvo.visakarta.client.event.ShowMarkersEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class ToolBarPresenter implements Presenter {

    private EventBus eventBus;
    private Display view;
    
    public ToolBarPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
    }
    
    public interface Display {
        HasClickHandlers getMapPropertiesButton();
        HasClickHandlers getMarkersButton();
        HasClickHandlers getSaveMapButton();
        Widget asWidget();
    }
    
    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(view.asWidget());
    }

    private void bind() {
        view.getMapPropertiesButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new ShowMapPropertiesEvent());
            }
            
        });
        
        view.getMarkersButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new ShowMarkersEvent());
            }
            
        });
        
        view.getSaveMapButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new SaveMapEvent());
            }
        });
    }
    
}
