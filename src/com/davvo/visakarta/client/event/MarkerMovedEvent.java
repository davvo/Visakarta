package com.davvo.visakarta.client.event;

import com.davvo.visakarta.shared.LatLon;
import com.google.gwt.event.shared.GwtEvent;

public class MarkerMovedEvent extends GwtEvent<MarkerMovedHandler> {

    public static Type<MarkerMovedHandler> TYPE = new Type<MarkerMovedHandler>();
    
    private int id;
    private LatLon pos;
    
    public MarkerMovedEvent(int id, LatLon pos) {
        this.id = id;
        this.pos = pos;
    }
    
    public int getId() {
        return id;
    }

    public LatLon getPos() {
        return pos;
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
