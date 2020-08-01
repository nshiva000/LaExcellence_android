package com.gmail.hyd.laexcellence.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.DownloadsAdapter;
import com.gmail.hyd.laexcellence.Adapters.FormAnalysisAdapter;
import com.gmail.hyd.laexcellence.Adapters.TopicAnalysisAdapter;
import com.gmail.hyd.laexcellence.Models.DownloadsModel.Datum;
import com.gmail.hyd.laexcellence.Models.TestAnalysisModel.Formwise;
import com.gmail.hyd.laexcellence.Models.TestAnalysisModel.TestAnalysisModel;
import com.gmail.hyd.laexcellence.Models.TestAnalysisModel.Topicwise;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnalysisActivity extends AppCompatActivity {

    String sid,tid;

    RecyclerView topicRecyclerView,formRecyclerView;


    RecyclerView.Adapter adapter;
    RecyclerView.Adapter formAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Topicwise> topicArrayList = new ArrayList();
    ArrayList<Formwise> formArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Test Analysis");

        }

        topicRecyclerView = findViewById(R.id.topicRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        topicRecyclerView.setLayoutManager(layoutManager);
        topicRecyclerView.setHasFixedSize(true);

        adapter = new TopicAnalysisAdapter(getApplicationContext(),topicArrayList);
        topicRecyclerView.setAdapter(adapter);


        formRecyclerView = findViewById(R.id.formRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        formRecyclerView.setLayoutManager(layoutManager);
        formRecyclerView.setHasFixedSize(true);

        formAdapter = new FormAnalysisAdapter(getApplicationContext(),formArrayList);
        formRecyclerView.setAdapter(formAdapter);


        tid = getIntent().getStringExtra("tid");
        sid = SharedPrefManager.get_mInstance(this).getSid();
        getAnalysisData(sid,tid);


    }

    private void getAnalysisData(String sid,String tid){
        Call<TestAnalysisModel> call = RetrofitClient.getmInstance().getApi().get_test_analysis(sid,tid);
        call.enqueue(new Callback<TestAnalysisModel>() {
            @Override
            public void onResponse(Call<TestAnalysisModel> call, Response<TestAnalysisModel> response) {
                TestAnalysisModel testAnalysisModel = response.body();

                if (testAnalysisModel != null){
                    if (testAnalysisModel.getSuccess()){
                        ToasMsg(testAnalysisModel.getMessage());
                        topicArrayList.addAll(testAnalysisModel.getData().getTopicwise());
                        formArrayList.addAll(testAnalysisModel.getData().getFormwise());
                        adapter.notifyDataSetChanged();
                        formAdapter.notifyDataSetChanged();
                    }else {
                        ToasMsg(testAnalysisModel.getMessage());
                    }
                }else {
                    ToasMsg("server Resturned null response");
                }
            }

            @Override
            public void onFailure(Call<TestAnalysisModel> call, Throwable t) {
              ToasMsg(t.getMessage());
            }
        });
    }


    private void ToasMsg(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
