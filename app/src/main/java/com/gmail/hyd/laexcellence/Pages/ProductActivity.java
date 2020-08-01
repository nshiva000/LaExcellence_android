package com.gmail.hyd.laexcellence.Pages;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Adapters.ProductAdapter;
import com.gmail.hyd.laexcellence.Models.Product.Datum;
import com.gmail.hyd.laexcellence.Models.Product.Product;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.RetrofitClient;
import com.gmail.hyd.laexcellence.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {


    ArrayList<Datum> arrayList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Products");

        }


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        get_product(SharedPrefManager.get_mInstance(getApplicationContext()).getSid());

    }


    private void get_product(String sid) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading,Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<Product> call = RetrofitClient.getmInstance().getApi().get_product(sid);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();


                if (product != null) {
                    if (product.getSuccess()) {
                        arrayList.addAll(product.getData());

                        adapter = new ProductAdapter(arrayList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    } else {
                        toast_msg("No products Available to display");
                    }
                } else {
                    toast_msg("Server not responding");
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
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
