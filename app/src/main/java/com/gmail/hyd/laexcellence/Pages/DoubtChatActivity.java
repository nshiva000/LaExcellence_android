package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.DoubtChatAdapter;
import com.gmail.hyd.laexcellence.Models.ReplyDoubt.ReplyDoubt;
import com.gmail.hyd.laexcellence.Models.UserSingleDoubts.Discussion;
import com.gmail.hyd.laexcellence.Models.UserSingleDoubts.UserSingleDoubts;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoubtChatActivity extends AppCompatActivity {


    String sid, did;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Discussion> datumArrayList = new ArrayList<>();

    TextView question, doubt;

    EditText doubt_msg;
    ImageButton doubt_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        question = findViewById(R.id.question);
        doubt = findViewById(R.id.your_doubt);


        doubt_msg = findViewById(R.id.doubt_msg);
        doubt_btn = findViewById(R.id.doubt_btn);

        Intent intent = getIntent();
        did = intent.getStringExtra("did");

        sid = SharedPrefManager.get_mInstance(getApplicationContext()).getSid();


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Doubt");

        }

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        get_discussion();


        doubt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doubt_msg_text = doubt_msg.getText().toString();
                sent_reply(doubt_msg_text);

            }
        });


    }

    private void sent_reply(String msg) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending..Please Wait");
        progressDialog.show();
        Call<ReplyDoubt> replyDoubtCall = RetrofitClient.getmInstance().getApi().reply_to_doubt(did, sid, msg);
        replyDoubtCall.enqueue(new Callback<ReplyDoubt>() {
            @Override
            public void onResponse(Call<ReplyDoubt> call, Response<ReplyDoubt> response) {
                progressDialog.dismiss();
                ReplyDoubt replyDoubt = response.body();
                if (replyDoubt != null) {
                    if (replyDoubt.getSuccess()) {
                        Toast(replyDoubt.getMessage());
                        doubt_msg.setText("");
                        get_discussion();
                    } else {
                        Toast(replyDoubt.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReplyDoubt> call, Throwable t) {
                progressDialog.dismiss();
                Toast("Server Not Working");

            }
        });
    }

    private void get_discussion() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..Please Wait");
        progressDialog.show();
        datumArrayList.clear();
        Call<UserSingleDoubts> userSingleDoubtsCall = RetrofitClient.getmInstance().getApi().user_single_doubts(did);
        userSingleDoubtsCall.enqueue(new Callback<UserSingleDoubts>() {
            @Override
            public void onResponse(Call<UserSingleDoubts> call, Response<UserSingleDoubts> response) {
                progressDialog.dismiss();
                UserSingleDoubts userSingleDoubts = response.body();
                if (userSingleDoubts != null) {
                    if (userSingleDoubts.getSuccess()) {
//                        userSingleDoubts.getDiscussion().get(2).setUserType("2");
//                        userSingleDoubts.getDiscussion().get(2).setDate("2");
//                        userSingleDoubts.getDiscussion().get(2).setRes("2");

                        question.setText(userSingleDoubts.getQuestion());
                        doubt.setText(userSingleDoubts.getDoubt());


                        datumArrayList.addAll(userSingleDoubts.getDiscussion());
                        adapter = new DoubtChatAdapter(getApplicationContext(), datumArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast(userSingleDoubts.getMessage());
                    }
                } else {
                    Toast("Server Not Responding, Please Try Again Later");
                }
            }

            @Override
            public void onFailure(Call<UserSingleDoubts> call, Throwable t) {
                progressDialog.dismiss();
                Toast("Server Not Responding, Please Try Again Later");
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
