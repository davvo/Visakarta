package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MarkerClickedHandler extends EventHandler {

    public void onMarkerClicked(MarkerClickedEvent event);
    
}
