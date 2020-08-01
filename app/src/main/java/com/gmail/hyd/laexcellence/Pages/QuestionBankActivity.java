package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.QuestionBankAdapter;
import com.gmail.hyd.laexcellence.Models.TestListModel.Datum;
import com.gmail.hyd.laexcellence.Models.TestListModel.TestListModel;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionBankActivity extends AppCompatActivity {
    String sid;

    RecyclerView recyclerView;
    QuestionBankAdapter questionBankAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Datum> arrayList = new ArrayList();
    Button create_new_exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bank);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Tests");

        }

        create_new_exam = findViewById(R.id.create_new_exam);

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        questionBankAdapter = new QuestionBankAdapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(questionBankAdapter);




        sid = sid = SharedPrefManager.get_mInstance(this).getSid();
        get_all_test(sid);

        create_new_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionBankActivity.this,CreateNewExamActivity.class);
                startActivity(intent);
            }
        });
    }


    private void get_all_test(String sid){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please Wait");
        progressDialog.show();

        Call<TestListModel> testListModelCall = RetrofitClient.getmInstance().getApi().get_all_tests(sid);
        testListModelCall.enqueue(new Callback<TestListModel>() {
            @Override
            public void onResponse(Call<TestListModel> call, Response<TestListModel> response) {
                progressDialog.dismiss();
                TestListModel testListModel = response.body();
                if (testListModel != null){
                    Log.e("result",testListModel.getMessage());
                    for (int i = 0; i < testListModel.getData().size(); i++){
                        arrayList.add(testListModel.getData().get(i));
                    }
                    questionBankAdapter.notifyDataSetChanged();
                    //arrayList.addAll(testListModel.getData().get(0));


                }else {
                    Toast.makeText(getApplicationContext(),"server responded null",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TestListModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error",t.getLocalizedMessage());

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

}
