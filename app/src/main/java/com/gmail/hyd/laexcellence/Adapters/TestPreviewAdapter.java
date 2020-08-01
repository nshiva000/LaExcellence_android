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
import com.gmail.hyd.laexcellence.R;

import java.util.List;

public class TestPreviewAdapter  extends RecyclerView.Adapter<TestPreviewAdapter.TestPreviewViewHolder> {


    private Context context;
    private List<String> stringList;
    CustomItemClickListener itemClickListener;

    public TestPreviewAdapter(Context context, List<String> stringList, CustomItemClickListener itemClickListener) {
        this.context = context;
        this.stringList = stringList;
        this.itemClickListener = itemClickListener;
    }

    public TestPreviewAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public TestPreviewViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qid_item,viewGroup,false);
        final TestPreviewAdapter.TestPreviewViewHolder mViewHolder = new TestPreviewAdapter.TestPreviewViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v,mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestPreviewViewHolder testPreviewViewHolder, int i) {
        testPreviewViewHolder.qid.setText(stringList.get(i));

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class TestPreviewViewHolder extends RecyclerView.ViewHolder{
        LinearLayout qid_card;
        TextView qid;
        CardView rid;
        public TestPreviewViewHolder(@NonNull View itemView) {
            super(itemView);
            qid = itemView.findViewById(R.id.qid);
            qid_card = itemView.findViewById(R.id.qid_card);
            rid = itemView.findViewById(R.id.rid);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}