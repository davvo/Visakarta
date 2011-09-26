package com.davvo.visakarta.shared;

import java.io.Serializable;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

@SuppressWarnings("serial")
public class Place implements Comparable<Place>, Serializable, Suggestion {

    private String name;    
    private LatLon pos;

    public Place() {        
    }
    
    public Place(String name, LatLon pos) {
        this.name = name;
        this.pos = pos;
    }
    
    public String getName() {
        return name;
    }

    public LatLon getPos() {
        return pos;
    }
    
    public String toString() {
        return name + ", " + pos;
    }

    @Override
    public int compareTo(Place o) {
        return name.compareTo(o.name);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } 
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        Place other = (Place) obj;
        
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String getDisplayString() {
        return name;
    }

    @Override
    public String getReplacementString() {
        return name;
    }

}
