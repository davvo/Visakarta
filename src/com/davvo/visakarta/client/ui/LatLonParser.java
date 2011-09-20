package com.davvo.visakarta.client.ui;

import java.text.ParseException;

import com.davvo.visakarta.shared.LatLon;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.text.shared.Parser;

public class LatLonParser implements Parser<LatLon> {

    private static LatLonParser INSTANCE;
    
    private RegExp regexp = RegExp.compile("^(-?\\d+(\\.\\d+)?)\\s*,?\\s*(-?\\d+(\\.\\d+)?)$");
    
    public static LatLonParser instance() {
        if (INSTANCE == null) {
            INSTANCE = new LatLonParser();
        }
        return INSTANCE;
    }
    
    private LatLonParser() {
    }
    
    @Override
    public LatLon parse(CharSequence text) throws ParseException {
        MatchResult match = regexp.exec(text.toString());
        if (match == null) {
            throw new ParseException(text.toString(), 0);
        }
        
        double lat = Double.parseDouble(match.getGroup(1));
        double lon = Double.parseDouble(match.getGroup(3));
        return new LatLon(lat, lon);
    }

}
