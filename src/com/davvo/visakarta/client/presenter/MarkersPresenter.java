package com.davvo.visakarta.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.davvo.visakarta.client.event.AddMarkerEvent;
import com.davvo.visakarta.client.event.DeleteMarkerEvent;
import com.davvo.visakarta.client.event.MarkerAddedEvent;
import com.davvo.visakarta.client.event.MarkerAddedHandler;
import com.davvo.visakarta.client.event.MarkerDeletedEvent;
import com.davvo.visakarta.client.event.MarkerDeletedHandler;
import com.davvo.visakarta.client.event.MarkerMovedEvent;
import com.davvo.visakarta.client.event.MarkerMovedHandler;
import com.davvo.visakarta.client.event.ShowMarkerDetailsEvent;
import com.davvo.visakarta.client.event.ShowMarkersEvent;
import com.davvo.visakarta.client.event.ShowMarkersHandler;
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
        eventBus.addHandler(ShowMarkersEvent.TYPE, new ShowMarkersHandler() {
                        
            @Override
            public void onShowMarkers(ShowMarkersEvent event) {
                view.show();                
            }
        });
        
        eventBus.addHandler(MarkerAddedEvent.TYPE, new MarkerAddedHandler() {
            
            @Override
            public void onMarkerAdded(MarkerAddedEvent event) {
                markers.add(event.getMarker());
                view.setData(markers);                
            }
        });
        
        eventBus.addHandler(MarkerMovedEvent.TYPE, new MarkerMovedHandler() {
            
            @Override
            public void onMarkerMoved(MarkerMovedEvent event) {
                markers.get(event.getIndex()).setPos(event.getPos());                
            }
        });
        
        eventBus.addHandler(MarkerDeletedEvent.TYPE, new MarkerDeletedHandler() {
            
            @Override
            public void onMarkerDeleted(MarkerDeletedEvent event) {
                markers.removeAll(getMarkers(event.getIndex()));
                view.setData(markers);
                
            }
        });
        
        view.getAddButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new AddMarkerEvent());
            }
        });
        
        view.getDeleteButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                List<Integer> index = view.getSelectedRows();
                
                if (!index.isEmpty()) {
                    eventBus.fireEvent(new DeleteMarkerEvent(index));
                }
            }
        });
        
        view.getList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
              int index = view.getClickedRow(event);
              
              if (index >= 0) {
                eventBus.fireEvent(new ShowMarkerDetailsEvent(index, markers.get(index).getPos()));
              }
            }
          });

    }
    
    private List<VKMarker> getMarkers(List<Integer> index) {
        List<VKMarker> list = new ArrayList<VKMarker>(index.size());
        for (int i: index) {
            list.add(markers.get(i));
        }
        return list;
    }

}
