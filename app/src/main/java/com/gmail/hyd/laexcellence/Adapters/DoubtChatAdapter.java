package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.hyd.laexcellence.Models.UserSingleDoubts.Discussion;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

public class DoubtChatAdapter extends RecyclerView.Adapter<DoubtChatAdapter.DoubtChatViewHolder> {

    private Context context;
    private List<Discussion> discussionList;

    public DoubtChatAdapter(Context context, List<Discussion> discussionList) {
        this.context = context;
        this.discussionList = discussionList;
    }

    @NonNull
    @Override
    public DoubtChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doubt_chat_item, viewGroup, false);
        return new DoubtChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoubtChatViewHolder doubtChatViewHolder, int i) {

        Discussion discussion = discussionList.get(i);


        if (discussion.getUserType().equals("1")) {
            doubtChatViewHolder.user_linearLayout.setVisibility(View.VISIBLE);
            doubtChatViewHolder.user_doubt.setText(discussion.getRes());
            doubtChatViewHolder.user_doubt_date.setText(discussion.getDate());
            doubtChatViewHolder.admin_linearLayout.setVisibility(View.GONE);
        } else {
            doubtChatViewHolder.admin_linearLayout.setVisibility(View.VISIBLE);
            doubtChatViewHolder.admin_doubt.setText(discussion.getRes());
            doubtChatViewHolder.admin_doubt_date.setText(discussion.getDate());
            doubtChatViewHolder.user_linearLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }

    public class DoubtChatViewHolder extends RecyclerView.ViewHolder {

        TextView user_doubt, user_doubt_date, admin_doubt, admin_doubt_date;
        LinearLayout user_linearLayout, admin_linearLayout;


        public DoubtChatViewHolder(@NonNull View itemView) {
            super(itemView);


            user_doubt = itemView.findViewById(R.id.user_doubt_msg);
            user_doubt_date = itemView.findViewById(R.id.user_doubt_date);
            user_linearLayout = itemView.findViewById(R.id.user_linearLayout);
            admin_linearLayout = itemView.findViewById(R.id.admin_linearLayout);

            admin_doubt = itemView.findViewById(R.id.admin_doubt_msg);
            admin_doubt_date = itemView.findViewById(R.id.admin_doubt_date);
        }
    }
}
