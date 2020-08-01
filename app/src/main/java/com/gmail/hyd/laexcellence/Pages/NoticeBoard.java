package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.NoticeBoardModel.NoticeBoardModel;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeBoard extends AppCompatActivity {

    String sid;
    WebView webView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        sid = SharedPrefManager.get_mInstance(getApplicationContext()).getSid();


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Notice Board");

        }

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..Please Wait");
        progressDialog.show();

        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }
        });

        webView.loadUrl("https://laex.in/office/api/noticeboard/" + sid);


    }

    private void getNoticeboard() {
        Call<NoticeBoardModel> noticeBoardModelCall = RetrofitClient.getmInstance().getApi().get_noticeBoard(sid);

        noticeBoardModelCall.enqueue(new Callback<NoticeBoardModel>() {
            @Override
            public void onResponse(Call<NoticeBoardModel> call, Response<NoticeBoardModel> response) {
                NoticeBoardModel noticeBoardModel = response.body();

                if (noticeBoardModel != null) {
                    if (noticeBoardModel.getData() != null) {

                        String html_text = noticeBoardModel.getData().get(0).getMessage();


                    } else {
                        Toast_msg("Data is empty, try later");
                    }
                } else {
                    Toast_msg("server error, try later");
                }
            }

            @Override
            public void onFailure(Call<NoticeBoardModel> call, Throwable t) {

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
