package com.davvo.visakarta.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class MarkerDeletedEvent extends GwtEvent<MarkerDeletedHandler> {

    public static Type<MarkerDeletedHandler> TYPE = new Type<MarkerDeletedHandler>();
    
    private List<Integer> index;
    
    public MarkerDeletedEvent(List<Integer> index) {
        this.index = index;
    }
    
    public List<Integer> getIndex() {
        return index;
    }
    
    @Override
    public Type<MarkerDeletedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MarkerDeletedHandler handler) {
        handler.onMarkerDeleted(this);
    }

}
