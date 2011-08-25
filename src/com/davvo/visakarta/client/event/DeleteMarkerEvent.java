package com.davvo.visakarta.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteMarkerEvent extends GwtEvent<MarkersEventHandler> {
    
    public static Type<MarkersEventHandler> TYPE = new Type<MarkersEventHandler>();

    private List<Integer> index;
    
    public DeleteMarkerEvent(List<Integer> index) {
        this.index = index;
    }
    
    public List<Integer> getIndex() {
        return index;
    }
    
    @Override
    public Type<MarkersEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MarkersEventHandler handler) {
        handler.onDeleteMarker(this);
    }

}
