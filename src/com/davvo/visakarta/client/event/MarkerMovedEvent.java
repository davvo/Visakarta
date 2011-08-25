package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MarkerMovedEvent extends GwtEvent<MarkerMovedHandler> {

    public static Type<MarkerMovedHandler> TYPE = new Type<MarkerMovedHandler>();

    private int index;
    
    public MarkerMovedEvent(int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    @Override
    public Type<MarkerMovedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MarkerMovedHandler handler) {
        handler.onMarkerMoved(this);
    }
    
}
