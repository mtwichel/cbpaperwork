package com.example.android.cbpaperwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.zip.DataFormatException;

import data.Check;

public class NewCheck extends AppCompatActivity {

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
                Check check = null;
                try {
                    check = getValues(view);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(PaperworkActivity.REQUEST_CHECK, check);
                    setResult(2, resultIntent);
                    finish();
                } catch (DataFormatException e) {
                    //do nothing
                }

            }
        });
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


}
