package com.davvo.visakarta.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class MarkerDeletedEvent extends GwtEvent<MarkerDeletedHandler> {

    public static Type<MarkerDeletedHandler> TYPE = new Type<MarkerDeletedHandler>();
    
    private List<Integer> ids;
    
    public MarkerDeletedEvent(List<Integer> ids) {
        this.ids = ids;
    }
    
    public List<Integer> getIds() {
        return ids;
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
