package com.davvo.visakarta.client.presenter;

import com.davvo.visakarta.client.event.DeleteMarkerEvent;
import com.davvo.visakarta.client.event.DeleteMarkerHandler;
import com.davvo.visakarta.client.event.MarkerMovedEvent;
import com.davvo.visakarta.client.event.MarkerMovedHandler;
import com.davvo.visakarta.client.event.ShowMarkerDetailsEvent;
import com.davvo.visakarta.client.event.ShowMarkerDetailsHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class MarkerDetailsPresenter {

    private final EventBus eventBus;
    private final Display view;
    
    private int markerIndex;
    
    public interface Display {
        HasClickHandlers getOkButton();
        HasClickHandlers getCancelButton();
        HasValue<Double> getLat();
        HasValue<Double> getLon();
        void setTitle(String title);
        void hide();
        void show();
        Widget asWidget();
    }
    
    public MarkerDetailsPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
        bind();
    }
    
    private void bind() {
        eventBus.addHandler(MarkerMovedEvent.TYPE, new MarkerMovedHandler() {
            
            @Override
            public void onMarkerMoved(MarkerMovedEvent event) {
                if (event.getIndex() == markerIndex) {
                    view.getLat().setValue(event.getPos().getLat());
                    view.getLon().setValue(event.getPos().getLon());
                }
                
            }
        });
        
        eventBus.addHandler(ShowMarkerDetailsEvent.TYPE, new ShowMarkerDetailsHandler() {
                        
            @Override
            public void onShowMarkerDetails(ShowMarkerDetailsEvent event) {
                markerIndex = event.getIndex();
                view.getLat().setValue(event.getPos().getLat());
                view.getLon().setValue(event.getPos().getLon());
                view.setTitle("Marker " + event.getIndex());
                view.show();
            }
        });
        
        eventBus.addHandler(DeleteMarkerEvent.TYPE, new DeleteMarkerHandler() {
            
            @Override
            public void onDeleteMarker(DeleteMarkerEvent event) {
                System.out.println(markerIndex + " ?? " + event.getIndex());
                if (event.getIndex().contains(markerIndex)) {
                    view.hide();
                } else {
                    int dec = 0;
                    for (int index: event.getIndex()) {
                        if (index < markerIndex) {
                            ++dec;
                        }
                    }
                    markerIndex -= dec;
                }
            }
        });
            
    }
    
    
    
}
