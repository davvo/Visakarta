package com.davvo.visakarta.server;

import com.davvo.visakarta.client.MapService;

import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.MapType;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DatastoreMapServiceImpl extends RemoteServiceServlet implements MapService {

    @Override
    public String save(Map map) {
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Entity mapEntity = new Entity("Map", map.getId());
        mapEntity.setProperty("title", map.getTitle());
        mapEntity.setProperty("center", new GeoPt((float) map.getCenter().getLat(), (float) map.getCenter().getLon()));
        mapEntity.setProperty("mapType", map.getMapType());
        
        datastore.put(mapEntity);
        
        return map.getId();
    
    }

    @Override
    public Map load(String id) {
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey("Map", id);
        
        try {
            Entity mapEntity = datastore.get(key);
            
            Map map = new Map();
            map.setId(id);
            
            map.setTitle((String) mapEntity.getProperty("title")); 
            GeoPt geoPt = (GeoPt) mapEntity.getProperty("center");            
            map.setCenter(new LatLon(geoPt.getLatitude(), geoPt.getLongitude()));
            map.setMapType((MapType) mapEntity.getProperty("mapType"));
            
            return map;
            
        } catch (EntityNotFoundException x) {
            throw new UnknownMapException(id);
        }
        
    }

    @Override
    public boolean exists(String id) {
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey("Map", id);
        
        try {
            return datastore.get(key) != null;
        } catch (EntityNotFoundException x) {
            return false;
        }
        
    }

}
