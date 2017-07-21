package com.example.android.cbpaperwork;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import data.Check;
import data.PaperworkData;
import data.PaperworkDataWrapper;
import fragments.BankBagFrag;
import fragments.DepositFrag;
import fragments.OneTillFrag;
import fragments.OverShortFrag;
import fragments.TillFrag;

public class PaperworkActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, DepositFrag.OnFragmentInteractionListener, OverShortFrag.OnFragmentInteractionListener, TillFrag.OnFragmentInteractionListener, OneTillFrag.OnFragmentInteractionListener, BankBagFrag.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, ChangeClosersDialog.ChangeClosersDialogListeners {

    public static final String REQUEST_CHECK = "request_check";
    public static final String INTENT_EXTRA = "intent_extra";
    private static final int REQUEST_PERMISSION_WRITE = 1001;
    public static String INTENT_NEW = "intent_new";
    public static String INTENT_ID = "intent_id";

    private PaperworkData data;
    private PaperworkDataWrapper dataWrapper;

    private DatePickerDialog datePickerDialog;
    Toolbar toolbar;
    private boolean permissionGranted;
    private View navHeader;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperwork);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dataWrapper = getIntent().getParcelableExtra(INTENT_EXTRA);
        id = getIntent().getIntExtra(INTENT_ID, 1);
        if (getIntent().getBooleanExtra(INTENT_NEW, true)) {
            //new data
            Log.d("PaperworkActivity", "" + id);
            data = new PaperworkData(id);
            dataWrapper.addData(data);
            ChangeClosersDialog changeClosersDialog = new ChangeClosersDialog();
            Bundle b = new Bundle();
            b.putParcelable(ChangeClosersDialog.ARG_DATA, data);
            changeClosersDialog.setArguments(b);
            changeClosersDialog.show(getFragmentManager(), "closers");
        } else {

            Log.d("PaperworkActivity", "" + id);
            data = dataWrapper.dataList.get(id);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        fab.hide();

        navHeader = navigationView.getHeaderView(0);
        updateNavHeader();


        datePickerDialog = new DatePickerDialog(
                this, PaperworkActivity.this, data.getYear(), data.getMonth(), data.getDay());

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_overShort));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paperwork, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.action_change_date:
                datePickerDialog = new DatePickerDialog(this, PaperworkActivity.this, data.getYear(), data.getMonth(), data.getDay());
                datePickerDialog.show();
                break;

            case R.id.action_print:
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                PrintingHelper printingHelper = new PrintingHelper(this, data);

                break;
            case R.id.action_edit_closers:
                ChangeClosersDialog changeClosersDialog = new ChangeClosersDialog();
                Bundle b = new Bundle();
                b.putParcelable(ChangeClosersDialog.ARG_DATA, data);
                changeClosersDialog.setArguments(b);
                changeClosersDialog.show(getFragmentManager(), "closers");

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        data.setDate(day, month, year);
        Snackbar.make(findViewById(R.id.content_panel), "Date Set To: " + data.getDateString(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.revertToPreDate();
                updateNavHeader();
            }
        }).show();
        updateNavHeader();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_overShort:
                Bundle bundle = new Bundle();
                bundle.putParcelable(DepositFrag.ARG_DATA, data);
                fragment = new OverShortFrag();
                fragment.setArguments(bundle);
                title = "Over/Short Calculation";

                break;
            case R.id.nav_deposit:

                Bundle overShortBundle = new Bundle();
                overShortBundle.putParcelable(DepositFrag.ARG_DATA, data);
                fragment = new DepositFrag();
                fragment.setArguments(overShortBundle);
                title = "Deposit";
                break;

            case R.id.nav_tills:
                Bundle tillsBundle = new Bundle();
                tillsBundle.putParcelable(TillFrag.ARG_DATA, data);
                fragment = new TillFrag();
                fragment.setArguments(tillsBundle);
                title = "Tills";
                break;
            case R.id.nav_bank_bag:
                Bundle bankBagBundle = new Bundle();
                bankBagBundle.putParcelable(BankBagFrag.ARG_DATA, data);
                fragment = new BankBagFrag();
                fragment.setArguments(bankBagBundle);
                title = "Bank Bag";
                break;
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_panel, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    @Override
    public void onFragmentInteraction(int callerId, PaperworkData data) {
        this.data = data;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Check check = data.getParcelableExtra(PaperworkActivity.REQUEST_CHECK);
        if (check != null) {
            //not deleting new check
            if (resultCode == NewCheck.NEW_CHECK) {
                this.data.addCheck(check);
            } else if (resultCode == NewCheck.OLD_CHECK) {
                this.data.editCheck(check.getCheckNumber(), check);
            } else if (resultCode == NewCheck.DELETE_CHECK) {
                Log.d("PaperworkActivity", "About to delete Check");
                this.data.deleteCheck(check.getCheckNumber(), check);
            }
        }

        displayView(R.id.nav_deposit);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Main", "On Stop");
        if (!permissionGranted) {
            checkPermissions();
        }

        JSONHelper.exportToJSON(this, dataWrapper);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!permissionGranted) {
            checkPermissions();
        }

        JSONHelper.exportToJSON(this, dataWrapper);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Main", "On Restart");

        JSONHelper.importFromJSON(this, id);
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
    public void onChangeClosersDialogPositiveClick(android.app.DialogFragment dialog, String c1, String c2) {
        data.setClosers(c1, c2);
        updateNavHeader();

    }

    public void updateNavHeader() {
        TextView closerView = navHeader.findViewById(R.id.nav_closers);
        TextView dateView = navHeader.findViewById(R.id.nav_date);

        closerView.setText("Closers: " + data.getCloser(0) + " & " + data.getCloser(1));
        dateView.setText(data.getDateString());
    }

}
