package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.NotificationAdapter;
import com.gmail.hyd.laexcellence.Models.Notification.Datum;
import com.gmail.hyd.laexcellence.Models.Notification.Notification;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Datum> datumArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Notifications");

        }
        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        get_notification();

    }


    private void get_notification() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.. Please Wait");
        progressDialog.show();
        String sid = SharedPrefManager.get_mInstance(getApplicationContext()).getSid();
        Call<Notification> notificationCall = RetrofitClient.getmInstance().getApi().get_notifications(sid);
        notificationCall.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                progressDialog.dismiss();
                Notification notification = response.body();
                if (notification != null) {
                    if (notification.getSuccess()) {
                        Toast_msg(notification.getMessage());
                        datumArrayList.addAll(notification.getData());


                        adapter = new NotificationAdapter(datumArrayList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast_msg(notification.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                progressDialog.dismiss();
                Toast_msg("server not responding please try again");
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }


    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
