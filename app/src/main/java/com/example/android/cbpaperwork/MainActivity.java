package com.example.android.cbpaperwork;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.DataFormatException;

import data.PaperworkData;
import data.PaperworkDataWrapper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int REQUEST_PERMISSION_WRITE = 1002;
    private ArrayList<String> displayList;
    private ListView listView;
    List<PaperworkData> dataList;
    PaperworkDataWrapper dataWrapper;
    private boolean permissionGranted;

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
                intent.putExtra(PaperworkActivity.INTENT_EXTRA, dataWrapper);
                intent.putExtra(PaperworkActivity.INTENT_NEW, true);
                intent.putExtra(PaperworkActivity.INTENT_ID, dataWrapper.dataList.size());
                startActivity(intent);
            }
        });


        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_clear_data:
                new AlertDialog.Builder(this)
                        .setTitle("Clear All Data")
                        .setMessage("Are You Sure You Want to Clear All Data? This CANNOT be undone!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                JSONHelper.deleteJson();
                                getData();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Snackbar.make(listView, "ToDo: Replace with record view", Snackbar.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getData();
    }

    public void getData() {
        if (!permissionGranted) {
            checkPermissions();
        }
        dataWrapper = JSONHelper.importFromJSON(this);

        listView = (ListView) findViewById(R.id.paperwork_list);
        PaperworkDataListAdapter arrayAdapter = new PaperworkDataListAdapter(this, R.layout.paperwork_item, dataWrapper.dataList);
        arrayAdapter.sort(new Comparator<PaperworkData>() {
            @Override
            public int compare(PaperworkData data1, PaperworkData data2) {
                if (data1.getId() < data2.getId()) {
                    //1 < 2
                    return -1;
                } else if (data1.getId() < data2.getId()) {
                    return 1;
                } else if (data1.getId() == data2.getId()) {
                    try {
                        throw new DataFormatException();
                    } catch (DataFormatException e) {
                        e.printStackTrace();
                    }
                }
                return 0;
            }
        });
        listView.setAdapter(arrayAdapter);

        registerForContextMenu(listView);
        listView.setOnItemClickListener(this);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.paperwork_list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_selected_paperwork, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete Paperwork Data")
                        .setMessage("Are You Sure You Want to Delete this Data? This CANNOT be undone!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dataWrapper.dataList.remove(info.position);
                                JSONHelper.exportToJSON(MainActivity.this, dataWrapper);
                                getData();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


                return true;
            case R.id.edit:
                Log.d("PaperworkActivity", "Position: " + info.position);
                Intent intent = new Intent(MainActivity.this, PaperworkActivity.class);
                intent.putExtra(PaperworkActivity.INTENT_EXTRA, dataWrapper);
                intent.putExtra(PaperworkActivity.INTENT_NEW, false);
                intent.putExtra(PaperworkActivity.INTENT_ID, info.position);

                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
