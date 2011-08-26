package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddMarkerEvent extends GwtEvent<AddMarkerHandler> {

    public static Type<AddMarkerHandler> TYPE = new Type<AddMarkerHandler>();
    
    @Override
    public Type<AddMarkerHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddMarkerHandler handler) {
        handler.onAddMarker(this);
    }

}
