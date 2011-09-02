package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MarkerUpdatedHandler extends EventHandler {

    public void onMarkerUpdated(MarkerUpdatedEvent event);
    
}
