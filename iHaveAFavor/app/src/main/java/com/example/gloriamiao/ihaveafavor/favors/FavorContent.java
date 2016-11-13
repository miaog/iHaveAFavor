package com.example.gloriamiao.ihaveafavor.favors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class provides the content for our app and inputs them into our database.
 */
public class FavorContent {

    /**
     * An array of sample (Favor) items.
     */
    public static final List<FavorItem> ITEMS = new ArrayList<FavorItem>();

    /**
     * A map of sample (Favor) items, by ID.
     */
    public static final Map<String, FavorItem> ITEM_MAP = new HashMap<String, FavorItem>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++){
            addItem(createFavorItem("INSERT DESCRIPTION " + Integer.toString(i), "INSERT LOCATION", "INSERT DUE TIME"));
        }
    }

    private static void addItem(FavorItem item) {
        // TODO: Query for the objects, implement sorting
        ITEMS.add(item);
        ITEM_MAP.put(item.description, item);
    }

    private static FavorItem createFavorItem(String description, String location, String dueTime) {
        return new FavorItem(description, "Item " + description, makeDetails(description));
    }

    private static String makeDetails(String position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        builder.append("\nMore details information here.");
        return builder.toString();
    }

    /**
     * A Favor item representing a piece of content.
     */
    public static class FavorItem {
        public final String description;
        public final String location;
        public final String dueTime;

        public FavorItem(String description, String location, String dueTime) {
            this.description = description;
            this.location = location;
            this.dueTime = dueTime;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
