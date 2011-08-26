package com.davvo.visakarta.client.event;

import com.davvo.visakarta.shared.VKMarker;
import com.google.gwt.event.shared.GwtEvent;

public class MarkerAddedEvent extends GwtEvent<MarkerAddedHandler> {

    public static Type<MarkerAddedHandler> TYPE = new Type<MarkerAddedHandler>();
    
    private VKMarker marker;
    
    public MarkerAddedEvent(VKMarker marker) {
        this.marker = marker;
    }
    
    public VKMarker getMarker() {
        return marker;
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
