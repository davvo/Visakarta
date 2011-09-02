package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MapMovedHandler extends EventHandler {

    public void onMapMoved(MapMovedEvent event);
    
}
