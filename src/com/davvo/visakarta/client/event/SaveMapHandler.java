package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SaveMapHandler extends EventHandler {

    public void onSaveMap(SaveMapEvent event);
    
}
