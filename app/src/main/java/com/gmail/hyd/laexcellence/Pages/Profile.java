package com.gmail.hyd.laexcellence.Pages;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;
import com.squareup.picasso.Picasso;


public class Profile extends AppCompatActivity {

    TextView username, email, mobile, batch, admission;
    String sid, name, image, batch_s, mobile_s, admissionId, email_s;
    ImageView profile;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Profile");

        }

        username = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        batch = findViewById(R.id.batch);
        profile = findViewById(R.id.profile_img);
        admission = findViewById(R.id.admission);

        sid = SharedPrefManager.get_mInstance(this).getSid();
        image = SharedPrefManager.get_mInstance(this).getImage();
        name = SharedPrefManager.get_mInstance(this).getName();
        batch_s = SharedPrefManager.get_mInstance(this).getBatch();
        mobile_s = SharedPrefManager.get_mInstance(this).getMobile();
        admissionId = SharedPrefManager.get_mInstance(this).getAdmissionId();
        email_s = SharedPrefManager.get_mInstance(this).getEmail();


        String user_name = getIntent().getStringExtra("user_name");

        username.setText(user_name);

        if (!email_s.isEmpty()) {
            email.setText(email_s);
        } else {
            email.setText("Update your Email");
            email.setTextSize(10);
            email.setTextColor(Color.RED);
        }

        mobile.setText(mobile_s);
        batch.setText(batch_s);
        Picasso.get().load(image).into(profile);
        admission.setText(admissionId);


        //times = new Time();


        //boolean i = times.isTimeBetweenTwoTime()


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Profile.this, Edit.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }





       /* Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
        int minute = now.get(Calendar.MINUTE);

        Date date = parseDate(hour + ":" + minute);
        Date dateCompareOne = parseDate("08:00");
        Date dateCompareTwo = parseDate("20:00");

        if (dateCompareOne.before( date ) && dateCompareTwo.after(date)) {
            //your logic
        }

        private Date parseDate(String date) {

            final String inputFormat = "HH:mm";
            SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
            try {
                return inputParser.parse(date);
            } catch (java.text.ParseException e) {
                return new Date(0);
            }
        }

        */


}
