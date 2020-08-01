package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.Signup.Signup;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText username, mobile, email, password;
    String username_txt, mobile_txt, email_txt, password_txt;
    Button signup_btn;
    TextView login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.username);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signup_btn = findViewById(R.id.register_btn);
        login_btn = findViewById(R.id.login_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username_txt = username.getText().toString().trim();
                mobile_txt = mobile.getText().toString().trim();
                email_txt = email.getText().toString().trim();
                password_txt = password.getText().toString().trim();


                if (TextUtils.isEmpty(username_txt)) {
                    username.setError("Please enter username");
                    username.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email_txt)) {
                    email.setError("Please enter username");
                    email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(mobile_txt)) {
                    mobile.setError("Please enter username");
                    mobile.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(password_txt)) {
                    password.setError("Please enter username");
                    password.requestFocus();
                    return;
                }


                user_register();

            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void user_register() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<Signup> signupCall = RetrofitClient.getmInstance().getApi().user_register(username_txt, email_txt, mobile_txt, password_txt);

        signupCall.enqueue(new Callback<Signup>() {
            @Override
            public void onResponse(Call<Signup> call, Response<Signup> response) {
                Signup signup = response.body();

                if (signup != null) {
                    toast_msg("Registered successfully, please Login");
                    finish();
                } else {
                    toast_msg("server not responding ,Please try again");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Signup> call, Throwable t) {
                toast_msg("server not responding ,Please try again");
                progressDialog.dismiss();
            }
        });
    }


    private void toast_msg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
