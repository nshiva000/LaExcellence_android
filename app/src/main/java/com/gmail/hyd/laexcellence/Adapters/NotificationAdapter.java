package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hyd.laexcellence.Models.Notification.Datum;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Datum> datumList;
    private Context context;

    public NotificationAdapter(List<Datum> datumList, Context context) {
        this.datumList = datumList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item, viewGroup, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        Datum datum = datumList.get(i);

        notificationViewHolder.title.setText(datum.getTitle());
        notificationViewHolder.des.setText(datum.getMessage());
        notificationViewHolder.date.setText(datum.getDate());
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView title, des, date;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            des = itemView.findViewById(R.id.des);
            date = itemView.findViewById(R.id.date);
        }
    }
}
