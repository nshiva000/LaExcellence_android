package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.DownloadsAdapter;
import com.gmail.hyd.laexcellence.Models.DownloadsModel.Datum;
import com.gmail.hyd.laexcellence.Models.DownloadsModel.DownloadsModel;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Downloads extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Downloads");

        }

        getDownloads();


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }


    private void getDownloads() {
        Call<DownloadsModel> downloadsModelCall = RetrofitClient.getmInstance().getApi().getDownloads();

        final ProgressDialog progressDialog = new ProgressDialog(Downloads.this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        downloadsModelCall.enqueue(new Callback<DownloadsModel>() {
            @Override
            public void onResponse(Call<DownloadsModel> call, Response<DownloadsModel> response) {
                DownloadsModel downloadsModel = response.body();
                progressDialog.dismiss();


                if (downloadsModel != null) {

                    if (downloadsModel.getSuccess()) {

                        for (int i = 0; i < downloadsModel.getData().size(); i++) {
                            downloadsModel.getData().get(i).setId(i + 1);
                            arrayList.add(downloadsModel.getData().get(i));
                        }


                        adapter = new DownloadsAdapter(arrayList, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast_msg("some thing problem with server");
                    }
                } else {
                    Toast_msg("Internal Server Error");
                }


            }

            @Override
            public void onFailure(Call<DownloadsModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
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
