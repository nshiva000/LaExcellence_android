package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.TestPreviewAdapter;
import com.gmail.hyd.laexcellence.Interfaces.CustomItemClickListener;
import com.gmail.hyd.laexcellence.Models.QuestionBankWriteTestModel.Datum;
import com.gmail.hyd.laexcellence.Models.QuestionBankWriteTestModel.QuestionBankWriteTestModel;
import com.gmail.hyd.laexcellence.Models.TestResultsModel.Data;
import com.gmail.hyd.laexcellence.Models.TestResultsModel.TestResultsModel;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestPreviewActivity extends AppCompatActivity implements View.OnClickListener{



    String sid, tid;
    RecyclerView recyclerView;
    TestPreviewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private int current_id;
    private List<Datum> aquestionsLists;
    QuestionBankWriteTestModel questionBankWriteTestModel;
    ArrayList<String> qid_questionlist = new ArrayList<>();
    TextView prev_qid, prev_a, prev_b, prev_c, prev_d, prev_answer, correct_ans, marks, rank, prev_question, prev_explanation;
    CardView report, doubt;
    Button p_btn, n_btn;
    //WebView prev_explanation;

    ArrayList<String> QuestionNoList = new ArrayList<>();
    HashMap<Integer,Integer> correct_ans_map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_preview);
        current_id = 0;





        prev_qid = findViewById(R.id.preview_qid);
        prev_question = findViewById(R.id.preview_question);
        prev_a = findViewById(R.id.preview_a);
        prev_b = findViewById(R.id.preview_b);
        prev_c = findViewById(R.id.preview_c);
        prev_d = findViewById(R.id.preview_d);
        prev_answer = findViewById(R.id.preview_ans);
        prev_explanation = findViewById(R.id.preview_explanation);
        correct_ans = findViewById(R.id.correct_ans);
        marks = findViewById(R.id.preview_marks);
        rank = findViewById(R.id.correct_rank);
        doubt = findViewById(R.id.doubt);
        report = findViewById(R.id.report);
        p_btn = findViewById(R.id.btn_previous);
        n_btn = findViewById(R.id.btn_next);
        p_btn.setOnClickListener(this);
        n_btn.setOnClickListener(this);
        doubt.setOnClickListener(this);
        report.setOnClickListener(this);

        tid = getIntent().getStringExtra("tid");
        sid = SharedPrefManager.get_mInstance(this).getSid();

        recyclerView = findViewById(R.id.p_qid);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



//        prev_explanation.setWebViewClient(new WebViewClient());
//        prev_explanation.getSettings().setBuiltInZoomControls(true);
//        prev_explanation.getSettings().setDisplayZoomControls(false);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Test Preview");

        }


        questionBankTestList(sid,tid);

    }



    private void questionBankTestList(String sid,String tid){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<TestResultsModel> testResultsModelCall = RetrofitClient.getmInstance().getApi().get_question_results(sid,tid);
        testResultsModelCall.enqueue(new Callback<TestResultsModel>() {
            @Override
            public void onResponse(Call<TestResultsModel> call, Response<TestResultsModel> response) {
                TestResultsModel testResultsModel = response.body();
                if (testResultsModel != null){
                    Data data = testResultsModel.getData();
                    if (data.getMarks() != null){
                        rank.setText("Score : "+data.getMarks());
                        for (int i = 0; i<data.getCorrectBy().size();i++){
                            correct_ans_map.put(i,data.getCorrectBy().get(i).getCorrectBy());
                        }
                    }
                }else {
                    Toasta("server returned null value");
                }

            }

            @Override
            public void onFailure(Call<TestResultsModel> call, Throwable t) {

            }
        });

        Call<QuestionBankWriteTestModel> questionBankWriteTestModelCall = RetrofitClient.getmInstance().getApi().get_question_bank_test(sid,tid);
        questionBankWriteTestModelCall.enqueue(new Callback<QuestionBankWriteTestModel>() {
            @Override
            public void onResponse(Call<QuestionBankWriteTestModel> call, Response<QuestionBankWriteTestModel> response) {
                questionBankWriteTestModel = response.body();
                progressDialog.dismiss();

                if (questionBankWriteTestModel != null){
                    Toasta(questionBankWriteTestModel.getMessage());


                    int id = 1;
                    for (int i = 0; i < questionBankWriteTestModel.getData().size(); i++) {
                        qid_questionlist.add(questionBankWriteTestModel.getData().get(i).getId());
                        questionBankWriteTestModel.getData().get(i).setId(id + "");
                        String id_text = id + "";
                        id++;
                        QuestionNoList.add(id_text);
                    }

                    aquestionsLists = questionBankWriteTestModel.getData();
                    //setFirstValueData(aquestionsLists);
                    set_text_view();

                    adapter = new TestPreviewAdapter(TestPreviewActivity.this, QuestionNoList, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            current_id = position;
                            set_text_view();
                        }
                    });


                    recyclerView.setAdapter(adapter);

                }else {
                    Toasta("server returned null");
                }
            }

            @Override
            public void onFailure(Call<QuestionBankWriteTestModel> call, Throwable t) {
              Log.e("error",t.getLocalizedMessage());
            }
        });

    }


    private void set_correct_ans(){
        switch (aquestionsLists.get(current_id).getAns().toUpperCase()) {
            case "A":
                prev_a.setTextColor(getResources().getColor(R.color.green));
                prev_b.setTextColor(Color.BLACK);
                prev_c.setTextColor(Color.BLACK);
                prev_d.setTextColor(Color.BLACK);
                break;
            case "B":
                prev_b.setTextColor(getResources().getColor(R.color.green));
                prev_a.setTextColor(Color.BLACK);
                prev_c.setTextColor(Color.BLACK);
                prev_d.setTextColor(Color.BLACK);
                break;
            case "C":
                prev_c.setTextColor(getResources().getColor(R.color.green));
                prev_b.setTextColor(Color.BLACK);
                prev_a.setTextColor(Color.BLACK);
                prev_d.setTextColor(Color.BLACK);
                break;
            case "D":
                prev_d.setTextColor(getResources().getColor(R.color.green));
                prev_b.setTextColor(Color.BLACK);
                prev_a.setTextColor(Color.BLACK);
                prev_c.setTextColor(Color.BLACK);
                break;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_previous:
                previous();
                break;
            case R.id.btn_next:
                next();
                break;
        }
    }







    private void next() {

        int size_questions = aquestionsLists.size();

        if (current_id != size_questions - 1) {
            current_id++;
        }


        if (current_id < size_questions) {
            set_text_view();
        }
        if (current_id == aquestionsLists.size() - 1) {
            Toasta("limit completed");
        }

    }

    private void Toasta(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void previous() {
        if (current_id != 0) {
            Log.e("position-", current_id + "");
            current_id--;
        }

        if (current_id >= 0) {
            set_text_view();
        } else {
            Toasta("limit completed");
        }
    }



    private void set_text_view(){
        set_correct_ans();

        prev_qid.setText(aquestionsLists.get(current_id).getId());
        prev_question.setText(aquestionsLists.get(current_id).getQuestion());


//        String htmlExplanationString = questionBankWriteTestModel.getData().get(current_id).getExplanation().replaceAll("\r\n", "<br>").replace("\n", "<br>");
//        prev_explanation.loadDataWithBaseURL(null, htmlExplanationString, "text/html", "UTF-8", "UTF-8");

        String htmlExplanationString = questionBankWriteTestModel.getData().get(current_id).getExplanation().replaceAll("<br>", "\n\r").replace("<br>", "\n");
        prev_explanation.setText(htmlExplanationString);

        prev_a.setText("A) " + aquestionsLists.get(current_id).getA());
        prev_b.setText("B) " + aquestionsLists.get(current_id).getB());
        prev_c.setText("C) " + aquestionsLists.get(current_id).getC());
        prev_d.setText("D) " + aquestionsLists.get(current_id).getD());
        correct_ans.setText("Correct Ans : "+aquestionsLists.get(current_id).getAns());
    }



}
