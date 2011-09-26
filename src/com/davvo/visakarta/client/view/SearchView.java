package com.davvo.visakarta.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.davvo.visakarta.client.presenter.SearchPresenter.Display;

public class SearchView extends DialogBox implements Display {
    
    private SuggestBox searchBox;
    private Button searchButton;
    
    public SearchView(SuggestOracle oracle) {        
        super(false, false);
        setHTML("Search");
        
        FlowPanel flowPanel = new FlowPanel();
        setWidget(flowPanel);        
        
        searchBox = new SuggestBox(oracle);
        searchBox.setWidth("300px");
        flowPanel.add(searchBox);
        
        searchButton = new Button("Search");
        flowPanel.add(searchButton);
        
        setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            
            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                SearchView.this.setPopupPosition(RootPanel.get().getOffsetWidth() / 2 - offsetWidth / 2, 30);
            }
            
        });
        
    }

    @Override
    public HasClickHandlers getSearchButton() {
        return searchButton;
    }

    @Override
    public HasValue<String> getSearchBox() {
        return searchBox;
    }
    
    
}
