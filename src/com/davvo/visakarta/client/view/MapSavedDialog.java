package com.davvo.visakarta.client.view;

import com.davvo.visakarta.shared.Map;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MapSavedDialog extends DialogBox {

    Button closeButton;
    
    public MapSavedDialog() {
        super(false, true);
        setGlassEnabled(true);
        setHTML("Map Saved!");
        
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setSpacing(10);
        setWidget(vPanel);
        
        StringBuilder sb = new StringBuilder()
        .append("<h1>Congratulations!</h1>")
        .append("<span>")
        .append("<a href=\"http://www.visakarta.se/").append(Map.getInstance().getId()).append("\">")
        .append("http://www.visakarta.se/").append(Map.getInstance().getId()).append("</a>")
        .append("</span>");
        
        vPanel.add(new HTMLPanel(sb.toString()));
        
        closeButton = new Button("Close");
        vPanel.add(closeButton);
        
        closeButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        
    }
    
}
