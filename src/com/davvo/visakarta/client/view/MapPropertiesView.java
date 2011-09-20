package com.davvo.visakarta.client.view;

import java.util.Collection;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.davvo.visakarta.client.presenter.MapPropertiesPresenter;
import com.davvo.visakarta.client.ui.IntegerListBox;
import com.davvo.visakarta.client.ui.LatLonBox;
import com.davvo.visakarta.client.ui.MapControlSelect;
import com.davvo.visakarta.client.ui.MapTypeSelect;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.MapControl;
import com.davvo.visakarta.shared.MapType;

public class MapPropertiesView extends DialogBox implements MapPropertiesPresenter.Display {

    private IntegerListBox zoomBox;
    private LatLonBox centerBox;
    
    private MapTypeSelect mapTypeSelect;
    private MapControlSelect mapControlSelect;
    
    public MapPropertiesView() {
        super(false, false);
        setHTML("Map Properties");
        
        Panel contents = new VerticalPanel();
        
        Panel latLonPanel = new HorizontalPanel();
        centerBox = new LatLonBox();
        latLonPanel.add(centerBox);

        contents.add(new Label("Center (latitude, longitude)"));
        contents.add(latLonPanel);

        zoomBox = new IntegerListBox(1, 21);        
        contents.add(new Label("Zoom level"));
        contents.add(zoomBox);
        
        mapTypeSelect = new MapTypeSelect();
        contents.add(new Label("Map type"));
        contents.add(mapTypeSelect);
        
        mapControlSelect = new MapControlSelect();
        contents.add(new Label("Controls"));
        contents.add(mapControlSelect);
                
        setWidget(contents);        
    }
        
    @Override
    public HasValue<LatLon> getCenter() {
        return centerBox;
    }
    
    @Override
    public HasValue<Integer> getZoom() {
        return zoomBox;
    }
    
    @Override
    public HasValue<MapType> getMapType() {
        return mapTypeSelect;
    }
    
    @Override
    public HasValue<Collection<MapControl>> getControls() {
        return mapControlSelect;
    }
    
    @Override
    public void showMe() {

        setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            
            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                MapPropertiesView.this.setPopupPosition(RootPanel.get().getOffsetWidth() - offsetWidth - 180, 50);
            }
            
        });
    }

}
