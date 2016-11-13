package com.example.gloriamiao.ihaveafavor;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



import com.example.gloriamiao.ihaveafavor.ItemListActivity;
import com.example.gloriamiao.ihaveafavor.R;
import com.parse.ParseGeoPoint;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SubmitFavorForm extends AppCompatActivity {
    private Location location;
    public FavorUser favor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_favor_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        FavorUser favor = intent.getParcelableExtra("favor");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void submitFavor(View view){
        EditText fav = (EditText) findViewById(R.id.favor_message);
        String f = fav.getText().toString();
        EditText desc = (EditText) findViewById(R.id.description_message);
        String d = desc.getText().toString();
        EditText time = (EditText) findViewById(R.id.time_message);
        String t = time.getText().toString();
        favor.post_favor(f, d,t);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void destroyWindow(View view){
        finish();
    }
}