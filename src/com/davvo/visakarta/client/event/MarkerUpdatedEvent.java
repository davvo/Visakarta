package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MarkerUpdatedEvent extends GwtEvent<MarkerUpdatedHandler> {

    public static Type<MarkerUpdatedHandler> TYPE = new Type<MarkerUpdatedHandler>();

    private int id;
    
    public MarkerUpdatedEvent(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public Type<MarkerUpdatedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MarkerUpdatedHandler handler) {
        handler.onMarkerUpdated(this);
    }
    
}
