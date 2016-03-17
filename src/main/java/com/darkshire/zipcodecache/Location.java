package com.darkshire.zipcodecache;

/**
 * Represents lowest-level place and/or zip code data, with only name and location.
 *
 * @author John Kim
 */
public abstract class Location {

    private static final float PI_OVER_180 = ((float) Math.PI) / 180.0f;
    private static final float EARTH_AVERAGE_RADIUS_MILES = 3959f;
    private static final float DEFAULT_MILES_NEAR = 100;
    private static final float DEFAULT_RADIANS_NEAR = DEFAULT_MILES_NEAR / EARTH_AVERAGE_RADIUS_MILES;

    // For future use, there is an approximately 0.1% correction for varying Earth radius
    private static final double EARTH_POLAR_RADIUS_MILES = 3949.90;
    private static final double EARTH_EQUATORIAL_RADIUS_MILES = 3963.19;

    protected float lat; // the latitude in radians
    protected float lng; // the longitude in radians

    protected static final float deg2rad(float degrees) { return (degrees * PI_OVER_180); }
    protected static final float rad2deg(float radians) { return (radians / PI_OVER_180); }

    public static final double exactDistanceRadians(Location a, Location b) {
        return Math.acos(
                         Math.sin(a.lat)* Math.sin(b.lat) + Math.cos(a.lat)*Math.cos(b.lat)*Math.cos(a.lng-b.lng)
                         );
    }
    public static final double exactDistanceMiles(Location a, Location b) {
        return exactDistanceRadians(a,b) * EARTH_AVERAGE_RADIUS_MILES;
    }
    public static final float approximateDistanceRadians(Location a, Location b) {
        return (float) Math.sqrt( (float) Math.cos(a.lat) *
                                  (float) Math.cos(b.lat) *
                                  ( (a.lat-b.lat)*(a.lat-b.lat) +
                                    (a.lng-b.lng)*(a.lng-b.lng) )
                                  );
    }
    public static final float approximateDistanceMiles(Location a, Location b) {
        return approximateDistanceRadians(a,b) * EARTH_AVERAGE_RADIUS_MILES;
    }
    public static final boolean isNear(Location a, Location b, float radians) {
        return !( (a.lat < b.lat - radians) ||
                  (a.lat > b.lat + radians) ||
                  (a.lng < b.lng - radians) ||
                  (a.lng > b.lng + radians) );
    }
    public static final boolean isNear(Location a, Location b) {
        return isNear(a, b, DEFAULT_RADIANS_NEAR);
    }

    /**
     * Returns latitude in degrees. This is internally stored as a float (to minimize storage) 
     * of radians (to minimize calculation time). 
     * @return latitude in degrees
     */
    public float getLatitude() {
        return rad2deg(this.lat);
    }

    /**
     * Returns longitude in degrees. This is internally stored as a float (to minimize storage) 
     * of radians (to minimize calculation time). 
     * @return longitude in degrees
     */
    public float getLongitude() {
        return rad2deg(this.lng);
    }

    /**
     * Returns distance in radians between two locations. This requires minimal calculation, and is 
     * useful for determining relative distances. 
     * @param o Another location object
     * @return distance in radians
     */
    public float distanceTo(Location o) {
        return approximateDistanceRadians(this, o);
    }

    /**
     * Returns a boolean for whether the 
     * @param o Another location object
     * @return True if the other location is potentially close to this location
     */
    public boolean isNear(Location o) {
        return isNear(this, o);
    }

    /**
     * Returns a boolean for whether the 
     */
    public boolean isNear(Location o, float radians) {
        return isNear(this, o, radians);
    }
    
}
