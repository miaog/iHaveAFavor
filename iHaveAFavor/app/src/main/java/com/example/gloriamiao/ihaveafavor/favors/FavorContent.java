package com.example.gloriamiao.ihaveafavor.favors;

import android.support.design.widget.Snackbar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
    private static void getFavors() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Favor");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> favors, ParseException e) {
                if (e == null) {
                    if (favors.size() != 0) {
                        for (int i = 0; i < favors.size(); i++) {
                            addItem(createFavorItem(favors.get(i).getString("desc"), favors.get(i).get("time"), favors.get(i).get("location"), favors.get(i).getObjectId()));
                        }

                    }
                }
                else {
                }
            }
        });
    };


    private static void addItem(FavorItem item) {
        // TODO: Query for the objects, implement sorting
        ITEMS.add(item);
        ITEM_MAP.put(item.description, item);
    }

    private static FavorItem createFavorItem(String description, Object location, Object dueTime, String key) {
        return new FavorItem(description, "Item " + description, makeDetails(description), key);
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
        public final String key;
        public FavorItem(String description, String location, String dueTime, String key) {
            this.description = description;
            this.location = location;
            this.dueTime = dueTime;
            this.key = key;
        }

        @Override
        public String toString() {
            return description;
        }
        public String getKey(){
            return key;
        }
    }
}
