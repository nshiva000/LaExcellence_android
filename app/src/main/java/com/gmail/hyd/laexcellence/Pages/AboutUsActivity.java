package com.gmail.hyd.laexcellence.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("About Us");

        }

    }


    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
