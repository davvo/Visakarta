package com.davvo.visakarta.server;

import java.io.File;

import com.davvo.visakarta.server.search.SearchEngine;
import com.davvo.visakarta.shared.SearchResult;

import junit.framework.TestCase;

public class SearchEngineTest extends TestCase {

    SearchEngine se;
    
    @Override
    public void setUp() {
        se = new SearchEngine(new File("/places.txt"), "utf8");
    }
    
    public void testSearch() {
        SearchResult result = se.search("Malm");
        assertEquals(9, result.getPlaces().size());
        result = se.search("a");
        assertEquals(109, result.getPlaces().size());
        result = se.search("šja ");
        assertEquals(1, result.getPlaces().size());
    }
    
    public void testSearchOffset() {
        SearchResult result = se.search("a", 0, 10);
        assertEquals(109, result.getTotal());
        assertEquals(0, result.getOffset());
        assertEquals(10, result.getLimit());

        result = se.search("a", 3, 1);
        assertEquals(109, result.getTotal());
        assertEquals(3, result.getOffset());
        assertEquals(1, result.getLimit());
        assertEquals(1, result.getPlaces().size());
        assertEquals("Abborrberget", result.getPlaces().get(0).getName());
        
        result = se.search("a", 10, 0);
        assertEquals(109, result.getTotal());
        assertEquals(0, result.getPlaces().size());
        
        result = se.search("a", -10, 1);
        assertEquals(109, result.getTotal());
        assertEquals(1, result.getPlaces().size());
        assertEquals("Aapua", result.getPlaces().get(0).getName());
        
        result = se.search("a", 999, 10);
        assertEquals(109, result.getTotal());
        assertEquals(0, result.getPlaces().size());
    }
    
    public void testSearchLong() {
        SearchResult result = se.search("Malmšn, VŠstmanland");
        assertEquals(1, result.getPlaces().size());
    }
    
    public void testSearchMissing() {
        SearchResult result = se.search("Katten");
        assertEquals(0, result.getTotal());
        assertEquals(0, result.getPlaces().size());
    }
        
}
