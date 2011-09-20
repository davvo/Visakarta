package com.davvo.visakarta.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SerializeMapTest extends TestCase {

    public void testSerializeMap() throws ClassNotFoundException, IOException {
        Map map = Map.getInstance();
        map.setCenter(new LatLon(0, 0));
        
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        
        out.writeObject(map);
        out.close();
        
        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bin);
        
        Map map2 = (Map) in.readObject();
        
        Assert.assertEquals(map.getId(), map2.getId());
        Assert.assertEquals(map.getCenter(), map2.getCenter());
    }
    
}
