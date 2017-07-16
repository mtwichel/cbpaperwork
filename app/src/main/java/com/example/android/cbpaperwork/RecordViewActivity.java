package com.example.android.cbpaperwork;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class RecordViewActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public static final String ARG_DATA = "arg_data";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_overShort:
                    mTextMessage.setText("overshort");
                    return true;
                case R.id.navigation_deposit:
                    mTextMessage.setText("deposit");
                    return true;
                case R.id.navigation_tills:
                    mTextMessage.setText("tills");
                    return true;
                case R.id.navigation_change_bag:
                    mTextMessage.setText("change bag");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_view);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
