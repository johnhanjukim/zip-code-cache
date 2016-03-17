package com.darkshire.zipcodecache;

/**
 * Represents lowest-level zip code data, only number and location.
 *
 * @author John Kim
 */
public class ZipCode extends Location {

    public int number;

    public ZipCode(String s, float latitude, float longitude) {
        this.number = Integer.parseInt(s);
        this.lat = deg2rad(latitude);
        this.lng = deg2rad(longitude);
    }
    
    public ZipCode(int n, float latitude, float longitude) {
        this.number = n;
        this.lat = deg2rad(latitude);
        this.lng = deg2rad(longitude);
    }

    public String toString() {
        return ""+number;
    }

}
