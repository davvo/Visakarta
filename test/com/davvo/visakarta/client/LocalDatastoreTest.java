package com.davvo.visakarta.client;
import junit.framework.TestCase;

import com.davvo.visakarta.server.JDOMapServiceImpl;
import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;
import com.davvo.visakarta.shared.VKMarker;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class LocalDatastoreTest extends TestCase {

    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    public void setUp() {
        helper.setUp();
    }

    public void tearDown() {
        helper.tearDown();
    }

    public void testSaveMap() {
        MapService mapserv = new JDOMapServiceImpl();

        Map map = Map.getInstance();
        
        map.setId("123");
        map.setCenter(new LatLon(0, 0));
        map.getMarkers().add(new VKMarker(new LatLon(100, 100)));

        mapserv.save(map);
        
        Map map2 = mapserv.load("123");
        
        assertEquals(map2.getMarkers().size(), 1);
        assertEquals(map.getCenter(), map2.getCenter());
        
    }

}