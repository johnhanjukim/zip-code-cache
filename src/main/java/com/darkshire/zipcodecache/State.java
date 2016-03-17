package com.darkshire.zipcodecache;

import java.util.HashMap;
import java.util.Map;

public enum State {

    ALABAMA("Alabama","AL"),
    ALASKA("Alaska","AK"),
    ARIZONA("Arizona","AZ"),
    ARKANSAS("Arkansas","AR"),
    CALIFORNIA("California","CA"),
    COLORADO("Colorado","CO"),
    CONNECTICUT("Connecticut","CT"),
    DELAWARE("Delaware","DE"),
    DISTRICT_OF_COLUMBIA("District of Columbia","DC"),
    FLORIDA("Florida","FL"),
    GEORGIA("Georgia","GA"),
    HAWAII("Hawaii","HI"),
    IDAHO("Idaho","ID"),
    ILLINOIS("Illinois","IL"),
    INDIANA("Indiana","IN"),
    IOWA("Iowa","IA"),
    KANSAS("Kansas","KS"),
    KENTUCKY("Kentucky","KY"),
    LOUISIANA("Louisiana","LA"),
    MAINE("Maine","ME"),
    MARYLAND("Maryland","MD"),
    MASSACHUSETTS("Massachusetts","MA"),
    MICHIGAN("Michigan","MI"),
    MINNESOTA("Minnesota","MN"),
    MISSISSIPPI("Mississippi","MS"),
    MISSOURI("Missouri","MO"),
    MONTANA("Montana","MT"),
    NEBRASKA("Nebraska","NE"),
    NEVADA("Nevada","NV"),
    NEW_HAMPSHIRE("New Hampshire","NH"),
    NEW_JERSEY("New Jersey","NJ"),
    NEW_MEXICO("New Mexico","NM"),
    NEW_YORK("New York","NY"),
    NORTH_CAROLINA("North Carolina","NC"),
    NORTH_DAKOTA("North Dakota","ND"),
    OHIO("Ohio","OH"),
    OKLAHOMA("Oklahoma","OK"),
    OREGON("Oregon","OR"),
    PENNSYLVANIA("Pennsylvania","PA"),
    RHODE_ISLAND("Rhode Island","RI"),
    SOUTH_CAROLINA("South Carolina","SC"),
    SOUTH_DAKOTA("South Dakota","SD"),
    TENNESSEE("Tennessee","TN"),
    TEXAS("Texas","TX"),
    UTAH("Utah","UT"),
    VERMONT("Vermont","VT"),
    VIRGINIA("Virginia","VA"),
    WASHINGTON("Washington","WA"),
    WEST_VIRGINIA("West Virginia","WV"),
    WISCONSIN("Wisconsin","WI"),
    WYOMING("Wyoming","WY"),
    PUERTO_RICO("Puerto Rico","PR");

    public static final Map<String, State> STATE_BY_FULLNAME = new HashMap<String, State>();
    public static final Map<String, State> STATE_BY_ABBREVIATION = new HashMap<String, State>();
    static {
        for (State state : values()) {
            STATE_BY_FULLNAME.put(state.fullname.toLowerCase(), state);
            STATE_BY_ABBREVIATION.put(state.abbreviation.toLowerCase(), state);
        }
    }

    public final String fullname;
    public final String abbreviation;

    State(String fullname, String abbreviation) {
        this.fullname = fullname;
        this.abbreviation = abbreviation;
    }

    /**
     * Returns the fullname given the abbreviation for a state (case insensitive)
     * @param abbreviation State abbreviation string
     * @return The corresponding full name of the state
     */
    public static String getFullname(String abbreviation) {
        if (abbreviation == null) { return null; }
        return STATE_BY_ABBREVIATION.get(abbreviation.trim().toLowerCase()).fullname;
    }

    /**
     * Returns the abbreviation given the full name of a state (case insensitive)
     * @param fullname State full name string
     * @return The corresponding abbreviation for the state
     */
    public static String getAbbreviation(String fullname) {
        if (fullname == null) { return null; }
        return STATE_BY_FULLNAME.get(fullname.trim().toLowerCase()).abbreviation;
    }
    
    /**
     * Returns the state enum object given a name or abbreviation (case insensitive)
     * @param s Either a name or abbreviation for a state
     * @return The corresponding State enum object
     */
    public static State parse(String s) {
        if (s == null) { return null; }
        State state = STATE_BY_ABBREVIATION.get(s.trim().toLowerCase());
        if (state == null) {
            state = STATE_BY_FULLNAME.get(s.trim().toLowerCase());
        }
        return state;
    }
}
