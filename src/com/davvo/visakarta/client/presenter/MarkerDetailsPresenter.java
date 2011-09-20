package com.davvo.visakarta.client.presenter;

import com.davvo.visakarta.client.event.MarkerDeletedEvent;
import com.davvo.visakarta.client.event.MarkerDeletedHandler;
import com.davvo.visakarta.client.event.MarkerUpdatedEvent;
import com.davvo.visakarta.client.event.MarkerUpdatedHandler;
import com.davvo.visakarta.client.event.ShowMarkerDetailsEvent;
import com.davvo.visakarta.client.event.ShowMarkerDetailsHandler;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class MarkerDetailsPresenter implements Presenter {

    private final EventBus eventBus;
    private final Display view;
    
    private VKMarker marker;
    
    public interface Display {
        HasClickHandlers getOkButton();
        HasClickHandlers getCancelButton();
        HasValue<LatLon> getPosition();
        HasValue<Boolean> isInfoWindow();
        HasValue<String> getInfoWindowContent();
        void setTitle(String title);
        void hide();
        void show();
        Widget asWidget();
    }
    
    public MarkerDetailsPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
    }
    
    public void go() {
        bind();
    }
    
    private void bind() {
        eventBus.addHandler(MarkerUpdatedEvent.TYPE, new MarkerUpdatedHandler() {
            
            @Override
            public void onMarkerUpdated(MarkerUpdatedEvent event) {
                if (event.getSource() != this && marker != null && event.getId() == marker.getId()) {
                    populateView();
                }
                
            }
        });
        
        eventBus.addHandler(ShowMarkerDetailsEvent.TYPE, new ShowMarkerDetailsHandler() {
                        
            @Override
            public void onShowMarkerDetails(ShowMarkerDetailsEvent event) {
                marker = Map.getInstance().getMarker(event.getId()); 
                populateView();
                view.show();
            }
        });
        
        eventBus.addHandler(MarkerDeletedEvent.TYPE, new MarkerDeletedHandler() {

            @Override
            public void onMarkerDeleted(MarkerDeletedEvent event) {
                if (marker != null && !Map.getInstance().getMarkers().contains(marker)) {
                    view.hide();
                }
            }
        });
        
        view.getOkButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                populateMarker();
                eventBus.fireEventFromSource(new MarkerUpdatedEvent(marker.getId()), MarkerDetailsPresenter.this);                
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
        view.setTitle("Marker " + marker.getId());
        view.getPosition().setValue(marker.getPos());
        view.isInfoWindow().setValue(marker.isInfoWindow());
        view.getInfoWindowContent().setValue(marker.getInfoWindowContent().replace("<br/>", "\n"));
    }
    
    private void populateMarker() {
        marker.setPos(view.getPosition().getValue());
        marker.setInfoWindow(view.isInfoWindow().getValue());
        marker.setInfoWindowContent(view.getInfoWindowContent().getValue().replace("\n", "<br/>"));
    }
    
}
