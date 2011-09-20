package com.davvo.visakarta.client;

import java.text.ParseException;

import com.davvo.visakarta.client.ui.LatLonParser;
import com.davvo.visakarta.shared.LatLon;

import junit.framework.TestCase;

public class LatLonBoxTest extends TestCase {
    
    private LatLonParser parser;
    
    
    public void setUp() {
        parser = LatLonParser.instance();
    }
    
    public void testLatLonParser() throws ParseException {        
        String[] valid = {"0,0", "0, 0", "0 ,0", "0 0", "0  ,  0"};

        for (String s: valid) {
            LatLon pos = parser.parse(s);
            assertEquals(0d, pos.getLat());
            assertEquals(0d, pos.getLon());
        }
        
        LatLon pos = parser.parse("-0, -0");
        assertEquals(-0d, pos.getLat());
        assertEquals(-0d, pos.getLon());
    }
    
    public void testLatLonParserError() throws ParseException {
        String[] invalid = {"", " ", "0", "0,", "0,0,0", "0,,0"};
        
        for (String s: invalid) {
            try {
                parser.parse(s);
                fail("Should blow up: " + s);
            } catch (Exception x) {
                assertTrue("Expected ParseException", x instanceof ParseException);
            }
        }
    }
    
    
}
