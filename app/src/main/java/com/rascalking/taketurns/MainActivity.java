package com.rascalking.taketurns;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class MainActivity extends Activity {
    public static final String DEFAULT_KIDS_JSON = "[\"Juliet\", \"Cole\", \"Genevieve\"]";
    public static final String PREFS_NAME = "com.rascalking.taketurns.kids";
    public static final String PREFS_KEY = "kids";
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] kids = {"Juliet", "Cole", "Genevieve"};
    private ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapper = new ObjectMapper();
        getViews();
        setAdapters();
    }

    private void storeKids() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            String kidsJson = mapper.writeValueAsString(kids);
            editor.putString(PREFS_KEY, kidsJson);
            editor.commit();
        }
        catch (IOException e) {
            Log.e(TAG, "Error serializing kids to shared preferences", e);
        }
    }

    private void getViews() {
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapters() {
        adapter = new MyAdapter(kids);
        recyclerView.setAdapter(adapter);
    }
}
