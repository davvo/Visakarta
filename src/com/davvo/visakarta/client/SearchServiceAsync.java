package com.davvo.visakarta.client;

import com.davvo.visakarta.shared.SearchResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {

    public void search(String value, int offset, int limit, AsyncCallback<SearchResult> callback);
    
}
