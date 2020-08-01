package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hyd.laexcellence.Models.TestAnalysisModel.Formwise;
import com.gmail.hyd.laexcellence.Models.TestAnalysisModel.Topicwise;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

public class FormAnalysisAdapter extends RecyclerView.Adapter<FormAnalysisAdapter.FormViewHolder> {

    private Context context;
    private List<Formwise> formwiseList;

    public FormAnalysisAdapter(Context context, List<Formwise> formwiseList) {
        this.context = context;
        this.formwiseList = formwiseList;
    }

    @NonNull
    @Override
    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.form_analysis_item,viewGroup,false);
        return new FormViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormViewHolder formViewHolder, int i) {
        Formwise formwise = formwiseList.get(i);

        formViewHolder.form_txt.setText(formwise.getFormName());
        formViewHolder.incorrect_txt.setText(formwise.getW().toString());
        formViewHolder.correct_txt.setText(formwise.getC().toString());
        formViewHolder.un_attempted_txt.setText(formwise.getU().toString());

    }

    @Override
    public int getItemCount() {
        Log.e("form size",formwiseList.size()+"");
        return formwiseList.size();
    }

    public class FormViewHolder extends RecyclerView.ViewHolder{
        TextView form_txt,no_questions_txt,correct_txt,incorrect_txt,un_attempted_txt;

        public FormViewHolder(@NonNull View itemView) {
            super(itemView);

            form_txt = itemView.findViewById(R.id.form_name);
            incorrect_txt = itemView.findViewById(R.id.form_in_correct);
            correct_txt = itemView.findViewById(R.id.form_correct);
            un_attempted_txt = itemView.findViewById(R.id.form_un_attempted);
        }
    }
}
