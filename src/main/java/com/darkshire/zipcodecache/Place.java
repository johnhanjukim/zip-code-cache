package com.darkshire.zipcodecache;

/**
 *  This represents the simplest data on a U.S. place, including only name and state. 
 *  It is get small to minimize memory use to cache all places. 
 *
 * @author John Kim
 */
public class Place extends Location {

    public final String name;
    public final State state;

    /**
     * Create Place with a given name string and State object, with latitude and longitude
     */
    public Place(String name, State state, float latitude, float longitude) {
        this.name = name;
        this.state = state;
        this.lat = deg2rad(latitude);
        this.lng = deg2rad(longitude);
    }

    public int hashCode() {
        return name.hashCode() + state.hashCode();
    }

    public String toString() {
        return name + ", " + state.abbreviation;
    }

}
