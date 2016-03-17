package com.darkshire.zipcodecache;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;

/**
 * A parser for the tab-delimited files from the U.S. Census. 
 *
 * @author John Kim
 */
public class CensusDataParser {

    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private final static String PLACE_HEADER = "USPS\tGEOID\tANSICODE\tNAME\tLSAD\tFUNCSTAT\tALAND\tAWATER\tALAND_SQMI\tAWATER_SQMI\tINTPTLAT\tINTPTLONG";
    private final static String ZCTA_HEADER = "GEOID\tALAND\tAWATER\tALAND_SQMI\tAWATER_SQMI\tINTPTLAT\tINTPTLONG";
    private final static Pattern PLACE_SUFFIX = Pattern.compile("\\s+(city|town|CDP)$");
    private Path placeFile;
    private Path zctaFile;
    
    public CensusDataParser(String placeFilename, String zctaFilename) {
        this.placeFile = Paths.get(placeFilename);
        this.zctaFile = Paths.get(zctaFilename);
    }

    public CensusDataParser(Path placeFile, Path zctaFile) {
        this.placeFile = placeFile;
        this.zctaFile = zctaFile;
    }

    public void processPlaceLine(Map<String,Location> cache, String line) {
        String[] fields = line.split("\\t");
        // Fields: 0: USPS, 1: GEOID, 2: ANSICODE, 3: NAME, 4: LSAD, 5: FUNCSTAT,
        // 6: ALAND, 7: AWATER, 8: ALAND_SQMI, 9: AWATER_SQMI, 10: INTPTLAT, 11: INTPTLONG
        // Use standard "<local name>, <state code>" as key
        String name = PLACE_SUFFIX.matcher(fields[3]).replaceFirst("");
        State state = State.parse(fields[0]);
        float latitude = Float.parseFloat(fields[10]);
        float longitude = Float.parseFloat(fields[11]);
        Place place = new Place(name, state, latitude, longitude);

        String key = name + ", " + fields[0]; // "Boston, MA"
        cache.put(key, place);
    }

    public void processZctaLine(Map<String,Location> cache, String line) {
        String[] fields = line.split("\\t");
        // Fields:
        // 0: GEOID, 1: ALAND, 2: AWATER, 3: ALAND_SQMI, 4: AWATER_SQMI, 5: INTPTLAT, 6: INTPTLONG
        String key = fields[0];
        float latitude = Float.parseFloat(fields[5]);
        float longitude = Float.parseFloat(fields[6]);
        ZipCode zipCode = new ZipCode(key, latitude, longitude);
        cache.put(key, zipCode);
    }
    
    public void loadCache(Map<String,Location> cache) throws IOException {
        try (Scanner scanner =  new Scanner(placeFile, ENCODING.name())){
            if (!PLACE_HEADER.equals(scanner.nextLine().trim())) {
                throw new RuntimeException("Invalid place file header");
            }
            while (scanner.hasNextLine()){
                processPlaceLine(cache, scanner.nextLine());
            }
        }
        try (Scanner scanner =  new Scanner(zctaFile, ENCODING.name())){
            if (!ZCTA_HEADER.equals(scanner.nextLine().trim())) {
                throw new RuntimeException("Invalid place file header");
            }
            while (scanner.hasNextLine()){
                processZctaLine(cache, scanner.nextLine());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0 || args[0].startsWith("-h")) {
            System.out.println("java CensusDataParser <placeFile> <zctaFile> <placeName> [<placeName>]");
            System.exit(0);
        }
        CensusDataParser parser = new CensusDataParser(args[0], args[1]);
        Map<String,Location> map = new HashMap<String,Location>();
        try {
            parser.loadCache(map);
            if (args.length >= 3) {
                Location loc1 = map.get(args[2]);
                System.out.println("LOC: " + loc1 + " (" +loc1.getLatitude() + ", " + loc1.getLongitude() + ")");
                if (args.length >= 4) {
                    Location loc2 = map.get(args[3]);
                    System.out.println("LOC: " + loc2 + " (" +loc2.getLatitude() + ", " + loc2.getLongitude() + ")");
                    float d = Location.approximateDistanceMiles(loc1, loc2);
                    System.out.println("DISTANCE: " + d + " miles");
                    double e = Location.exactDistanceMiles(loc1, loc2);
                    System.out.println("EXACT DISTANCE: " + e + " miles");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }
}
