package com.davvo.visakarta.server;

import com.davvo.visakarta.client.MapService;

import com.davvo.visakarta.shared.Map;
import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class JDOMapServiceImpl extends RemoteServiceServlet implements MapService {

    @Override
    public String save(Map map) {

        PersistenceManager pm = PMF.get().getPersistenceManager();
        
        try {
            
            if (exists(map.getId(), pm)) {
                throw new DuplicateMapException(map.getId());
            }
            
            pm.makePersistent(map);
            
        } finally {

            pm.close();            
        }        
        
        return map.getId();
        
    }

    @Override
    public Map load(String id) {
        
        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            Map map = pm.getObjectById(Map.class, id);
            map.getMarkers();
            return pm.detachCopy(map);
        } catch (Exception x) {
            throw new UnknownMapException(id);
        } finally {
            pm.close();
        }        
        
    }

    @Override
    public boolean exists(String id) {
        
        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            return exists(id, pm);
        } finally {
            pm.close();
        }        
        
    }
    
    private boolean exists(String id, PersistenceManager pm) {
        try {
            return pm.getObjectById(Map.class, id) != null;
        } catch (Exception x) {
            return false;
        }        
    }
    
    
}
