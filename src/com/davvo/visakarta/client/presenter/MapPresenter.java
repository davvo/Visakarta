package com.davvo.visakarta.client.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.davvo.visakarta.client.event.AddMarkerEvent;
import com.davvo.visakarta.client.event.DeleteMarkerEvent;
import com.davvo.visakarta.client.event.HasMapHandlers;
import com.davvo.visakarta.client.event.MarkerMovedEvent;
import com.davvo.visakarta.client.event.MarkerMovedHandler;
import com.davvo.visakarta.client.event.MarkersEventHandler;
import com.davvo.visakarta.client.event.ShowMapPropertiesEvent;
import com.davvo.visakarta.client.event.ShowMarkerDetailsEvent;
import com.davvo.visakarta.client.event.ShowMarkersEvent;
import com.davvo.visakarta.client.event.MapMovedEvent;
import com.davvo.visakarta.client.event.MapMovedHandler;
import com.davvo.visakarta.client.event.MapPropertiesChangedEvent;
import com.davvo.visakarta.client.event.MapPropertiesEventHandler;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.MapPosition;
import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class MapPresenter implements Presenter, MarkersEventHandler {

    private EventBus eventBus;
    private Display view;
    
    List<VKMarker> markers = new ArrayList<VKMarker>();
    
    public interface Display {
        LatLon getCenter();
        void setCenter(LatLon center, int zoom);
        int getZoom();
        void setZoom(int zoom);
        HasMapHandlers getMapHandler();
        void addMarker(VKMarker marker);
        void deleteMarkers(List<Integer> index); 
        Widget asWidget();   
    }
    
    public MapPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
    }
    
    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(view.asWidget());
    }
    
    private void bind() {
        
        eventBus.addHandler(MapPropertiesChangedEvent.TYPE, new MapPropertiesEventHandler() {
            
            @Override
            public void onMapPropertiesChanged(MapPropertiesChangedEvent event) {
                if (event.getSource() != MapPresenter.this) {
                    view.setCenter(event.getMapPosition().getCenter(), event.getMapPosition().getZoom());
                }
            }

            @Override
            public void onShowMapProperties(ShowMapPropertiesEvent event) {
                // Ignore
            }
        });
        
        eventBus.addHandler(AddMarkerEvent.TYPE, this);
        
        eventBus.addHandler(DeleteMarkerEvent.TYPE, this);
        
        view.getMapHandler().addMapMoveHandler(new MapMovedHandler() {
            
            @Override
            public void onMapMove(MapMovedEvent event) {
                MapPosition newPos = new MapPosition(view.getCenter(), view.getZoom());
                eventBus.fireEventFromSource(new MapPropertiesChangedEvent(newPos), MapPresenter.this);
                System.out.println(newPos);
            }            
        });
        
        view.getMapHandler().addMarkerMovedHandler(new MarkerMovedHandler() {
            
            @Override
            public void onMarkerMoved(MarkerMovedEvent event) {
                eventBus.fireEventFromSource(event, MapPresenter.this);
            }
        });
        
    }

    @Override
    public void onShowMarkers(ShowMarkersEvent event) {
        // Ignore        
    }

    @Override
    public void onShowMarkerDetails(ShowMarkerDetailsEvent event) {
        // ignore
    }

    @Override
    public void onAddMarker(AddMarkerEvent event) {
        VKMarker marker = new VKMarker(view.getCenter());
        markers.add(marker);
        view.addMarker(marker);        
    }

    @Override
    public void onDeleteMarker(DeleteMarkerEvent event) {
        List<Integer> index = event.getIndex();
        view.deleteMarkers(index);        
        Collection<VKMarker> toRemove = new ArrayList<VKMarker>(index.size());
        for (int i: index) {
            toRemove.add(markers.get(i));
        }
        markers.removeAll(toRemove);
    }

}
