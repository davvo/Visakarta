package com.davvo.visakarta.client.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.davvo.visakarta.client.event.AddMarkerEvent;
import com.davvo.visakarta.client.event.DeleteMarkerEvent;
import com.davvo.visakarta.client.event.MarkersEventHandler;
import com.davvo.visakarta.client.event.ShowMarkerDetailsEvent;
import com.davvo.visakarta.client.event.ShowMarkersEvent;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;

public class MarkersPresenter {

    private final EventBus eventBus;
    private final Display view;

    List<VKMarker> markers = new ArrayList<VKMarker>();
    
    public interface Display {
        HasClickHandlers getAddButton();
        HasClickHandlers getDeleteButton();
        List<Integer> getSelectedRows();
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
        eventBus.addHandler(ShowMarkersEvent.TYPE, new MarkersEventHandler() {
            
            @Override
            public void onShowMarkers(ShowMarkersEvent event) {
                view.show();                
            }
            
            @Override
            public void onShowMarkerDetails(ShowMarkerDetailsEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onDeleteMarker(DeleteMarkerEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onAddMarker(AddMarkerEvent event) {
            }
        });
        
        view.getAddButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                markers.add(new VKMarker(null));
                view.setData(markers);
                eventBus.fireEventFromSource(new AddMarkerEvent(), MarkersPresenter.this);
            }
        });
        
        view.getDeleteButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                List<Integer> index = view.getSelectedRows();
                Collection<VKMarker> toRemove = new ArrayList<VKMarker>();
                for (int i: index) {
                    toRemove.add(markers.get(i));
                }
                markers.removeAll(toRemove);
                view.setData(markers);
                eventBus.fireEventFromSource(new DeleteMarkerEvent(index), MarkersPresenter.this);
            }
        });
    }

}
