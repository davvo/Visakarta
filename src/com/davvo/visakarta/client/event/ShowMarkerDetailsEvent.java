package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowMarkerDetailsEvent extends GwtEvent<MarkersEventHandler> {

    public Type<MarkersEventHandler> TYPE = new Type<MarkersEventHandler>();
    
    @Override
    public Type<MarkersEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MarkersEventHandler handler) {
        handler.onShowMarkerDetails(this);
    }

}
