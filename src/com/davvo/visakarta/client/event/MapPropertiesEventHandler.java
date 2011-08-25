package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MapPropertiesEventHandler extends EventHandler {

    public void onMapPropertiesChanged(MapPropertiesChangedEvent event);
    
    public void onShowMapProperties(ShowMapPropertiesEvent event);
    
}
