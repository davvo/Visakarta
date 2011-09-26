package com.davvo.visakarta.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class SearchResult implements Serializable {

    private int offset;
    private int limit;    
    private int total;
    private ArrayList<Place> places;
    
    public SearchResult(int offset, int limit, int total, ArrayList<Place> places) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.places = places;
    }
    
    public SearchResult() {
    }
    
    public int getOffset() {
        return offset;
    }
    
    public int getLimit() {
        return limit;
    }
    
    public int getTotal() {
        return total;
    }
    
    public List<Place> getPlaces() {
        return places;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(places.size()).append(";").append(getTotal()).append(";");
        sb.append(getOffset()).append(";").append(getLimit()).append("\n");
        for (Place place: places) {
            sb.append(place).append("\n");
        }
        return sb.toString();
    }
    
    
    
}
