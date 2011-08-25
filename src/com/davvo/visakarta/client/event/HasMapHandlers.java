package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasMapHandlers extends HasHandlers {

    public HandlerRegistration addMapMoveHandler(MapMovedHandler handler);
    
    public HandlerRegistration addMarkerMovedHandler(MarkerMovedHandler handler);
    
}
