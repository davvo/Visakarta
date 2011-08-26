package com.davvo.visakarta.client.event;

import com.davvo.visakarta.shared.LatLon;
import com.google.gwt.event.shared.GwtEvent;

public class ShowMarkerDetailsEvent extends GwtEvent<ShowMarkerDetailsHandler> {

    public static Type<ShowMarkerDetailsHandler> TYPE = new Type<ShowMarkerDetailsHandler>();
    
    int index;
    LatLon pos;
    
    public ShowMarkerDetailsEvent(int index, LatLon pos) {
        this.index = index;
        this.pos = pos;
    }
    
    public int getIndex() {
        return index;
    }
    
    public LatLon getPos() {
        return pos;
    }
    
    @Override
    public Type<ShowMarkerDetailsHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowMarkerDetailsHandler handler) {
        handler.onShowMarkerDetails(this);
    }

}
