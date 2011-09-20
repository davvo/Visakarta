package com.davvo.visakarta.client.view;

import com.davvo.visakarta.client.presenter.ToolBarPresenter.Display;


import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class ToolBarView extends DialogBox implements Display {

    Panel widget;
    ToggleButton mapPropertiesButton;
    ToggleButton markersButton;
    ToggleButton saveMapButton;
        
    public ToolBarView() {
        super(false, false);
        
        widget = new FlowPanel();
        widget.addStyleName("toolBar");

        mapPropertiesButton = new ToggleButton("Preferences");
        widget.add(mapPropertiesButton);
        
        markersButton = new ToggleButton("Markers");
        widget.add(markersButton);
        
        saveMapButton = new ToggleButton("Save map");
        widget.add(saveMapButton);
        
        setHTML("Main menu");
        setWidget(widget);
        
        setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            
            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                ToolBarView.this.setPopupPosition(RootPanel.get().getOffsetWidth() - offsetWidth - 10, 50);
            }
            
        });
    }
        
    @Override
    public HasValue<Boolean> getMapPropertiesButton() {
        return mapPropertiesButton;
    }

    @Override
    public HasValue<Boolean> getMarkersButton() {
        return markersButton;
    }

    @Override
    public HasValue<Boolean> getSaveMapButton() {
        return saveMapButton;
    }
    
    @Override
    public Widget asWidget() {
        return widget;
    }

}
