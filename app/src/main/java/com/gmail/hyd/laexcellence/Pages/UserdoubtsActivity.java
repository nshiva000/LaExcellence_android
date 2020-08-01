package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.UserAllDoubtsAdapter;
import com.gmail.hyd.laexcellence.Models.UserAllDoubts.Datum;
import com.gmail.hyd.laexcellence.Models.UserAllDoubts.UserAllDoubts;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserdoubtsActivity extends AppCompatActivity {

    String sid;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> datumArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdoubts);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        sid = SharedPrefManager.get_mInstance(getApplicationContext()).getSid();


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Doubts");

        }

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        get_all_user_doubts();
    }


    private void get_all_user_doubts() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..Please wait.");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<UserAllDoubts> userAllDoubtsCall = RetrofitClient.getmInstance().getApi().user_all_doubts(sid);
        userAllDoubtsCall.enqueue(new Callback<UserAllDoubts>() {
            @Override
            public void onResponse(Call<UserAllDoubts> call, Response<UserAllDoubts> response) {
                progressDialog.dismiss();
                UserAllDoubts userAllDoubts = response.body();
                if (userAllDoubts != null) {
                    if (userAllDoubts.getSuccess()) {

                        if (userAllDoubts.getData().size() == 0) {
                            Toast("No Data to Show");
                        }


                        datumArrayList.addAll(userAllDoubts.getData());
                        adapter = new UserAllDoubtsAdapter(getApplicationContext(), datumArrayList);
                        recyclerView.setAdapter(adapter);


                    } else {
                        Toast(userAllDoubts.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserAllDoubts> call, Throwable t) {
                progressDialog.dismiss();
                Toast("server not responding, please try again later");
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


    private void Toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


}
