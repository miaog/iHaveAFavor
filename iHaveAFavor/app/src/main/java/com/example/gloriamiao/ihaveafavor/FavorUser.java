package com.example.gloriamiao.ihaveafavor;

/**
 * Created by vivek on 11/12/16.
 */
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vivek on 11/12/16.
 */

/*TODO
Implement the friends list stuff
Facebook login on Parse
Send/delete favors OK
Implement geo shit OK
Retrieve favor at launch if it exists OK
Implement getFavors KINDAOK
Implement acceptFavor
Implement fetchOffers()
Implement acceptService()
 */
public class FavorUser implements Parcelable{
    private AccessToken UserToken;
    private String userId;
    private String userName;
    private LinkedList<String> FriendList;
    private ParseObject currFavor;
    private List<ParseObject> favorlist;
    private List<ParseObject> offerlist;
    private List<ParseObject> transactionlist;
    private Activity ma;
    private LocationManager lm;

    public FavorUser(Activity m, LocationManager l) {
        ma = m;
        lm = l;
    }
    private int mData;
    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<FavorUser> CREATOR = new Parcelable.Creator<FavorUser>() {
        public FavorUser createFromParcel(Parcel in) {
            return new FavorUser(in);
        }

        public FavorUser[] newArray(int size) {
            return new FavorUser[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private FavorUser(Parcel in) {
        mData = in.readInt();
    }

    public void post_favor(String title, String text, String time, int hour, int minute){
        /*if (currFavor!=null){
            Snackbar.make(ma.findViewById(android.R.id.content), "Already posted a favor!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }*/

        //add the favor
        currFavor = new ParseObject("Favor");
        currFavor.put("id", userId);
        currFavor.put("name", userName);
        currFavor.put("desc", title);
        currFavor.put("hour", hour);
        currFavor.put("minute", minute);
        currFavor.put("location", text);
        currFavor.put("time", time);
        Location location;
        double longitude = 0;
        double latitude = 0;
        try {
            ActivityCompat.requestPermissions(ma, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }catch(SecurityException e){}

        ParseGeoPoint point = new ParseGeoPoint(latitude, longitude);
        currFavor.put("location",point);
        currFavor.saveInBackground();
    }

    public void accept_favor(ParseObject favor){
        ParseObject offer = new ParseObject("Offer");
        offer.put("favor", favor);
        offer.put("helper_id", userId);
        offer.put("id", favor.getInt("id"));
        offer.saveInBackground();
    }


    public void accept_offer(ParseObject offer){
        ParseObject transaction = new ParseObject("Transaction");
        transaction.put("helper_id", offer.getString("helper_id"));
        transaction.put("helped_id", userId);
        transaction.put("title", currFavor.get("title"));
        transaction.put("text", currFavor.get("text"));
        transaction.saveInBackground();

        //delete offers and favors
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
        query.whereEqualTo("favor", currFavor);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> offers, ParseException e) {
                if (e == null) {
                    if (offers.size() != 0) {
                        for (int i = 0; i<offers.size();i++)
                            offers.get(i).deleteInBackground();
                        offerlist.clear();
                    }
                    currFavor.deleteInBackground();
                    currFavor = null;

                } else {
                    Snackbar.make(ma.findViewById(android.R.id.content), "nothing", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        return;
    }

    public void del_favor(){
        if (currFavor != null){
            //should also delete all associated offers!
            currFavor.deleteInBackground();
            currFavor = null;
        }else{
            Snackbar.make(ma.findViewById(android.R.id.content), "no favor to delete", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


    public void get_info(){
        UserToken = AccessToken.getCurrentAccessToken();
        userId = "";
        userName = "";

        //initialize lists and objects
        currFavor  = null;
        FriendList = new LinkedList<String>();

        favorlist = new ArrayList<ParseObject>();
        offerlist= new ArrayList<ParseObject>();
        transactionlist = new ArrayList<ParseObject>();
        fetchFavors();

        //fill in user id, username and friends list
        GraphRequest request = GraphRequest.newMeRequest(
                UserToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            userId = object.getString("id");
                            userName = object.getString("name");
                            //Snackbar.make(ma.findViewById(android.R.id.content), "tis succesful", Snackbar.LENGTH_LONG)
                            //        .setAction("Action", null).show();

                            //check if there already exists a favor for us
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Favor");
                            query.whereEqualTo("id", userId);

                            query.getFirstInBackground(new GetCallback<ParseObject>() {
                                public void done(ParseObject object, ParseException e) {
                                    if (object == null) {
                                    } else {
                                        currFavor = object;
                                    }
                                }
                            });
                            fetchFavors();

                        }
                        catch(Exception e)
                        {}
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name");
        request.setParameters(parameters);
        request.executeAsync();



        /*
        FRIEND SHIT, TO REPAIR

        new GraphRequest(

                AccessToken.getCurrentAccessToken(),
                "/{user-id}/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONArray array = response.getJSONArray(); //weird
                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    Snackbar.make(ma.findViewById(android.R.id.content), array.getString(i), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    FriendList.add(array.getString(i));
                                } catch (Exception e) {
                                }
                            }
                        }else{
                            Snackbar.make(ma.findViewById(android.R.id.content), "no friends", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }
        ).executeAsync();
        */



    }

    public void fetchOffers(final View v){
        if(currFavor == null) //there can't be offers if there aren't any favors
            return;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
        query.whereEqualTo("favor", currFavor);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> offers, ParseException e) {
                if (e == null) {
                    if (offers.size() != 0) {
                        offerlist= offers;
                        //sort the favors TODO

                        Snackbar.make(v.findViewById(android.R.id.content), "lotsa offers", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                } else {
                    Snackbar.make(v.findViewById(android.R.id.content), "nothing", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public void fetchFavors(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Favor");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> favors, ParseException e) {
                if (e == null) {
                    if (favors.size() != 0) {
                        favorlist = favors;
                        //sort the favors TODO

                        Snackbar.make(ma.findViewById(android.R.id.content), "lotsa stuff", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                } else {
                    Snackbar.make(ma.findViewById(android.R.id.content), "nothing", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }


    public void fetchTransactions(View v){
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Transaction");
        query1.whereEqualTo("helper_id",userId);
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Transaction");
        query1.whereEqualTo("helped_id",userId);
        ArrayList <ParseQuery<ParseObject>> querylist = new ArrayList<ParseQuery<ParseObject>>();
        querylist.add(query1);
        querylist.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(querylist);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> transactions, ParseException e) {
                if (e == null) {
                    if (transactions.size() != 0) {
                        transactionlist = transactions;
                        //sort the favors TODO

                        Snackbar.make(v.findViewById(android.R.id.content), "lotsa transactions", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                } else {
                    Snackbar.make(ma.findViewById(android.R.id.content), "no transactions", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void initFavorList(){
        return;
    }
    /*
    public void getAllFavors();
    public void getFavors(int n);*/
}
