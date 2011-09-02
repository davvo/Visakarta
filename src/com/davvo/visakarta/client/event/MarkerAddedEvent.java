package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MarkerAddedEvent extends GwtEvent<MarkerAddedHandler> {

    public static Type<MarkerAddedHandler> TYPE = new Type<MarkerAddedHandler>();
    
    private int id;
    
    public MarkerAddedEvent(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public Type<MarkerAddedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MarkerAddedHandler handler) {
        handler.onMarkerAdded(this);
    }

}
