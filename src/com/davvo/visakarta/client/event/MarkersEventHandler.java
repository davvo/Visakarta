package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MarkersEventHandler extends EventHandler {

    public void onShowMarkers(ShowMarkersEvent event);
    
    public void onShowMarkerDetails(ShowMarkerDetailsEvent event);
    
    public void onAddMarker(AddMarkerEvent event);
    
    public void onDeleteMarker(DeleteMarkerEvent event);
    
}
