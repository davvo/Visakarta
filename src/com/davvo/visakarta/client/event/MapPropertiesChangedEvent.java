package com.davvo.visakarta.client.event;

import com.davvo.visakarta.shared.MapPosition;
import com.google.gwt.event.shared.GwtEvent;

public class MapPropertiesChangedEvent extends GwtEvent<MapPropertiesEventHandler> {

    public static Type<MapPropertiesEventHandler> TYPE = new Type<MapPropertiesEventHandler>();
    
    private MapPosition mapPosition;
    
    public MapPropertiesChangedEvent(MapPosition mapPosition) {
        this.mapPosition = mapPosition;
    }

    public MapPosition getMapPosition() {
        return mapPosition;
    }

    @Override
    public Type<MapPropertiesEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MapPropertiesEventHandler handler) {
        handler.onMapPropertiesChanged(this);
    }

}
