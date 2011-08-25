package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowMapPropertiesEvent extends GwtEvent<MapPropertiesEventHandler> {

    public static Type<MapPropertiesEventHandler> TYPE = new Type<MapPropertiesEventHandler>();

    @Override
    public Type<MapPropertiesEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MapPropertiesEventHandler handler) {
        handler.onShowMapProperties(this);
    }

}
