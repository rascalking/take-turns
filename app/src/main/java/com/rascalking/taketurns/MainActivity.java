package com.rascalking.taketurns;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public static final String PREFS_NAME = "com.rascalking.taketurns.kids";
    public static final String DEFAULT_KIDS_JSON = "[\"Juliet\", \"Cole\", \"Genevieve\"]";
    public static final String PREFS_KEY = "kids";

    private ArrayAdapter<String> adapter;
    private List<String> kids;
    private ListView listView;
    private ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapper = new ObjectMapper();
        getKidsList();
        getViews();
        setListeners();
        setAdapters();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop(){
        super.onStop();
        storeKids();
    }

    private void storeKids() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            String kidsJson = mapper.writeValueAsString(kids);
            editor.putString(PREFS_KEY, kidsJson);
            editor.commit();
        }
        catch (IOException e) {

        }
    }

    private void getKidsList() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        String kidsJson = prefs.getString(PREFS_KEY, DEFAULT_KIDS_JSON);
        try {
            kids = mapper.readValue(kidsJson, List.class);
        }
        catch (IOException e) {
            kids = new ArrayList<>();
        }
    }

    private void getViews() {
        listView = (ListView) findViewById(R.id.listView);
    }

    private void setAdapters() {
        adapter = new ArrayAdapter<>(this,
                                     android.R.layout.simple_list_item_1,
                                     kids);
        listView.setAdapter(adapter);
    }

    private void setListeners() {
        // Define listener for clicking on an item​
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String kid = adapter.getItem(position);
                adapter.remove(kid);
                adapter.add(kid);
            }
        };

        listView.setOnItemClickListener(clickListener);
    }
}
