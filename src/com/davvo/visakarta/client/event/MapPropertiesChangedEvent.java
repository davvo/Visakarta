package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MapPropertiesChangedEvent extends GwtEvent<MapPropertiesChangedHandler> {

    public static Type<MapPropertiesChangedHandler> TYPE = new Type<MapPropertiesChangedHandler>();
    
    @Override
    public Type<MapPropertiesChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MapPropertiesChangedHandler handler) {
        handler.onMapPropertiesChanged(this);
    }

}
