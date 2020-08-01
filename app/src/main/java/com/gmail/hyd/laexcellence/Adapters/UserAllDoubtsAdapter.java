package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Pages.DoubtChatActivity;
import com.gmail.hyd.laexcellence.Models.UserAllDoubts.Datum;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

public class UserAllDoubtsAdapter extends RecyclerView.Adapter<UserAllDoubtsAdapter.UserAllDoubtsViewHolder> {

    private Context context;
    private List<Datum> datumList;

    public UserAllDoubtsAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public UserAllDoubtsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_all_doubts_item, viewGroup, false);
        return new UserAllDoubtsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAllDoubtsViewHolder userAllDoubtsViewHolder, int i) {

        final Datum datum = datumList.get(i);

        userAllDoubtsViewHolder.doubt.setText(datum.getProblem());
        userAllDoubtsViewHolder.doubt_date.setText(datum.getDate());
        userAllDoubtsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DoubtChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("did", datum.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class UserAllDoubtsViewHolder extends RecyclerView.ViewHolder {


        TextView doubt, doubt_date;
        CardView cardView;

        public UserAllDoubtsViewHolder(@NonNull View itemView) {
            super(itemView);

            doubt = itemView.findViewById(R.id.doubt);
            doubt_date = itemView.findViewById(R.id.doubt_date);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    private void Toast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
