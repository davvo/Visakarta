package com.davvo.visakarta.client;

import com.davvo.visakarta.shared.Map;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MapServiceAsync {

    void save(Map map, AsyncCallback<String> callback);

    void load(String id, AsyncCallback<Map> callback);
    
    void exists(String id, AsyncCallback<Boolean> callback);
    
}
