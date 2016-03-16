package com.darkshire.zipcodecache;

/**
 * Represents lowest-level zip code data, only number and location.
 *
 * @author John Kim
 */
public class ZipCode extends Place {

    public ZipCode(String name) {
        super(name);
    }
    
    public ZipCode(int n) {
        super(""+n);
    }

}
