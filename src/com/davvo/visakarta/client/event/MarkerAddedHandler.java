package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MarkerAddedHandler extends EventHandler {

    public void onMarkerAdded(MarkerAddedEvent event);
    
}
