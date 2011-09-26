package com.davvo.visakarta.server;

import java.io.File;

import com.davvo.visakarta.client.SearchService;
import com.davvo.visakarta.server.search.SearchEngine;
import com.davvo.visakarta.shared.SearchResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

    static SearchEngine searchEngine;
    
    @Override
    public void init() {
        if (searchEngine == null) {
            searchEngine = new SearchEngine(new File("/places.txt"), "utf8");
        }
    }
    
    
    @Override
    public SearchResult search(String value, int offset, int limit) {
        return searchEngine.search(value, offset, limit);
    }

}
