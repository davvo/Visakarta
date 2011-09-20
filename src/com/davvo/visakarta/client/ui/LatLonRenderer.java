package com.davvo.visakarta.client.ui;

import java.io.IOException;

import com.davvo.visakarta.shared.LatLon;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.Renderer;

public class LatLonRenderer implements Renderer<LatLon> {

    private static LatLonRenderer INSTANCE;
    
    private static final NumberFormat numberFormat = NumberFormat.getFormat("##0.0#####");
    
    public static LatLonRenderer instance() {
        if (INSTANCE == null) {
            INSTANCE = new LatLonRenderer();
        }
        return INSTANCE;
    }
    
    private LatLonRenderer() {
    }
    
    @Override
    public String render(LatLon latLon) {
        return numberFormat.format(latLon.getLat()) + ", " +
            numberFormat.format(latLon.getLon());
    }

    @Override
    public void render(LatLon object, Appendable appendable) throws IOException {
        appendable.append(render(object));
        
    }
    
}
