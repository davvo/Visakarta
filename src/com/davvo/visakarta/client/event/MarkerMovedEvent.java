package com.davvo.visakarta.client.event;

import com.davvo.visakarta.shared.LatLon;
import com.google.gwt.event.shared.GwtEvent;

public class MarkerMovedEvent extends GwtEvent<MarkerMovedHandler> {

    public static Type<MarkerMovedHandler> TYPE = new Type<MarkerMovedHandler>();

    private int index;
    private LatLon pos;
    
    public MarkerMovedEvent(int index, LatLon pos) {
        this.index = index;
        this.pos = pos;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public LatLon getPos() {
        return this.pos;
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
