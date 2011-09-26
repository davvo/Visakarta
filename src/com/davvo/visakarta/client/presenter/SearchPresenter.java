package com.davvo.visakarta.client.presenter;

import com.davvo.visakarta.client.SearchServiceAsync;
import com.davvo.visakarta.client.event.MapPropertiesChangedEvent;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.Place;
import com.davvo.visakarta.shared.SearchResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class SearchPresenter implements Presenter {

    private EventBus eventBus;
    private SearchServiceAsync searchService;
    private Display view;
    
    public interface Display {
        HasClickHandlers getSearchButton();
        HasValue<String> getSearchBox();
        
        void show();
        void hide();
        Widget asWidget();
    }
    
    public SearchPresenter(EventBus eventBus, SearchServiceAsync searchService, Display view) {
        this.eventBus = eventBus;
        this.searchService = searchService;
        this.view = view;
    }
    
    @Override
    public void go() {
        bind();
        view.show();
    }
    
    private void bind() {
        view.getSearchButton().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                String value = view.getSearchBox().getValue();
                
                searchService.search(value, 0, 1, new AsyncCallback<SearchResult>() {
                    
                    @Override
                    public void onSuccess(SearchResult result) {
                        if (!result.getPlaces().isEmpty()) {
                            Place place = result.getPlaces().get(0);
                            Map.getInstance().setCenter(place.getPos());
                            Map.getInstance().setZoom(12);
                            eventBus.fireEventFromSource(new MapPropertiesChangedEvent(), this);
                        }
                    }
                    
                    @Override
                    public void onFailure(Throwable caught) {
                        System.err.println(caught.toString());
                    }
                });
                
            }
        });
    }

}
