package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.hyd.laexcellence.Models.Product.Datum;
import com.gmail.hyd.laexcellence.Pages.PaymentActivity;
import com.gmail.hyd.laexcellence.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Datum> datumList;
    private Context context;

    public ProductAdapter(List<Datum> datumList, Context context) {
        this.datumList = datumList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {

        final Datum datum = datumList.get(i);

        productViewHolder.description.setText(datum.getDescription());
        productViewHolder.price.setText(datum.getAmount() + " /-");
        Picasso.get().load(datum.getPic()).into(productViewHolder.product_img);

        if (!datum.getBaught()) {
            productViewHolder.buy_btn.setVisibility(View.GONE);
            productViewHolder.already.setVisibility(View.VISIBLE);
        }

        productViewHolder.buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("des", datum.getDescription());
                intent.putExtra("price", datum.getAmount());
                intent.putExtra("img", datum.getPic());
                intent.putExtra("pid", datum.getProductId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView price, description;
        ImageView product_img;
        Button coupon_btn, buy_btn;
        EditText coupon_code;
        LinearLayout already;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.amount);
            description = itemView.findViewById(R.id.des);
            product_img = itemView.findViewById(R.id.product_img);
            buy_btn = itemView.findViewById(R.id.buy_now);
            already = itemView.findViewById(R.id.already);
        }
    }
}
