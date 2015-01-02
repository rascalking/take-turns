package com.rascalking.taketurns;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private ArrayList<String> kids;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void getKidsList() {
        kids = new ArrayList<String>();
        kids.add("Juliet");
        kids.add("Cole");
        kids.add("Genevieve");
    }

    private void getViews() {
        listView = (ListView) findViewById(R.id.listView);
    }

    private void setAdapters() {
        adapter = new ArrayAdapter<String>(this,
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
