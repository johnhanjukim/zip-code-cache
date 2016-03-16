package com.darkshire.zipcodecache;

/**
 * Represents lowest-level place and/or zip code data, with only name and location.
 *
 * @author John Kim
 */
public class Place {

    private static final float PI_OVER_180 = ((float) Math.PI) / 180.0f;
    private static final float EARTH_RADIUS_MILES = 3959f;

    protected String name;
    protected float lat; // the latitude in radians
    protected float lng; // the longitude in radians

    private static final float deg2rad(float degrees) { return (degrees * PI_OVER_180); }
    private static float rad2deg(float radians) { return (radians / PI_OVER_180); }

    public static double exactDistance(Place a, Place b) {
        double angle = Math.sin(a.lat)* Math.sin(b.lat) +
            Math.cos(a.lat)*Math.cos(b.lat)*Math.cos(a.lng-b.lng);
        double exact_distance = 60 * 1.1515 * Math.acos(angle) * 180 / Math.PI;
        return exact_distance;
    }

    /**
     * Create Place with a given name
     */
    public Place(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return rad2deg(this.lat);
    }
    public void setLatitude(float degrees) {
        this.lat = deg2rad(degrees);
    }
    public void setLatitude(String s) {
        setLatitude(Float.parseFloat(s));
    }

    public float getLongitude() {
        return rad2deg(this.lng);
    }
    public void setLongitude(float degrees) {
        this.lng = deg2rad(degrees);
    }
    public void setLongitude(String s) {
        setLongitude(Float.parseFloat(s));
    }

    /**
     * Returns distance in miles between two zip codes
     */
    public float distanceTo(Place o) {
        float angle = (float) Math.sqrt(
                                        (float) Math.cos(this.lat) *
                                        (float) Math.cos(o.lat) *
                                        ( (this.lat-o.lat)*(this.lat-o.lat) + 
                                          (this.lng-o.lng)*(this.lng-o.lng) )
                                        ) * EARTH_RADIUS_MILES;
        return angle;
    }

    public String toString() {
        return name;
    }

}
