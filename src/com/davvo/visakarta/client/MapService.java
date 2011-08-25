package com.davvo.visakarta.client;

import com.davvo.visakarta.shared.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("mapserv")
public interface MapService extends RemoteService {

    String save(Map map);
    
    Map load(String id);
    
    boolean exists(String id);
    
}
