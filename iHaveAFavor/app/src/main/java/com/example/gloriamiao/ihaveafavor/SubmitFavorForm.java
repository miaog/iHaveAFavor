package com.example.gloriamiao.ihaveafavor;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.gloriamiao.ihaveafavor.ItemListActivity;
import com.example.gloriamiao.ihaveafavor.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubmitFavorForm extends AppCompatActivity {

    static int chosenHour = 0;
    static int chosenMin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_favor_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            chosenHour = hourOfDay;
            chosenMin = minute;
        }
    }

    public void selectLocation(View view){
        //Use your geolocation stuff here
        TextView mTextView = (TextView) findViewById(R.id.place_message);
        mTextView.setText("text here");
    }
    public void submitFavor(View view){
        EditText favor = (EditText) findViewById(R.id.favor_message);
        TextView place = (TextView) findViewById(R.id.place_message);
        TextView time = (TextView) findViewById(R.id.time_message);
        String f = favor.getText().toString();
        String p = place.getText().toString();
        String t = time.getText().toString();
        String requester = "Sasa";
        ParseObject testObject = new ParseObject("Favor");
        testObject.put("favor", f);
        testObject.put("place", p);
        testObject.put("time", t);
        testObject.put("hour", chosenHour);
        testObject.put("minute", chosenMin);
        testObject.put("requester", requester);
        testObject.put("accepted", false);
        testObject.saveInBackground();

        ParseObject profileObject = new ParseObject(requester);
        ParseQuery<ParseObject> q = ParseQuery.getQuery(requester);
        q.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
            if (e == null) {
            } else {
            }
        }});
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Favor");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void destroyWindow(View view){
        finish();
    }
}