package com.darkshire.zipcodecache;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.*;

/**
 * A leaned-down map of both locations and zip codes, that minimizes lookup time
 *
 * @author John Kim
 */
public class LocationCache /* implements Map<String,Location> */ {

    private static final int MIN_ZIP_CODE = 600;
    private static final int MAX_ZIP_CODE = 99999;
    
    private Map<String,Location> placeMap = new HashMap<>();
    private Location[] zipCodeArray = new Location[MAX_ZIP_CODE+1];
    
    public LocationCache() {
    }

    /**
     *  This parses strings into standardized "<locationName>, <stateAbbreviation>"
     */
    public String standardizeLocationName(String key) {
        return key.trim();
    }

    public Location get(String key) {
        try {
            int code = Integer.parseInt(key);
            if ((code > MIN_ZIP_CODE) && (code < MAX_ZIP_CODE)) {
                return zipCodeArray[code];
            }
        } catch (NumberFormatException e) { }
        String standardKey = standardizeLocationName(key);
        return placeMap.get(standardKey);
    }

    public void put(String key, Location location) {
        throw new RuntimeException("not available");
    }

    public int putAll(Map<String,Location> map) {
        throw new RuntimeException("not available");
    }

    /*
    public Set<Entry<String,Location>> entrySet() {
        return null;
    }
    */

    public Set<String> keySet() {
        return null;
    }

    public Set<Location> values() {
        return null;
    }

    public void clear() {
        throw new RuntimeException("not implemented");
    }

}
