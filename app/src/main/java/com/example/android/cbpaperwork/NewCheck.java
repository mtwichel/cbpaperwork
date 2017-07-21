package com.example.android.cbpaperwork;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.zip.DataFormatException;

import data.Check;

public class NewCheck extends AppCompatActivity {

    public static final String CHECK = "check";
    public static final int NEW_CHECK = 2;
    public static final int OLD_CHECK = 3;
    public static final int DELETE_CHECK = 4;
    private Check check;
    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_check);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_new_check);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    check = getValues(view);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(PaperworkActivity.REQUEST_CHECK, check);
                    setResult(requestCode, resultIntent);
                    finish();
                } catch (DataFormatException e) {
                    //do nothing
                }

            }
        });

        if (getIntent().getParcelableExtra(CHECK) != null) {
            check = getIntent().getParcelableExtra(CHECK);
            requestCode = OLD_CHECK;
        } else {
            check = new Check("", "", 0);
            requestCode = NEW_CHECK;
        }

        displayCheck();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Check getValues(View view) throws DataFormatException {
        EditText number = (EditText) findViewById(R.id.enterCheckNumber);
        EditText name = (EditText) findViewById(R.id.enterCheckName);
        EditText amount = (EditText) findViewById(R.id.enterCheckAmount);

        String numberS = number.getText().toString();
        String nameS = name.getText().toString();
        String amountS = amount.getText().toString();

        if(numberS.equals("")){

            Snackbar.make(view, "Please Enter a Check Number", Snackbar.LENGTH_LONG).show();
            throw new DataFormatException();
        }
        if(nameS.equals("")){
            Snackbar.make(view, "Please Enter a Name on the Check", Snackbar.LENGTH_LONG).show();
            throw new DataFormatException();
        }
        if(amountS.equals("")){
            Snackbar.make(view, "Please Enter a Check Amount", Snackbar.LENGTH_LONG).show();
            throw new DataFormatException();
        }

        return new Check(numberS, nameS, Double.parseDouble(amountS));
    }

    private void displayCheck() {
        EditText number = (EditText) findViewById(R.id.enterCheckNumber);
        EditText name = (EditText) findViewById(R.id.enterCheckName);
        EditText amount = (EditText) findViewById(R.id.enterCheckAmount);

        number.setText(check.getCheckNumber());
        name.setText(check.getCheckName());
        amount.setText("" + check.getAmount());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_check_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete Check")
                        .setMessage("Are You Sure You Want to Delete this Check?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (requestCode == OLD_CHECK) {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra(PaperworkActivity.REQUEST_CHECK, check);
                                    setResult(DELETE_CHECK, resultIntent);
                                    finish();
                                } else if (requestCode == NEW_CHECK) {
                                    check = null;
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra(PaperworkActivity.REQUEST_CHECK, check);
                                    setResult(requestCode, resultIntent);
                                    finish();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check, menu);
        return true;
    }


}
