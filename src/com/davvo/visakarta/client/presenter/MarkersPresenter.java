package com.davvo.visakarta.client.presenter;

import java.util.List;

import com.davvo.visakarta.client.event.MarkerAddedEvent;
import com.davvo.visakarta.client.event.MarkerDeletedEvent;
import com.davvo.visakarta.client.event.ShowMarkerDetailsEvent;
import com.davvo.visakarta.client.event.ShowMarkersEvent;
import com.davvo.visakarta.client.event.ShowMarkersHandler;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;

public class MarkersPresenter {

    private final EventBus eventBus;
    private final Display view;

    public interface Display {
        HasClickHandlers getAddButton();
        HasClickHandlers getDeleteButton();
        HasClickHandlers getCloseButton();
        List<Integer> getSelectedRows();
        HasClickHandlers getList();
        int getClickedRow(ClickEvent event);      
        void setData(List<VKMarker> data);
        void show();
        void hide();
        Widget asWidget();
    }
    
    public MarkersPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
        bind();
    }
    
    private void bind() {
        
        /**
         * Show yourself
         */
        eventBus.addHandler(ShowMarkersEvent.TYPE, new ShowMarkersHandler() {
                        
            @Override
            public void onShowMarkers(ShowMarkersEvent event) {
                view.show();                
            }
        });
        
        /**
         * Add marker and update view. Fire MarkerAddedEvent to notify other presenters.
         */
        view.getAddButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                VKMarker marker = new VKMarker(Map.getInstance().getCenter());
                Map.getInstance().getMarkers().add(marker);
                view.setData(Map.getInstance().getMarkers());
                eventBus.fireEvent(new MarkerAddedEvent(marker.getId()));
            }
        });
        
        /**
         * Remove markers and update view. Fire MarkerDeletedEvent to notify other presenters.
         */
        view.getDeleteButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                List<Integer> index = view.getSelectedRows();
                
                if (!index.isEmpty()) {
                    eventBus.fireEvent(new MarkerDeletedEvent(Map.getInstance().getMarkerIdsAtIndex(index)));
                    Map.getInstance().getMarkers().removeAll(Map.getInstance().getMarkersAtIndex(index));
                    view.setData(Map.getInstance().getMarkers());
                }
            }
        });
        
        view.getCloseButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                view.hide();
            }
        });
        
        view.getList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
              int index = view.getClickedRow(event);
              if (index >= 0) {
                  VKMarker marker = Map.getInstance().getMarkers().get(index);
                  eventBus.fireEvent(new ShowMarkerDetailsEvent(marker.getId()));
              }
            }
          });

    }
    
}
