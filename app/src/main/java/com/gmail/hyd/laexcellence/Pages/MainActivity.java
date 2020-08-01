package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.Login.Login;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText userName, password;
    Button login_btn;
    String Str_username, Str_password;

    TextView forgot_view, signup_btn;


    @Override
    protected void onStart() {
        super.onStart();


        if (SharedPrefManager.get_mInstance(this).isLoggedIn()) {
            boolean isLoggedIn = SharedPrefManager.get_mInstance(getApplicationContext()).isLoggedIn();

            if (isLoggedIn) {
                Toast("You are LoggedIn");
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }


            //login_call(userName,pass);


//            Intent intent = new Intent(MainActivity.this,TestList.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Call the function callInstamojo to start payment here

        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);

        forgot_view = findViewById(R.id.forgot_textView);


        forgot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Str_username = userName.getText().toString().trim();
                Str_password = password.getText().toString().trim();


                if (TextUtils.isEmpty(Str_username)) {
                    userName.setError("Please enter username");
                    userName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Str_password)) {
                    password.setError("Please enter Password");
                    password.requestFocus();
                    return;
                }

                Log.e("username", Str_password + Str_username);
                login_call(Str_username, Str_password);


            }
        });
    }


    private void login_call(String username, String password) {


        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Logging In,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Call<Login> call = RetrofitClient.getmInstance().getApi().user_login(username, password);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login result = response.body();

                if (result.getSuccess()) {
                    Log.e("success", result.getData().getName());


                    SharedPrefManager.get_mInstance(MainActivity.this)
                            .saveUser(result);

                    progressDialog.dismiss();


                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    progressDialog.dismiss();
                }
                Toast(result.getMessage());


            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast("No Internet");
                progressDialog.dismiss();
                Log.e("error", t.getMessage());
            }
        });

    }


    private void Toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
