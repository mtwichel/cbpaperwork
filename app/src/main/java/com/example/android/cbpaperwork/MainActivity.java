package com.example.android.cbpaperwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.PaperworkData;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<String> displayList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PaperworkActivity.class);
                intent.putExtra(PaperworkActivity.INTENT_EXTRA, "00");
                startActivity(intent);
            }
        });


        Map<String, PaperworkData> map = JSONHelper.importAllFromJSON(this);
        List<PaperworkData> dataList = new ArrayList<>(map.values());
        displayList = new ArrayList<>();
        for (PaperworkData d: dataList) {
            displayList.add(d.getId());
        }
        listView = (ListView) findViewById(R.id.paperwork_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String selectedFromList = (listView.getItemAtPosition(position).toString());
        Toast.makeText(this, selectedFromList, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, PaperworkActivity.class);
        intent.putExtra(PaperworkActivity.INTENT_EXTRA, selectedFromList);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, PaperworkData> map = JSONHelper.importAllFromJSON(this);
        List<PaperworkData> dataList = new ArrayList<>(map.values());
        displayList = new ArrayList<>();
        for (PaperworkData d: dataList) {
            displayList.add(d.getId());
        }
        listView = (ListView) findViewById(R.id.paperwork_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(arrayAdapter);
    }
}
