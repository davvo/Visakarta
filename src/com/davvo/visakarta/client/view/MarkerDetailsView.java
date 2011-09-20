package com.davvo.visakarta.client.view;


import com.davvo.visakarta.client.presenter.MarkerDetailsPresenter.Display;
import com.davvo.visakarta.client.ui.LatLonBox;
import com.davvo.visakarta.shared.LatLon;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MarkerDetailsView implements Display {

    Button okButton;
    Button cancelButton;
    
    LatLonBox posBox;

    RadioButton infoWindowYesButton;
    RadioButton infoWindowNoButton;
    TextArea infoWindowContentBox;
    
    DialogBox dialogBox;
    
    public MarkerDetailsView() {
        Panel contents = new VerticalPanel();        
        
        Panel posPanel = new HorizontalPanel();
        posBox = new LatLonBox();
        posBox.setWidth("140px");
        posPanel.add(posBox);

        contents.add(new Label("latitude, longitude"));
        contents.add(posPanel);

        Panel infoWindowChoicePanel = new FlowPanel();
        infoWindowYesButton = new RadioButton("infoWindow", "Yes");
        infoWindowChoicePanel.add(infoWindowYesButton);
        infoWindowNoButton = new RadioButton("infoWindow", "No");
        infoWindowNoButton.setValue(true);
        infoWindowChoicePanel.add(infoWindowNoButton);
        
        contents.add(new Label("Info window"));
        contents.add(infoWindowChoicePanel);
        
        infoWindowContentBox = new TextArea();
        infoWindowContentBox.setCharacterWidth(60);
        infoWindowContentBox.setVisibleLines(20);
        
        contents.add(new Label("Info window contents"));
        contents.add(infoWindowContentBox);
        
        Panel buttonPanel = new HorizontalPanel();
        okButton = new Button("Apply");
        buttonPanel.add(okButton);
        cancelButton = new Button("Close");
        buttonPanel.add(cancelButton);
        
        contents.add(buttonPanel);
        
        dialogBox = new DialogBox(false, false);
        dialogBox.setText("Marker properties");
        dialogBox.setWidget(contents);
        
        dialogBox.setPixelSize(200, 200);
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
    public HasValue<LatLon> getPosition() {
        return posBox;
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
        dialogBox.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            
            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                dialogBox.setPopupPosition(100, 50);
            }
            
        });
    }

    @Override
    public Widget asWidget() {
        return dialogBox;
    }

    @Override
    public HasValue<Boolean> isInfoWindow() {
        return infoWindowYesButton;
    }

    @Override
    public HasValue<String> getInfoWindowContent() {
        return infoWindowContentBox;
    }

}
