package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MapPropertiesChangedHandler extends EventHandler {

    public void onMapPropertiesChanged(MapPropertiesChangedEvent event);
    
}
