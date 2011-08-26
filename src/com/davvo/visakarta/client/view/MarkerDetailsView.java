package com.davvo.visakarta.client.view;

import com.davvo.visakarta.client.presenter.MarkerDetailsPresenter.Display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MarkerDetailsView implements Display {

    Button okButton;
    Button cancelButton;
    
    DoubleBox latBox;
    DoubleBox lonBox;
    
    DialogBox dialogBox;
    
    public MarkerDetailsView() {
        Panel contents = new VerticalPanel();        
        
        Panel latLonPanel = new HorizontalPanel();
        latBox = new DoubleBox();
        latLonPanel.add(latBox);
        lonBox = new DoubleBox();
        latLonPanel.add(lonBox);

        contents.add(new Label("latitude, longitude"));
        contents.add(latLonPanel);

        Panel buttonPanel = new HorizontalPanel();
        okButton = new Button("Apply");
        buttonPanel.add(okButton);
        cancelButton = new Button("Close");
        buttonPanel.add(cancelButton);
        
        contents.add(buttonPanel);
        
        dialogBox = new DialogBox(false, false);
        dialogBox.setText("Marker properties");
        dialogBox.setWidget(contents);
    }
    
    @Override
    public HasClickHandlers getOkButton() {
        return okButton;
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
    public void setTitle(String title) {
        dialogBox.setText(title);
    }

    @Override
    public void hide() {
        dialogBox.hide();
    }

    @Override
    public void show() {
        dialogBox.show();
    }

    @Override
    public Widget asWidget() {
        return dialogBox;
    }

}
