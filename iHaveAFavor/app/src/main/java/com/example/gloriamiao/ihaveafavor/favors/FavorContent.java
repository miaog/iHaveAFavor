package com.example.gloriamiao.ihaveafavor.favors;

import android.support.design.widget.Snackbar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.io.IOException;

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

    private static int key = 0;

//    private static void getFavors() {
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Favor");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> favors, ParseException e) {
//                if (e == null) {
//                    if (favors.size() != 0) {
////                        for (intpackage com.example.gloriamiao.ihaveafavor.favors;
//
////                        import java.util.ArrayList;
////                        import java.util.HashMap;
////                        import java.util.List;
////                        import java.util.Map;
////                        import java.io.BufferedReader;
////                        import java.io.FileReader;
//
///**
// * This class provides the content for our app and inputs them into our database.
// */
//                        public class FavorContent {
//
//                            /**
//                             * An array of sample (Favor) items.
//                             */
//                            public static final List<FavorItem> ITEMS = new ArrayList<FavorItem>();
//
//                            /**
//                             * A map of sample (Favor) items, by ID.
//                             */
//                            public static final Map<String, FavorItem> ITEM_MAP = new HashMap<String, FavorItem>();
//
//                            private static final int COUNT = 5;
//
//                            static {
//                                // Add some sample items.
//                                for (int i = 1; i <= COUNT; i++){
//                                    addItem(createFavorItem("INSERT DESCRIPTION " + Integer.toString(i), "INSERT LOCATION", "INSERT DUE TIME"));
//                                }
//                            }
//
//                            private static void addItem(FavorItem item) {
//                                ITEMS.add(item);
//                                ITEM_MAP.put(item.description, item);
//                            }
//
//                            private static FavorItem createFavorItem(String description, String location, String dueTime) {
//                                return new FavorItem(description, "Item " + description, makeDetails(description));
//                            }
//
//                            private static String makeDetails(String position) {
//                                StringBuilder builder = new StringBuilder();
//                                builder.append("Details about Item: ").append(position);
//                                builder.append("\nMore details information here.");
//                                return builder.toString();
//                            }
//
//                            /**
//                             * A Favor item representing a piece of content.
//                             */
//                            public static class FavorItem {
//                                public final String description;
//                                public final String location;
//                                public final String dueTime;
//
//                                public FavorItem(String description, String location, String dueTime) {
//                                    this.description = description;
//                                    this.location = location;
//                                    this.dueTime = dueTime;
//                                }
//
//                                @Override
//                                public String toString() {
//                                    return description;
//                                }
//                            }
//                        }
//                        i = 0; i < favors.size(); i++) {
//                            addItem(createFavorItem(favors.get(i).getString("desc"), favors.get(i).get("time"), favors.get(i).get("location"), favors.get(i).getObjectId()));
//                        }
//
//                    }
//                }
//                else {
//                }
//            }
//        });
//    };
    public static Map<String,String> fields = new HashMap<String,String>(){
        {
            put("I NEED A BLUE BOOK ASAP. Find me on the benches on first Evans!444-541-4535", "444-541-4535|8:30 PM");
            put("I lost my CS189 notes :( Someone send them to me?","george@berkeley.edu|9:00 PM");
            put("Can someone sneak me food... Im on Mainstacks, first table to the left by the Entrance","imsohungry@its5AM.pie|1:30 AM");
        }

    };
    static {
        // Add some sample items.
        for (Map.Entry<String, String> entry : fields.entrySet()){
            String[] fields = entry.getValue().split("|");
            addItem(createFavorItem(entry.getKey(),fields[0],fields[1]));
        }
    }

    private static void addItem(FavorItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.description, item);
    }

    private static FavorItem createFavorItem(String description, String contact, String dueTime) {
        key++;
        makeDetails(contact, dueTime);
        return new FavorItem(description, contact, dueTime, Integer.toString(key));
    }

    private static String makeDetails(String contact, String dueTime) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(dueTime);
        builder.append("\nMore details information here.").append(contact);
        return builder.toString();
    }

    /**
     * A Favor item representing a piece of content.
     */
    public static class FavorItem {
        public final String description;
        public final String contact;
        public final String dueTime;
        public final String key;
        public FavorItem(String description, String contact, String dueTime, String key) {
            this.description = description;
            this.contact = contact;
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
