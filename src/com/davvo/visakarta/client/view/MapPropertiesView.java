package com.davvo.visakarta.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.davvo.visakarta.client.presenter.MapPropertiesPresenter;
import com.davvo.visakarta.shared.MapControl;
import com.davvo.visakarta.shared.NavControl;
import com.davvo.visakarta.shared.MapType;

public class MapPropertiesView implements MapPropertiesPresenter.Display {

    private DialogBox dialogBox;
    
    private Button saveButton;
    private Button cancelButton;

    private TextBox titleBox;
    private DoubleBox latBox;
    private DoubleBox lonBox;    
    private ListBox zoomBox;
    
    private Map<MapType, RadioButton> mapTypeButtons = new HashMap<MapType, RadioButton>();
    private Map<NavControl, RadioButton> navControlButtons = new HashMap<NavControl, RadioButton>();
    private Map<MapControl, CheckBox> mapControlButtons = new HashMap<MapControl, CheckBox>();
    
    public MapPropertiesView() {
        Panel contents = new VerticalPanel();
        
        titleBox = new TextBox();
        
        contents.add(new Label("Title"));
        contents.add(titleBox);
        
        Panel latLonPanel = new HorizontalPanel();
        latBox = new DoubleBox();
        latLonPanel.add(latBox);
        lonBox = new DoubleBox();
        latLonPanel.add(lonBox);

        contents.add(new Label("Center (latitude, longitude)"));
        contents.add(latLonPanel);

        zoomBox = new ListBox();
        for (int i = 0; i <= 21; ++i) {
            zoomBox.addItem(String.valueOf(i));
        }
        
        contents.add(new Label("Zoom level"));
        contents.add(zoomBox);
        
        Panel mapTypePanel = new FlowPanel();
        for (MapType mapType: MapType.values()) {
            RadioButton rb = new RadioButton("mapType", mapType.toString());
            mapTypeButtons.put(mapType, rb);
            mapTypePanel.add(rb);
        }
        
        contents.add(new Label("Map type"));
        contents.add(mapTypePanel);
        
        Panel navControlPanel = new FlowPanel();
        for (NavControl control: NavControl.values()) {
            RadioButton rb = new RadioButton("navControl", control.toString());
            navControlButtons.put(control, rb);
            navControlPanel.add(rb);
        }
        
        contents.add(new Label("Navigation"));
        contents.add(navControlPanel);
        
        Panel mapControlPanel = new FlowPanel();
        for (MapControl control: MapControl.values()) {
            CheckBox cb = new CheckBox(control.toString());
            mapControlButtons.put(control, cb);
            mapControlPanel.add(cb);
        }
        
        contents.add(new Label("Controls"));
        contents.add(mapControlPanel);
        
        Panel buttonPanel = new HorizontalPanel();
        saveButton = new Button("Apply");
        buttonPanel.add(saveButton);
        cancelButton = new Button("Close");
        buttonPanel.add(cancelButton);
        
        contents.add(buttonPanel);
        
        dialogBox = new DialogBox(false, false);
        dialogBox.setText("Map properties");
        dialogBox.setWidget(contents);
    }
    
    @Override
    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }
    
    @Override
    public HasValue<Double> getLat() {
        return latBox;
    }

    @Override
    public HasValue<Double> getLon() {
        return lonBox;
    }
    
    @Override
    public int getZoom() {
        return zoomBox.getSelectedIndex();
    }

    @Override
    public void setZoom(int zoom) {
        this.zoomBox.setSelectedIndex(zoom);
    }

    @Override
    public HasValue<String> getTitle() {
        return titleBox;
    }

    @Override
    public Widget asWidget() {
        return dialogBox;
    }

    @Override
    public void show() {
        dialogBox.center();
    }

    @Override
    public void hide() {
        dialogBox.hide();        
    }

    @Override
    public MapType getMapType() {
        for (Map.Entry<MapType, RadioButton> e: mapTypeButtons.entrySet()) {
            if (e.getValue().getValue()) {
                return e.getKey();
            }
        }
        return MapType.NORMAL;
    }

    @Override
    public void setMapType(MapType mapType) {
        mapTypeButtons.get(mapType).setValue(true);
    }
    
    @Override
    public NavControl getNavControl() {
        for (Map.Entry<NavControl, RadioButton> e: navControlButtons.entrySet()) {
            if (e.getValue().getValue()) {
                return e.getKey();
            }
        }
        return NavControl.NONE;
    }

    @Override
    public void setNavControl(NavControl navControl) {
        navControlButtons.get(navControl).setValue(true);
    }

    @Override
    public List<MapControl> getMapControls() {
        List<MapControl> mapControls = new ArrayList<MapControl>();
        for (Map.Entry<MapControl, CheckBox> e: mapControlButtons.entrySet()) {
            if (e.getValue().getValue()) {
                mapControls.add(e.getKey());
            }
        }
        return mapControls;
    }

    @Override
    public void setMapControls(List<MapControl> mapControls) {
        for (Map.Entry<MapControl, CheckBox> e: mapControlButtons.entrySet()) {
            e.getValue().setValue(mapControls.contains(e.getKey()));
        }
    }
    
}
