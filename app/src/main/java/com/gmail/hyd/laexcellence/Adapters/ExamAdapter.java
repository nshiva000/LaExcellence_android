package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gmail.hyd.laexcellence.Interfaces.CustomItemClickListener;
import com.gmail.hyd.laexcellence.Models.QuestionsList.QuestionsList;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamRecyclerViewHolderr> {

    private Context context;
    private List<QuestionsList> list;
    CustomItemClickListener listener;

    public ExamAdapter(Context context, List<QuestionsList> list, CustomItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExamRecyclerViewHolderr onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qid_item, viewGroup, false);

        final ExamRecyclerViewHolderr mViewHolder = new ExamRecyclerViewHolderr(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamRecyclerViewHolderr examRecyclerViewHolderr, int i) {

        QuestionsList questionsList = list.get(i);
        examRecyclerViewHolderr.qid.setText(questionsList.getId());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ExamRecyclerViewHolderr extends RecyclerView.ViewHolder {

        LinearLayout qid_card;
        TextView qid;
        CardView rid;

        public ExamRecyclerViewHolderr(@NonNull View itemView) {
            super(itemView);

            qid = itemView.findViewById(R.id.qid);
            qid_card = itemView.findViewById(R.id.qid_card);
            rid = itemView.findViewById(R.id.rid);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
