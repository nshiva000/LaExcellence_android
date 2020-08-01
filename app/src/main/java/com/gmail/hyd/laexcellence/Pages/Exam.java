package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.ExamAdapter;
import com.gmail.hyd.laexcellence.Interfaces.CustomItemClickListener;
import com.gmail.hyd.laexcellence.Models.QuestionsList.QuestionsList;
import com.gmail.hyd.laexcellence.Models.Submit.Submit;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Exam extends AppCompatActivity implements View.OnClickListener {


    private List<QuestionsList> questionsLists;

    private TextView questionId;
    private static TextView timer;
    private TextView ssss;
    private LinearLayout qid_card, lid_card;
    private WebView question;
    private int pos;
    private int total_size;

    private List<QuestionsList> questionsListLists;
    ArrayList<String> list_answers = new ArrayList<String>();
    Map<Integer, Integer> mark_list = new HashMap<>();
    CountDownTimer cTimer = null;


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<QuestionsList> arrayList = new ArrayList();

    Map<Integer, String> myMap = new HashMap<>();

    String sid;
    String exam_id, end_time, duration, start_time;

    private static CountDownTimer countDownTimer;


    RadioGroup radioGroup;
    RadioButton r_btn1, r_btn2, r_btn3, r_btn4, radioButton;
    Button submit, next, previous, mark, finish;

    private int current_id;

    private List<QuestionsList> q_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Test");

        }
        stopCountdown();


        recyclerView = findViewById(R.id.examid_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        question = findViewById(R.id.question);
        questionId = findViewById(R.id.questionId);
        timer = findViewById(R.id.timer);
        qid_card = findViewById(R.id.qid_card);
        lid_card = findViewById(R.id.lid);

        question.setWebViewClient(new WebViewClient());
        question.getSettings().setJavaScriptEnabled(true);
        question.getSettings().setBuiltInZoomControls(true);
        question.getSettings().setDisplayZoomControls(false);

        radioGroup = findViewById(R.id.r_group);
        r_btn1 = findViewById(R.id.r_btn1);
        r_btn2 = findViewById(R.id.r_btn2);
        r_btn3 = findViewById(R.id.r_btn3);
        r_btn4 = findViewById(R.id.r_btn4);

        submit = findViewById(R.id.btn_submit);
        next = findViewById(R.id.btn_next);
        previous = findViewById(R.id.btn_previous);
        mark = findViewById(R.id.mark);
        finish = findViewById(R.id.finish);

        submit.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        mark.setOnClickListener(this);
        finish.setOnClickListener(this);

        r_btn1.setOnClickListener(this);
        r_btn2.setOnClickListener(this);
        r_btn3.setOnClickListener(this);
        r_btn4.setOnClickListener(this);


        exam_id = getIntent().getStringExtra("examId");
        end_time = getIntent().getStringExtra("last_time");
        duration = getIntent().getStringExtra("duration");
        start_time = getIntent().getStringExtra("start_time");


        Log.e("duration", duration);


        sid = SharedPrefManager.get_mInstance(this).getSid();

        Log.e("exam_id", sid);

        get_all_questions(exam_id);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                mark.setBackground(getResources().getDrawable(R.drawable.mark));

                Q_next();
                break;
            case R.id.btn_previous:
                mark.setBackground(getResources().getDrawable(R.drawable.mark));

                Q_previous();
                break;
            case R.id.btn_submit:
                clear_ans(current_id);
                break;
            case R.id.mark:

                Toasta("mark");
                q_mark();
                break;
            case R.id.finish:
                exam_finish();
                Toasta("finish");
                break;
            case R.id.r_btn1:
                submit(current_id);
                break;
            case R.id.r_btn2:
                submit(current_id);
                break;
            case R.id.r_btn3:
                submit(current_id);
                break;
            case R.id.r_btn4:
                submit(current_id);
                break;


        }
    }


    private void clear_ans(final int c) {

        r_btn1.setChecked(false);
        r_btn2.setChecked(false);
        r_btn3.setChecked(false);
        r_btn4.setChecked(false);
        radioGroup.clearCheck();

        Log.e("submit", myMap.toString());
        myMap.remove(current_id);
        Log.e("b_submit", myMap.toString());


        recyclerView.scrollToPosition(c);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.findViewHolderForAdapterPosition(c) != null) {


                    recyclerView.findViewHolderForAdapterPosition(c).itemView.findViewById(R.id.lid).setBackgroundColor(getResources().getColor(R.color.white));
                    //recyclerView.findViewHolderForAdapterPosition(c).itemView.findViewById(R.id.qid).setBackgroundColor(getResources().getColor(R.color.white));

                }
            }
        }, 50);
    }


    private void submit(int c) {

        String ans = null;
        int selectedId = radioGroup.getCheckedRadioButtonId();

        switch (selectedId) {
            case R.id.r_btn1:
                ans = "a";
                submit_mark(c);
                break;
            case R.id.r_btn2:
                submit_mark(c);
                ans = "b";
                break;
            case R.id.r_btn3:
                submit_mark(c);
                ans = "c";
                break;
            case R.id.r_btn4:
                submit_mark(c);
                ans = "d";
                break;
            default:
                ans = "u";
                break;


        }


        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        Toasta("Question :" + (current_id + 1) + " ans :" + ans + " submitted");

        myMap.put(current_id, ans);

    }


    private void submit_mark(final int c) {

        recyclerView.scrollToPosition(c);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.findViewHolderForAdapterPosition(c) != null) {


                    recyclerView.findViewHolderForAdapterPosition(c).itemView.findViewById(R.id.lid).setBackgroundColor(getResources().getColor(R.color.green));
                    //recyclerView.findViewHolderForAdapterPosition(c).itemView.findViewById(R.id.qid).setBackgroundColor(getResources().getColor(R.color.white));

                }
            }
        }, 50);


    }


    private void set_radio() {
        String ans = "u";

        Log.e("position", current_id + "");
        r_btn1.setChecked(false);
        r_btn2.setChecked(false);
        r_btn3.setChecked(false);
        r_btn4.setChecked(false);
        radioGroup.clearCheck();


        if (myMap.containsKey(current_id)) {
            Log.e("ansscheck", myMap.get(current_id));
            check_radio(myMap.get(current_id));
        }


    }


    private void check_radio(String s) {

        Log.e("check radio", s);

        switch (s) {
            case "a":
                r_btn1.setChecked(true);
                break;
            case "b":
                r_btn2.setChecked(true);
                break;
            case "c":
                r_btn3.setChecked(true);
                break;
            case "d":
                r_btn4.setChecked(true);
                break;

        }

    }


    private void get_all_questions(String s) {

        final ProgressDialog progressDialog = new ProgressDialog(Exam.this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<List<QuestionsList>> call = RetrofitClient.getmInstance().getApi().get_all_questions(exam_id);

        call.enqueue(new Callback<List<QuestionsList>>() {
            @Override
            public void onResponse(Call<List<QuestionsList>> call, Response<List<QuestionsList>> response) {

                q_list = response.body();

                total_size = q_list.size();

                Log.e("total size", total_size + "");

                int id = 1;
                for (int i = 0; i < q_list.size(); i++) {
                    q_list.get(i).setId(id + "");
                    id++;
                }
                progressDialog.dismiss();

                if (q_list != null) {


                    adapter = new ExamAdapter(getApplicationContext(), q_list, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Log.d("recycler", "clicked position:" + position);
                            current_id = position;


                            r_btn1.setChecked(false);
                            r_btn2.setChecked(false);
                            r_btn3.setChecked(false);
                            r_btn4.setChecked(false);
                            radioGroup.clearCheck();


                            // question.setText(q_list.get(position).getQuestion());


                            String htmlString = q_list.get(position).getQuestion().replaceAll("\r\n", "<br>").replace("\n", "<br>");
                            question.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", "UTF-8");

                            questionId.setText(q_list.get(position).getId());
                            r_btn1.setText(q_list.get(position).getA());
                            r_btn2.setText(q_list.get(position).getB());
                            r_btn3.setText(q_list.get(position).getC());
                            r_btn4.setText(q_list.get(position).getD());


                            TextView ttd = (TextView) v.findViewById(R.id.qid);

                            set_radio();
                            q_mark_icon(current_id);
                        }
                    });

                    recyclerView.setAdapter(adapter);


                    //question.setText(q_list.get(0).getQuestion());

                    String htmlString = q_list.get(0).getQuestion().replaceAll("\r\n", "<br>").replace("\n", "<br>");
                    question.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", "UTF-8");

                    questionId.setText(q_list.get(0).getId());
                    r_btn1.setText(q_list.get(0).getA());
                    r_btn2.setText(q_list.get(0).getB());
                    r_btn3.setText(q_list.get(0).getC());
                    r_btn4.setText(q_list.get(0).getD());

                    // startTimer(30);

                    try {
                        getTimeDifference(end_time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<List<QuestionsList>> call, Throwable t) {
                progressDialog.dismiss();
                Toasta("No Internet");
            }
        });
    }


    private void q_mark() {
        recyclerView.scrollToPosition(current_id);


        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.findViewHolderForAdapterPosition(current_id) != null) {

                    if (!mark_list.containsKey(current_id)) {

                        mark_list.put(current_id, current_id);
                        Log.e("mark_list", mark_list.toString());
                        mark.setBackground(getResources().getDrawable(R.drawable.unmark));
                        recyclerView.findViewHolderForAdapterPosition(current_id).itemView.findViewById(R.id.qid_card).setBackgroundColor(getResources().getColor(R.color.mark));

                    } else {
                        mark_list.remove(current_id);
                        Log.e("mark_list", mark_list.toString());
                        mark.setBackground(getResources().getDrawable(R.drawable.mark));
                        recyclerView.findViewHolderForAdapterPosition(current_id).itemView.findViewById(R.id.qid_card).setBackgroundColor(getResources().getColor(R.color.white));

                    }

                }
            }
        }, 50);

        // recyclerView.findViewHolderForAdapterPosition(current_id).itemView.findViewById(R.id.qid_card).setBackgroundColor(Color.YELLOW);


    }

    private void q_mark_icon(int c) {
        if (mark_list.containsKey(current_id)) {
            mark.setBackground(getResources().getDrawable(R.drawable.unmark));
            Log.e("markPositioncontains", "contains");
        } else {
            mark.setBackground(getResources().getDrawable(R.drawable.mark));
            Log.e("markPositioncontains", "contains");
        }
        Log.e("mark", mark_list.toString());
        Log.e("markPosition", current_id + "");
    }


    private String create_ans_string() {

        for (int i = 0; i < total_size; i++) {
            String map_ans = myMap.get(i);

            if (map_ans != null) {
                list_answers.add(map_ans);
                Log.e("mapsize", map_ans + i);
            } else {
                list_answers.add("u");

            }
        }


        final StringBuilder sb = new StringBuilder();
        for (int s = 0; s < total_size; s++) {
            sb.append(list_answers.get(s));
            sb.append(",");
        }


        return sb.toString();
    }


    private void exam_direct_finish() {


        String sb = create_ans_string();


        Call<Submit> submitCall = RetrofitClient.getmInstance().getApi().submit_answers(sid, exam_id, sb);


        submitCall.enqueue(new Callback<Submit>() {
            @Override
            public void onResponse(Call<Submit> call, Response<Submit> response) {
                Submit submitres = response.body();

                Toasta(submitres.getMessage());

                Intent intent = new Intent(Exam.this, TestList.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Submit> call, Throwable t) {

            }
        });


    }

    private void exam_finish() {
        Log.e("mapsize", myMap.toString());


        final String sb = create_ans_string();


        new AlertDialog.Builder(this)
                .setTitle("Submit The Test")
                .setMessage("Do you want to Submit the Test")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.e("comma", sb.toString());

                        Call<Submit> submitCall = RetrofitClient.getmInstance().getApi().submit_answers(sid, exam_id, sb);


                        submitCall.enqueue(new Callback<Submit>() {
                            @Override
                            public void onResponse(Call<Submit> call, Response<Submit> response) {
                                Submit submitres = response.body();
                                cTimer.cancel();

                                Toasta(submitres.getMessage());
                                Intent intent = new Intent(Exam.this, TestList.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Submit> call, Throwable t) {
                                Toasta("Not submitted, please try again");
                            }
                        });


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list_answers.clear();
                    }
                }).show();


    }


    private void Toasta(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void Q_next() {
        

        if (current_id != q_list.size() - 1) {
            //submit(current_id);
            current_id++;
            set_radio();
            q_mark_icon(current_id);

            //Log.e("size",q_list.size()+"");
            //Log.e("Currenid",current_id+"");
        }

        if (current_id == q_list.size() - 1) {
            //submit(current_id);
            set_radio();
            q_mark_icon(current_id);
        }

        Log.e("size", q_list.size() + "");
        Log.e("Currenid", current_id + "");


        if (current_id < q_list.size()) {


            //question.setText(q_list.get(current_id).getQuestion());

            String htmlString = q_list.get(current_id).getQuestion().replaceAll("\r\n", "<br>").replace("\n", "<br>");
            question.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", "UTF-8");

            questionId.setText(q_list.get(current_id).getId());
            r_btn1.setText(q_list.get(current_id).getA());
            r_btn2.setText(q_list.get(current_id).getB());
            r_btn3.setText(q_list.get(current_id).getC());
            r_btn4.setText(q_list.get(current_id).getD());
            //Log.e("position+", current_id + "");

        }
        if (current_id == q_list.size() - 1) {
            Toasta("limit completed");
        }


    }

    private void Q_previous() {


        //Log.e("position",current_id+"");
        if (current_id != 0) {
            //submit(current_id);

            //Log.e("position-", current_id + "");
            current_id--;
        }


        if (current_id >= 0) {
            set_radio();
            q_mark_icon(current_id);

            // question.setText(q_list.get(current_id).getQuestion());

            String htmlString = q_list.get(current_id).getQuestion().replaceAll("\r\n", "<br>").replace("\n", "<br>");
            question.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", "UTF-8");


            questionId.setText(q_list.get(current_id).getId());
            r_btn1.setText(q_list.get(current_id).getA());
            r_btn2.setText(q_list.get(current_id).getB());
            r_btn3.setText(q_list.get(current_id).getC());
            r_btn4.setText(q_list.get(current_id).getD());
            //Log.e("position+", current_id + "");

        } else {
            Toasta("limit completed");
        }

    }


    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


    //Start Countodwn method
    private void startTimer(int noOfMinutes) {
        cTimer = new CountDownTimer(noOfMinutes * 60 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timer.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                timer.setText("Completed");
                exam_direct_finish();
            }
        }.start();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            cTimer.cancel();

        finish();
        return super.onOptionsItemSelected(item);
    }


    public void getTimeDifference(String last) throws ParseException {

        Date date = new Date();
        String strDateFormat = "HH:mm";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String current = dateFormat.format(date);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date startDate = simpleDateFormat.parse(current);
        Date endDate = simpleDateFormat.parse(last);

        Log.i("time", current + last);

        long difference = endDate.getTime() - startDate.getTime();
        if (difference < 0) {
            Date dateMax = simpleDateFormat.parse("24:00");
            Date dateMin = simpleDateFormat.parse("00:00");
            difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
        }
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

        int t_min = min + (hours * 60);

        int myNum = Integer.parseInt(duration);


        if (t_min >= myNum) {
            startTimer(myNum);
            Log.e("time2", "minutes" + min + "hours" + hours);
            Log.e("time", min + "ss" + t_min + "ss" + myNum);

        } else {
            Log.e("timezzz", "minutes:" + min + "hours" + hours);
            startTimer(t_min);


        }


    }


    @Override
    public void onBackPressed() {
        cTimer.cancel();
        finish();
    }
}

