package com.davvo.visakarta.client.view;

import com.davvo.visakarta.client.presenter.ToolBarPresenter.Display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ToolBarView implements Display {

    Panel widget;
    Button mapPropertiesButton;
    Button markersButton;
    Button saveMapButton;
    
    public ToolBarView() {
        buildGUI();
    }
    
    private void buildGUI() {        
        widget = new FlowPanel();
        widget.addStyleName("toolBar");
        
        mapPropertiesButton = new Button("Properties");
        widget.add(mapPropertiesButton);

        markersButton = new Button("Markers");
        widget.add(markersButton);
        
        saveMapButton = new Button("Save map!");
        widget.add(saveMapButton);
    }
    
    @Override
    public HasClickHandlers getMapPropertiesButton() {
        return mapPropertiesButton;
    }

    @Override
    public HasClickHandlers getMarkersButton() {
        return markersButton;
    }

    @Override
    public HasClickHandlers getSaveMapButton() {
        return saveMapButton;
    }
    
    @Override
    public Widget asWidget() {
        return widget;
    }

}
