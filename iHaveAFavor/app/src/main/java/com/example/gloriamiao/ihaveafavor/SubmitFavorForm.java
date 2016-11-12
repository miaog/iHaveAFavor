package com.example.gloriamiao.ihaveafavor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.gloriamiao.ihaveafavor.ItemListActivity;
import com.example.gloriamiao.ihaveafavor.R;
import com.parse.ParseObject;

public class SubmitFavorForm extends AppCompatActivity {

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
    public void submitForm(View view){
        EditText favor = (EditText) findViewById(R.id.favor_message);
        EditText place = (EditText) findViewById(R.id.place_message);
        EditText time = (EditText) findViewById(R.id.time_message);
        String f = favor.getText().toString();
        String p = place.getText().toString();
        String t = time.getText().toString();
        //hash: NyqjT3gSNK7evHtV5kSxj3ZXKcs=
          //      appid: 1508618309155393
        ParseObject testObject = new ParseObject("Testing");
        testObject.put("favor", f);
        testObject.put("place", p);
        testObject.put("time", t);
        testObject.saveInBackground();
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
