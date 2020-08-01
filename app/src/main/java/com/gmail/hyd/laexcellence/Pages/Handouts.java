package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.HandoutsAdapter;
import com.gmail.hyd.laexcellence.Models.HandoutsModel.Datum;
import com.gmail.hyd.laexcellence.Models.HandoutsModel.HandoutsModel;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Handouts extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handouts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Handouts");

        }

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        getHandouts();
    }


    private void getHandouts() {
        Call<HandoutsModel> handoutsModelCall = RetrofitClient.getmInstance().getApi().getHandouts();

        final ProgressDialog progressDialog = new ProgressDialog(Handouts.this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        handoutsModelCall.enqueue(new Callback<HandoutsModel>() {
            @Override
            public void onResponse(Call<HandoutsModel> call, Response<HandoutsModel> response) {
                HandoutsModel handoutsModel = response.body();
                progressDialog.dismiss();


                if (handoutsModel != null) {

                    if (handoutsModel.getSuccess()) {

                        for (int i = 0; i < handoutsModel.getData().size(); i++) {

                            handoutsModel.getData().get(i).setId(i + 1);
                            arrayList.add(handoutsModel.getData().get(i));
                        }


                        adapter = new HandoutsAdapter(arrayList, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast_msg("some thing problem with server");
                    }
                } else {
                    Toast_msg("Internal Server Error");
                }


            }

            @Override
            public void onFailure(Call<HandoutsModel> call, Throwable t) {
                progressDialog.dismiss();
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
