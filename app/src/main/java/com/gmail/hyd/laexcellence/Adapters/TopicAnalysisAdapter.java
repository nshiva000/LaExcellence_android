package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hyd.laexcellence.Models.TestAnalysisModel.Topicwise;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

public class TopicAnalysisAdapter extends RecyclerView.Adapter<TopicAnalysisAdapter.TopicViewHolder> {

    private Context context;
    private List<Topicwise> topicwiseList;

    public TopicAnalysisAdapter(Context context, List<Topicwise> topicwiseList) {
        this.context = context;
        this.topicwiseList = topicwiseList;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.topic_analysis_item,viewGroup,false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder topicViewHolder, int i) {
        Topicwise topicwise = topicwiseList.get(i);

        topicViewHolder.topic_txt.setText(topicwise.getTopicName());
        topicViewHolder.no_questions_txt.setText(topicwise.getU()+topicwise.getC()+topicwise.getW()+"");
        topicViewHolder.un_attempted_txt.setText(topicwise.getU().toString());
        topicViewHolder.correct_txt.setText(topicwise.getC().toString());
        topicViewHolder.incorrect_txt.setText(topicwise.getW().toString());

    }

    @Override
    public int getItemCount() {
        return topicwiseList.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder{

        TextView topic_txt,no_questions_txt,correct_txt,incorrect_txt,un_attempted_txt;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            topic_txt = itemView.findViewById(R.id.topic_name);
            no_questions_txt = itemView.findViewById(R.id.topic_no_questions);
            incorrect_txt = itemView.findViewById(R.id.topic_in_correct);
            correct_txt = itemView.findViewById(R.id.topic_correct);
            un_attempted_txt = itemView.findViewById(R.id.topic_un_attempted);
        }

    }
}
