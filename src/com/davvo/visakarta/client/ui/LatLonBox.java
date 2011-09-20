package com.davvo.visakarta.client.ui;

import com.davvo.visakarta.shared.LatLon;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ValueBox;

public class LatLonBox extends ValueBox<LatLon> {
    
    public LatLonBox() {
        super(Document.get().createTextInputElement(), LatLonRenderer.instance(), LatLonParser.instance());
    }
    
}
