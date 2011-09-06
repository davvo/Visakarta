package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public interface MapTypeChangedHandler extends EventHandler {

    public void onMapTypeChanged(MapTypeChangedEvent event);

    public static class MapTypeChangedEvent extends GwtEvent<MapTypeChangedHandler> {

        public static Type<MapTypeChangedHandler> TYPE = new Type<MapTypeChangedHandler>();
        
        @Override
        public Type<MapTypeChangedHandler> getAssociatedType() {
            return TYPE;
        }
    
        @Override
        protected void dispatch(MapTypeChangedHandler handler) {
            handler.onMapTypeChanged(this);
        }
    }
    
}

