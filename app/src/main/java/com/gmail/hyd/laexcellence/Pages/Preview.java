package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.PreviewAdapter;
import com.gmail.hyd.laexcellence.Interfaces.CustomItemClickListener;
import com.gmail.hyd.laexcellence.Models.QuestionsList.QuestionsList;
import com.gmail.hyd.laexcellence.Models.RaiseDoubt.RaiseDoubt;
import com.gmail.hyd.laexcellence.Models.ReportQuestion.ReportQuestion;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Preview extends AppCompatActivity implements View.OnClickListener {


    String exam_id, sid, my_answers, correct_answer, marks_s, rank_s;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<QuestionsList> arrayList = new ArrayList();
    private int current_id;
    private List<QuestionsList> aquestionsLists;


    ArrayList<String> qid_questionlist = new ArrayList<>();

    ArrayList ans_array = new ArrayList();

    String foo = my_answers;

    TextView prev_qid, prev_a, prev_b, prev_c, prev_d, prev_answer, correct_ans, marks, rank;
    CardView report, doubt;
    Button p_btn, n_btn;

    WebView prev_question, prev_explanation;

    HashMap<String, String> q_id = new HashMap<>();


    private List<QuestionsList> q_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

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


        prev_question.setWebViewClient(new WebViewClient());
        prev_question.getSettings().setBuiltInZoomControls(true);
        prev_question.getSettings().setDisplayZoomControls(false);


        prev_explanation.setWebViewClient(new WebViewClient());
        prev_explanation.getSettings().setBuiltInZoomControls(true);
        prev_explanation.getSettings().setDisplayZoomControls(false);

        p_btn.setOnClickListener(this);
        n_btn.setOnClickListener(this);

        doubt.setOnClickListener(this);
        report.setOnClickListener(this);


        exam_id = getIntent().getStringExtra("examId");
        sid = SharedPrefManager.get_mInstance(this).getSid();
        my_answers = getIntent().getStringExtra("my_answers");
        marks_s = getIntent().getStringExtra("marks");
        rank_s = getIntent().getStringExtra("rank");

//        if (marks_s.isEmpty()){
//            String str = marks_s;
//
//            if(str.length() > 0){
//                String substr = ".";
//                String before = str.substring(0, str.indexOf(substr));
//                marks.setText("Marks : "+before);
//            }
//
//        }


        marks.setText("Marks : " + marks_s);


        if (!rank_s.isEmpty()) {
            String str1 = rank_s;


            if (str1.length() > 0) {
                String substr1 = ".";
                String before1 = str1.substring(0, str1.indexOf(substr1));
                rank.setText("Rank : " + before1);
            }


        }


        Log.e("details", exam_id + my_answers + sid);


        recyclerView = findViewById(R.id.p_qid);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Test Preview");

        }


        getAnsList(sid);

        boolean q = false;


        String foo = my_answers;
        String[] split = foo.split(",");
        for (int i = 0; i < split.length; i++) {

            ans_array.add(split[i]);

        }


        Log.e("explode", ans_array.toString());


        //Log.e("q", q+"");


    }


    private void getAnsList(String s) {

        final ProgressDialog progressDialog = new ProgressDialog(Preview.this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<List<QuestionsList>> call = RetrofitClient.getmInstance().getApi().get_all_questions(exam_id);

        call.enqueue(new Callback<List<QuestionsList>>() {
            @Override
            public void onResponse(Call<List<QuestionsList>> call, Response<List<QuestionsList>> response) {
                final List<QuestionsList> questionsLists = response.body();


                Log.e("aa", questionsLists.toString());

                int id = 1;
                for (int i = 0; i < questionsLists.size(); i++) {
                    qid_questionlist.add(questionsLists.get(i).getId());
                    questionsLists.get(i).setId(id + "");
                    String id_text = id + "";
                    id++;
                }


                aquestionsLists = questionsLists;
                progressDialog.dismiss();

                if (ans_array.size() > 1) {
                    questionsLists.get(0).setMy_ans(ans_array);
                } else {

                    //ArrayList arrayList_new_u = new ArrayList();

                    for (int i = 0; i < questionsLists.size(); i++) {
                        ans_array.add(i, "U");
                    }

                    questionsLists.get(0).setMy_ans(ans_array);
                }


                Log.e("my", ans_array.size() + "" + questionsLists.size() + ans_array.toString());

                Log.d("recycler", "clicked position:" + questionsLists.get(0).getQuestion());

                correct_answer = "Correct ans :" + questionsLists.get(0).getAns().toUpperCase();

                prev_qid.setText(questionsLists.get(0).getId());

                String htmlString = questionsLists.get(0).getQuestion().replaceAll("\r\n", "<br>").replace("\n", "<br>");
                prev_question.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", "UTF-8");

                //prev_question.setText(questionsLists.get(0).getQuestion());

                prev_a.setText("A) " + questionsLists.get(0).getA());
                prev_b.setText("B) " + questionsLists.get(0).getB());
                prev_c.setText("C) " + questionsLists.get(0).getC());
                prev_d.setText("D) " + questionsLists.get(0).getD());


                String htmlExplanationString = questionsLists.get(0).getExplanation().replaceAll("\r\n", "<br>").replace("\n", "<br>");
                prev_explanation.loadDataWithBaseURL(null, htmlExplanationString, "text/html", "UTF-8", "UTF-8");


                // prev_explanation.setText(questionsLists.get(0).getExplanation());
                correct_ans.setText(correct_answer);
                initial(questionsLists, ans_array);


                adapter = new PreviewAdapter(getApplicationContext(), questionsLists, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.d("recycler", "clicked position:" + position);
                        current_id = position;


                        ArrayList ans_aa = questionsLists.get(0).getMy_ans();


                        prev_qid.setText(questionsLists.get(current_id).getId());
                        //prev_question.setText(questionsLists.get(current_id).getQuestion());


                        String htmlString = questionsLists.get(current_id).getQuestion().replaceAll("\r\n", "<br>").replace("\n", "<br>");
                        prev_question.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", "UTF-8");


                        correct_answer = "Correct ans :" + questionsLists.get(current_id).getAns().toUpperCase();

                        prev_a.setText("A) " + questionsLists.get(current_id).getA());
                        prev_b.setText("B) " + questionsLists.get(current_id).getB());
                        prev_c.setText("C) " + questionsLists.get(current_id).getC());
                        prev_d.setText("D) " + questionsLists.get(current_id).getD());


                        //prev_explanation.setText(questionsLists.get(current_id).getExplanation());

                        String htmlExplanationString = questionsLists.get(current_id).getExplanation().replaceAll("\r\n", "<br>").replace("\n", "<br>");
                        prev_explanation.loadDataWithBaseURL(null, htmlExplanationString, "text/html", "UTF-8", "UTF-8");

                        correct_ans.setText(correct_answer);


                        initial(questionsLists, ans_aa);

                    }
                });

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<QuestionsList>> call, Throwable t) {
                Toasta("No Internet");
                progressDialog.dismiss();
            }
        });


    }


    public void initial(List<QuestionsList> questionsLists, ArrayList ans_aa) {

        correct_answer = "Correct ans :" + questionsLists.get(current_id).getAns().toUpperCase();

        correct_ans.setText(correct_answer);

        switch (questionsLists.get(current_id).getAns().toUpperCase()) {
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

        String your_ans = ans_aa.get(current_id).toString().toUpperCase();
        String your_ans_output;


        if (your_ans.equals("U")) {
            your_ans_output = "Not Attempted";
        } else {
            your_ans_output = "Your ans is : " + your_ans;
        }


        prev_answer.setText(your_ans_output);

    }


    public static long compareTo(java.util.Date date1, java.util.Date date2) {
        //returns negative value if date1 is before date2
        //returns 0 if dates are even
        //returns positive value if date1 is after date2
        return date1.getTime() - date2.getTime();
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
            case R.id.report:
                send_report();
                break;
            case R.id.doubt:
                ask_query();
                break;
        }
    }


    private void ask_query() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Preview.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.ask_doubt, null);
        final EditText doubt_editText = dialogLayout.findViewById(R.id.doubt_editText);
        Button doubt_btn = dialogLayout.findViewById(R.id.doubt_btn);
        builder.setView(dialogLayout);
        final AlertDialog alertDialog = builder.show();
        doubt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doubt_text = doubt_editText.getText().toString();
                if (TextUtils.isEmpty(doubt_text)) {
                    doubt_editText.setError("Field is Empty");
                    doubt_editText.requestFocus();
                    return;
                }
                raise_dought(qid_questionlist.get(current_id), doubt_text);
                alertDialog.dismiss();
            }
        });

    }


    private void send_report() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Preview.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.report, null);
        builder.setView(dialogLayout);

        ArrayList<String> report_list = new ArrayList<>();
        report_list.add("Key is Wrong");
        report_list.add("Question framing is inappropriate");
        report_list.add("Explanation is not clear");

        final Spinner spinner = dialogLayout.findViewById(R.id.reportSpinner);

        Button button = dialogLayout.findViewById(R.id.report_btn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Preview.this, android.R.layout.simple_dropdown_item_1line, report_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final AlertDialog alertDialog = builder.show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String report_string = spinner.getSelectedItem().toString();
                Log.e("report", report_string + "sid---" + sid + "qid--");
                //Toasta("report"+current_id+"--"+qid_questionlist.get(current_id).toString());

                send_report_server(qid_questionlist.get(current_id), report_string);
                alertDialog.dismiss();
            }
        });


    }


    private void raise_dought(String qid, String msg) {
        final ProgressDialog progressDialog = new ProgressDialog(Preview.this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.show();

        Call<RaiseDoubt> raiseDoubtCall = RetrofitClient.getmInstance().getApi().raise_doubt(sid, qid, msg);
        Log.e("request", sid + msg + qid);

        raiseDoubtCall.enqueue(new Callback<RaiseDoubt>() {
            @Override
            public void onResponse(Call<RaiseDoubt> call, Response<RaiseDoubt> response) {
                RaiseDoubt raiseDoubt = response.body();

                if (raiseDoubt != null) {
                    if (raiseDoubt.getSuccess()) {
                        Toasta("Doubt raised successfully");
                    } else {
                        Toasta(raiseDoubt.getMessage());
                    }
                } else {
                    Toasta("server not responding, try again later");
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RaiseDoubt> call, Throwable t) {
                Toasta("server not responding, try again later");
                progressDialog.dismiss();
            }
        });
    }


    private void send_report_server(String qid, String msg) {
        final ProgressDialog progressDialog = new ProgressDialog(Preview.this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.show();

        Call<ReportQuestion> reportQuestionCall = RetrofitClient.getmInstance().getApi().send_report_to_server(sid, qid, msg);
        Log.e("request", sid + msg + qid);
        reportQuestionCall.enqueue(new Callback<ReportQuestion>() {
            @Override
            public void onResponse(Call<ReportQuestion> call, Response<ReportQuestion> response) {
                ReportQuestion reportQuestion = response.body();

                if (reportQuestion != null) {
                    if (reportQuestion.getSuccess()) {
                        Toasta("Report successfully Sent");
                    } else {
                        Toasta("something wrong please try after sometime");
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ReportQuestion> call, Throwable t) {
                Toasta("something wrong please try after sometime");
                progressDialog.dismiss();
            }
        });

    }

    private void next() {


        int size_questions = aquestionsLists.size();


        //Toast(size_questions+"");

        Log.e("position", current_id + "");

        if (current_id != size_questions - 1) {
            current_id++;
            initial(aquestionsLists, ans_array);

            Log.e("size", size_questions + "");
            Log.e("Currenid", current_id + "");
        }


        if (current_id < size_questions) {
            prev_qid.setText(aquestionsLists.get(current_id).getId());
            // prev_question.setText(aquestionsLists.get(current_id).getQuestion());


            String htmlString = aquestionsLists.get(current_id).getQuestion().replaceAll("\r\n", "<br>").replace("\n", "<br>");
            prev_question.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", "UTF-8");

            prev_a.setText("A) " + aquestionsLists.get(current_id).getA());
            prev_b.setText("B) " + aquestionsLists.get(current_id).getB());
            prev_c.setText("C) " + aquestionsLists.get(current_id).getC());
            prev_d.setText("D) " + aquestionsLists.get(current_id).getD());


            //prev_explanation.setText(aquestionsLists.get(current_id).getExplanation());

            String htmlExplanationString = aquestionsLists.get(current_id).getExplanation().replaceAll("\r\n", "<br>").replace("\n", "<br>");
            prev_explanation.loadDataWithBaseURL(null, htmlExplanationString, "text/html", "UTF-8", "UTF-8");


            Log.e("position+", current_id + "");

        }
        if (current_id == aquestionsLists.size() - 1) {
            Toasta("limit completed");
        }

    }

    private void Toasta(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void previous() {


        Log.e("position", current_id + "");
        if (current_id != 0) {
            Log.e("position-", current_id + "");
            current_id--;
        }


        if (current_id >= 0) {
            initial(aquestionsLists, ans_array);
            prev_qid.setText(aquestionsLists.get(current_id).getId());
//            prev_question.setText(aquestionsLists.get(current_id).getQuestion());

            String htmlString = aquestionsLists.get(current_id).getQuestion().replaceAll("\r\n", "<br>").replace("\n", "<br>");
            prev_question.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", "UTF-8");


            prev_a.setText("A) " + aquestionsLists.get(current_id).getA());
            prev_b.setText("B) " + aquestionsLists.get(current_id).getB());
            prev_c.setText("C) " + aquestionsLists.get(current_id).getC());
            prev_d.setText("D) " + aquestionsLists.get(current_id).getD());

            String htmlExplanationString = aquestionsLists.get(current_id).getExplanation().replaceAll("\r\n", "<br>").replace("\n", "<br>");
            prev_explanation.loadDataWithBaseURL(null, htmlExplanationString, "text/html", "UTF-8", "UTF-8");


            //prev_explanation.setText(aquestionsLists.get(current_id).getExplanation());
            // correct_ans.setText(correct_answer);
            Log.e("position+", current_id + "");


        } else {
            Toasta("limit completed");
        }
    }
}
