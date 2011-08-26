package com.davvo.visakarta.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.davvo.visakarta.client.presenter.MapPropertiesPresenter;

public class MapPropertiesView implements MapPropertiesPresenter.Display {

    private DialogBox dialogBox;
    
    private Button saveButton;
    private Button cancelButton;

    private TextBox titleBox;
    private DoubleBox latBox;
    private DoubleBox lonBox;
    
    private ListBox zoomBox;
    
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
    
}
