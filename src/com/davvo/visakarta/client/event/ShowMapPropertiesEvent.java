package com.davvo.visakarta.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowMapPropertiesEvent extends GwtEvent<ShowMapPropertiesHandler> {

    public static Type<ShowMapPropertiesHandler> TYPE = new Type<ShowMapPropertiesHandler>();

    @Override
    public Type<ShowMapPropertiesHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowMapPropertiesHandler handler) {
        handler.onShowMapProperties(this);
    }

}
