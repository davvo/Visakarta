package com.davvo.visakarta.client.view;

import com.davvo.visakarta.client.presenter.ToolBarPresenter.Display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ToolBarView implements Display {

    Panel widget;
    Button mapPropertiesButton;
    Button markersButton;
    Anchor saveMapButton;
    
    Anchor markerAnchor;
    Anchor prefsAnchor;
    
    public ToolBarView() {
        buildGUI();
    }
    
    private void buildGUI() {        
        widget = new FlowPanel();
        widget.addStyleName("toolBar");

        prefsAnchor = new Anchor("Preferences");
        widget.add(prefsAnchor);
        
        markerAnchor = new Anchor("Add markers");
        widget.add(markerAnchor);
        
        saveMapButton = new Anchor("Save map");
        widget.add(saveMapButton);
    }
    
    @Override
    public HasClickHandlers getMapPropertiesButton() {
        return prefsAnchor;
    }

    @Override
    public HasClickHandlers getMarkersButton() {
        return markerAnchor;
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
