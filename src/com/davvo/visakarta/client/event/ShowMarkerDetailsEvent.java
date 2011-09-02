package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowMarkerDetailsEvent extends GwtEvent<ShowMarkerDetailsHandler> {

    public static Type<ShowMarkerDetailsHandler> TYPE = new Type<ShowMarkerDetailsHandler>();
    
    int id;
    
    public ShowMarkerDetailsEvent(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
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
