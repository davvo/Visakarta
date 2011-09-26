package com.davvo.visakarta.client;

import com.davvo.visakarta.shared.SearchResult;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public class SearchSuggestOracle extends SuggestOracle {

    private SearchServiceAsync searchService;
    private int limit;
    private Timer timer;
    
    public SearchSuggestOracle(SearchServiceAsync searchService, int limit) {
        this.searchService = searchService;
        this.limit = limit;
    }
    
    @Override
    public void requestSuggestions(final Request request, final Callback callback) {
        if (timer != null) {
            timer.cancel();
        }
        
        timer = new Timer() {
            
            @Override
            public void run() {
                queryService(request, callback);
            }
        };
        
        timer.schedule(300);
    }

    private void queryService(final Request request, final Callback callback) {
        searchService.search(request.getQuery(), 0, limit, new AsyncCallback<SearchResult>() {
            
            @Override
            public void onSuccess(SearchResult result) {
                
                Response response = new Response(result.getPlaces());
                if (result.getTotal() > result.getPlaces().size()) {
                    response.setMoreSuggestionsCount(result.getTotal() - result.getPlaces().size());
                }
                
                callback.onSuggestionsReady(request, response);        
            }
            
            @Override
            public void onFailure(Throwable caught) {
                callback.onSuggestionsReady(request, new Response());        
                System.err.println(caught);
            }
            
        }); 
    }
    
    
}
