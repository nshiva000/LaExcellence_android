package com.gmail.hyd.laexcellence.Pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.ForgotPassword.ForgotPassword;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {

    EditText email;
    String email_txt;
    Button forgot_btn;
    TextView login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        email = findViewById(R.id.email);

        forgot_btn = findViewById(R.id.forgot_btn);
        login_btn = findViewById(R.id.login_btn);

        forgot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_txt = email.getText().toString().trim();

                if (TextUtils.isEmpty(email_txt)) {
                    email.setError("Please enter Email Address");
                    email.requestFocus();
                    return;
                }
                change_password(email_txt);

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void change_password(String email) {
        Call<ForgotPassword> forgotPasswordCall = RetrofitClient.getmInstance().getApi().forgot_password(email);

        forgotPasswordCall.enqueue(new Callback<ForgotPassword>() {
            @Override
            public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                ForgotPassword forgotPassword = response.body();

                if (forgotPassword != null) {
                    if (forgotPassword.getSuccess()) {
                        toast_msg(forgotPassword.getMessage());
                        finish();
                    } else {
                        toast_msg(forgotPassword.getMessage());
                    }

                } else {
                    toast_msg("server not responding please try again");
                }
            }

            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                toast_msg("server not responding please try again");
            }
        });
    }


    private void toast_msg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
