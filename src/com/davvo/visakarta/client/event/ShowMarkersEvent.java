package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowMarkersEvent extends GwtEvent<ShowMarkersHandler> {

    public static Type<ShowMarkersHandler> TYPE = new Type<ShowMarkersHandler>();
    
    private boolean visible = false;
    
    public ShowMarkersEvent(boolean visible) {
        this.visible = visible;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    @Override
    public Type<ShowMarkersHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowMarkersHandler handler) {
        handler.onShowMarkers(this);
    }

}
