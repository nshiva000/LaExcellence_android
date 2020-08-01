package com.gmail.hyd.laexcellence.Pages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.Coupon.Coupon;
import com.gmail.hyd.laexcellence.Models.InstaMojoModel.InstaMojoModel;
import com.gmail.hyd.laexcellence.Models.PayNow.PayNow;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentActivity extends AppCompatActivity {

    EditText coupon;
    Button payNow, coupon_btn;
    String email_txt, amount_txt, purpose_txt, phone_txt, name_txt, sid_txt, pid_txt;


    TextView des, amount;

    String des_txt, price_txt, img_txt, coupen_txt;
    ImageView img;

    LinearLayout appied_coupon, linear_coupen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Payment");

        }


        appied_coupon = findViewById(R.id.applied_coupon);
        linear_coupen = findViewById(R.id.linear_coupon);


        des = findViewById(R.id.des);
        amount = findViewById(R.id.amount);

        img = findViewById(R.id.product_img);


        coupon = findViewById(R.id.coupon);
        coupon_btn = findViewById(R.id.coupon_apply);
        payNow = findViewById(R.id.buy_now);


        Intent intent = getIntent();
        des_txt = intent.getStringExtra("des");
        price_txt = intent.getStringExtra("price");
        img_txt = intent.getStringExtra("img");
        pid_txt = intent.getStringExtra("pid");


        des.setText(des_txt);
        amount.setText(price_txt);
        Picasso.get().load(img_txt).into(img);

        coupon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coupen_txt = coupon.getText().toString().trim();

                apply_coupon(sid_txt, pid_txt, coupen_txt);
                Log.e("coupon", coupen_txt);
            }
        });

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sid_txt = SharedPrefManager.get_mInstance(getApplicationContext()).getSid();
                email_txt = SharedPrefManager.get_mInstance(getApplicationContext()).getEmail();
                phone_txt = SharedPrefManager.get_mInstance(getApplicationContext()).getMobile();
                purpose_txt = des_txt;
                amount_txt = price_txt;
                name_txt = SharedPrefManager.get_mInstance(getApplicationContext()).getName();
                Log.e("success_send", "email_txt " + email_txt + "-phone_txt-" + phone_txt + "-amount_txt-" + amount_txt + "-purpose_txt-" + purpose_txt + "-name_txt-" + name_txt);
                callInstamojoPay(email_txt, phone_txt, amount_txt, purpose_txt, name_txt);
            }
        });


    }

    private void apply_coupon(String sid, String pid, String coupon_txt) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<Coupon> couponCall = RetrofitClient.getmInstance().getApi().apply_coupon(sid, pid, coupon_txt);

        couponCall.enqueue(new Callback<Coupon>() {
            @Override
            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                Coupon coupon = response.body();
                if (coupon != null) {
                    if (coupon.getSuccess()) {


                        appied_coupon.setVisibility(View.VISIBLE);
                        linear_coupen.setVisibility(View.GONE);


                        amount.setText(coupon.getFinalAmount().toString());
                        price_txt = coupon.getFinalAmount().toString();
                    } else {

                        toast_msg("Coupon Expired or Coupon Not Available");
                    }
                } else {
                    toast_msg("server is not responding");
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Coupon> call, Throwable t) {
                toast_msg("server is not responding");
                progressDialog.dismiss();
            }
        });
    }


    private void send_success_payment(String order_id, final String payment_id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Call<PayNow> payNowCall = RetrofitClient.getmInstance().getApi().pay_money(sid_txt, order_id, payment_id, coupen_txt);

        payNowCall.enqueue(new Callback<PayNow>() {
            @Override
            public void onResponse(Call<PayNow> call, Response<PayNow> response) {
                PayNow payNow = response.body();

                if (payNow != null) {
                    if (payNow.getSuccess()) {
                        Log.e("success", payNow.getMessage() + "  Order Booked Successfully");
                        toast_msg("Order Booked Successfully");
                        finish();
                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PayNow> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {


                Log.e("success", response + "");

                if (response != null) {
                    InstaMojoModel instaMojoModel = onInstaSuccess(response);

                    Log.e("success", "order id" + instaMojoModel.getOrderId() + " payment_id" + instaMojoModel.getPaymentId());


                    send_success_payment(instaMojoModel.getOrderId(), instaMojoModel.getPaymentId());
                }


            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
                Log.e("success", reason + "--" + code);
            }
        };
    }


    public InstaMojoModel onInstaSuccess(String strResponse) {
        String strWithoutColon = strResponse.replace(":", "\",\"");
        String strWithoutEquals = strWithoutColon.replace("=", "\":\"");
        String perfectJSON = "{\"" + strWithoutEquals + "\"}";
        Log.i("Perfect", perfectJSON);
        // COnvert the json to POJO
        InstaMojoModel instaMojoModel = new Gson().fromJson(perfectJSON, InstaMojoModel.class);
        return instaMojoModel;
    }


    private void toast_msg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

}
