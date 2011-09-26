package com.davvo.visakarta.server.search;

import java.io.BufferedReader;
import java.io.File;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.davvo.visakarta.shared.LatLon;
import com.davvo.visakarta.shared.Place;
import com.davvo.visakarta.shared.SearchResult;

public class SearchEngine {

    private static final int MAX_KEY_LENGTH = 5;
    private Map<String, List<Place>> index;
    
    public SearchEngine(File data, String charset) {
        index = new HashMap<String, List<Place>>();
        loadPlaces(data, charset);
        System.err.println("Index size = " + index.size());
    }
    
    public SearchResult search(String s) {
        return search(s, 0, Integer.MAX_VALUE);
    }
    
    public SearchResult search(String s, int offset, int limit) {
        List<Place> places = doSearch(s);
        int fromIndex = makeSureWithin(offset, 0, places.size());
        int toIndex = makeSureWithin(fromIndex + limit, 0, places.size());        
        return new SearchResult(offset, limit, places.size(), new ArrayList<Place>(places.subList(fromIndex, toIndex)));        
    }

    private List<Place> doSearch(String s) {
        s = s.toLowerCase().trim();
        
        if (s.length() <= MAX_KEY_LENGTH) {
            return getFromIndex(s);
        }
        
        String key = s.substring(0, MAX_KEY_LENGTH);
        List<Place> places = Collections.emptyList();
        
        for (Place place: getFromIndex(key)) {
            if (place.getName().toLowerCase().startsWith(s)) {
                if (places.isEmpty()) {
                    places = new ArrayList<Place>();
                }
                places.add(place);
            }
        }
        
        return places;
    }
    
    private List<Place> getFromIndex(String key) {
        if (index.containsKey(key)) {
            return Collections.unmodifiableList(index.get(key));
        }
        return Collections.emptyList();
    }
        
    private void loadPlaces(File data, String charset) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(data.getPath()), charset));
            Set<Place> places = new TreeSet<Place>(); 
            String line;
            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(";");
                String name = tokens[0];
                String[] pos = tokens[1].split(",");
                double lat = Double.parseDouble(pos[0]);
                double lon = Double.parseDouble(pos[1]);
                
                places.add(new Place(name, new LatLon(lat, lon)));
            }
             
            for (Place place: places) {
            
                int maxKeyLength = Math.min(place.getName().length(), MAX_KEY_LENGTH);
                
                for (int i = 0; i <= maxKeyLength; ++i) {
                    String key = place.getName().substring(0, i).toLowerCase();
                    List<Place> entry = index.get(key);
                    if (entry == null) {
                        entry = new ArrayList<Place>();
                        index.put(key, entry);
                    }
                    entry.add(place);  
                }
                
            }
            
            // Sort entries
            for (Map.Entry<String, List<Place>> e: index.entrySet()) {
                Collections.sort(e.getValue());
            }
            
        } catch (Exception x) {
            System.err.println("Error loading places: " + x.toString());
            x.printStackTrace();
        }
    }
    
    private int makeSureWithin(int v, int min, int max) {
        if (v > max) {
            return max;
        }
        if (v < min) {
            return min;
        }
        return v;
    }
    
}
