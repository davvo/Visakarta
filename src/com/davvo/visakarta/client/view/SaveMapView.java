package com.davvo.visakarta.client.view;

import com.davvo.visakarta.client.presenter.SaveMapPresenter.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;

public class SaveMapView extends DialogBox implements Display {
    
    private TextBox titleTextBox;
    private TextBox urlTextBox;
    private TextBox emailTextBox;
    private Button saveButton;
    private Button cancelButton;
    private Label urlDisplayLabel;

    public SaveMapView() {
        setGlassEnabled(true);
        setHTML("Save Map");
        
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setSpacing(10);
        setWidget(verticalPanel);
        verticalPanel.setSize("199px", "250px");
        
        Label lblTitle = new Label("Title");
        verticalPanel.add(lblTitle);
        
        titleTextBox = new TextBox();
        titleTextBox.setText("My Map");
        verticalPanel.add(titleTextBox);
        titleTextBox.setWidth("200px");
        
        Label lblEmailoptional = new Label("Email (optional)");
        verticalPanel.add(lblEmailoptional);
        
        emailTextBox = new VolatileTextBox();
        verticalPanel.add(emailTextBox);
        emailTextBox.setWidth("200px");
        
        Label lblUrl = new Label("Map URL");
        verticalPanel.add(lblUrl);
        
        urlTextBox = new VolatileTextBox();
        urlTextBox.setText("asdf");
        verticalPanel.add(urlTextBox);
        urlTextBox.setWidth("200px");
        
        urlDisplayLabel = new Label("http://www.visakarta.se/" + urlTextBox.getValue());
        verticalPanel.add(urlDisplayLabel);
        
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSpacing(5);
        verticalPanel.add(horizontalPanel);
        
        saveButton = new Button("Save");
        horizontalPanel.add(saveButton);
        
        cancelButton = new Button("Cancel");
        horizontalPanel.add(cancelButton);
    }

    @Override
    public HasValue<String> getEmail() {
        return emailTextBox;
    }

    @Override
    public HasValue<String> getMapURL() {
        return urlTextBox;
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
    public HasValue<String> getMapTitle() {
        return titleTextBox;
    }
    
    @Override
    public void setEmailOK(boolean ok) {
        saveButton.setEnabled(ok);
    }

    @Override
    public void setMapURLOK(boolean ok) {
        saveButton.setEnabled(ok);
    }
    
    @Override
    public void showMe() {
        this.center();
    }

    @Override
    public void hideMe() {
        this.hide();
    }
    
    private static class VolatileTextBox extends TextBox {

        private String oldValue;
        
        public VolatileTextBox() {
            this.addKeyUpHandler(new KeyUpHandler() {
                
                @Override
                public void onKeyUp(KeyUpEvent event) {
                    String newValue = getValue();
                    ValueChangeEvent.fireIfNotEqual(VolatileTextBox.this, oldValue, newValue);
                    oldValue = newValue;
                }
            });            
        }
        
    }

}
