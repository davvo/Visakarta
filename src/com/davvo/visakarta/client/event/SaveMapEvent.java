package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SaveMapEvent extends GwtEvent<SaveMapHandler> {

    public static Type<SaveMapHandler> TYPE = new Type<SaveMapHandler>();
    
    private boolean visible = false;
    
    public SaveMapEvent(boolean visible) {
        this.visible = visible;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    @Override
    public Type<SaveMapHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SaveMapHandler handler) {
        handler.onSaveMap(this);        
    }

    
}
