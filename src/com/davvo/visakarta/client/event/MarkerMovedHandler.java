package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MarkerMovedHandler extends EventHandler {

    public void onMarkerMoved(MarkerMovedEvent event);
    
}
