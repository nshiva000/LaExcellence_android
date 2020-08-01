package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.Edit.Edit_response;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit extends AppCompatActivity {

    private EditText mobile_no, email;

    private String mobile, email_s, sid, email_text, mobile_text;
    private Button edit;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        sid = SharedPrefManager.get_mInstance(this).getSid();
        // email = SharedPrefManager.get_mInstance(this).getImage();
        mobile = SharedPrefManager.get_mInstance(this).getMobile();


        email = (EditText) findViewById(R.id.edit_email);
        edit = findViewById(R.id.edit_btn);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Edit.this);
                progressDialog.setMessage("Loading,Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();


                email_text = email.getText().toString();
                Log.e("edit", sid + email_text + mobile_text);


                editprofile();


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    private void editprofile() {

        Call<Edit_response> call = RetrofitClient.getmInstance().getApi().edit_profile(sid, email_text);

        Log.e("edit", sid + email_text + mobile_text);

        call.enqueue(new Callback<Edit_response>() {
            @Override
            public void onResponse(Call<Edit_response> call, Response<Edit_response> response) {
                Edit_response edit_response = response.body();
                progressDialog.dismiss();

                if (edit_response.getSuccess()) {
                    Toast.makeText(getApplicationContext(), "profile updated Successfully,please login again", Toast.LENGTH_SHORT).show();

                    updateUserData();


                } else {
                    Toast.makeText(getApplicationContext(), "Something wrong Please Try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Edit_response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void updateUserData() {

        SharedPrefManager.get_mInstance(getApplicationContext()).clear();
        Intent intent = new Intent(Edit.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


}
