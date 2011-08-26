package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MarkerDeletedHandler extends EventHandler {

    public void onMarkerDeleted(MarkerDeletedEvent event);
    
}
