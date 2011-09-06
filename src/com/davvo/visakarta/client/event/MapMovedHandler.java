package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public interface MapMovedHandler extends EventHandler {

    public void onMapMoved(MapMovedEvent event);
    
    public class MapMovedEvent extends GwtEvent<MapMovedHandler> {

        public static Type<MapMovedHandler> TYPE = new Type<MapMovedHandler>();
        
        @Override
        public Type<MapMovedHandler> getAssociatedType() {
            return TYPE;
        }

        @Override
        protected void dispatch(MapMovedHandler handler) {
            handler.onMapMoved(this);
        }

    }    
}
