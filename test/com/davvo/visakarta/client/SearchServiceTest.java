package com.davvo.visakarta.client;

import com.davvo.visakarta.client.presenter.SearchPresenter;
import com.davvo.visakarta.client.view.SearchView;
import com.davvo.visakarta.shared.SearchResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public class SearchServiceTest extends GWTTestCase {

    EventBus eventBus;
    
    SearchServiceAsync searchService;
    SearchPresenter searchPresenter;
    SearchPresenter.Display display;
    
    @Override
    public String getModuleName() {
        return "com.davvo.visakarta.Visakarta";
    }
    
        
    public void gwtSetUp() {
        eventBus = new SimpleEventBus();
        searchService = GWT.create(SearchService.class);
        
        SuggestOracle oracle = new SearchSuggestOracle(searchService, 100);        
        display = new SearchView(oracle);
        searchPresenter = new SearchPresenter(eventBus, searchService, display);
    }
        
    public void testSearch() {
        searchService.search("stor", 0, 100, new AsyncCallback<SearchResult>() {
            
            @Override
            public void onSuccess(SearchResult result) {
                finishTest();
            }
            
            @Override
            public void onFailure(Throwable caught) {
                System.out.println("Failed: " + caught.getMessage());
            }
        });
        
        delayTestFinish(2000);            
    }    

}
