package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MarkerClickedEvent extends GwtEvent<MarkerClickedHandler> {

    public static Type<MarkerClickedHandler> TYPE = new Type<MarkerClickedHandler>();
    
    private int id;
    
    public MarkerClickedEvent(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public Type<MarkerClickedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MarkerClickedHandler handler) {
        handler.onMarkerClicked(this);
    }

    
    
}
