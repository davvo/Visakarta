package com.davvo.visakarta.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteMarkerEvent extends GwtEvent<DeleteMarkerHandler> {
    
    public static Type<DeleteMarkerHandler> TYPE = new Type<DeleteMarkerHandler>();

    private List<Integer> index;
    
    public DeleteMarkerEvent(List<Integer> index) {
        this.index = index;
    }
    
    public List<Integer> getIndex() {
        return index;
    }
    
    @Override
    public Type<DeleteMarkerHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteMarkerHandler handler) {
        handler.onDeleteMarker(this);
    }

}
