package com.davvo.visakarta.client;

import com.davvo.visakarta.shared.SearchResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService {

    public SearchResult search(String value, int offset, int limit);
    
}
