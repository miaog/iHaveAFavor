package com.example.gloriamiao.ihaveafavor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import android.graphics.Color;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {
    public FavorUser favor;
    private int clicked = 0;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        Intent intent = getIntent();
        final FavorUser favor = intent.getParcelableExtra("favor");
        final String favor_key = intent.getParcelableExtra("favor_key");
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Change to query for whether the user has accepted this or not
                if (clicked == 0) {
                    clicked = 1;
                    fab.setImageResource(R.drawable.ic_cab_done_holo_light);

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Favor");
                    query.getInBackground(favor_key, new GetCallback<ParseObject>() {
                        public void done(ParseObject favors, ParseException e) {
                            if (e == null) {
                                favor.accept_favor(favors);
                            }
                        }
                    });

                    Snackbar.make(view, "ACCEPTED!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    clicked = 0;
                    fab.setImageResource(R.drawable.ic_cab_done_holo_dark);
                }
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
