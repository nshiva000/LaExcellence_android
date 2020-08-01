package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.TestListModel.Datum;
import com.gmail.hyd.laexcellence.Pages.AnalysisActivity;
import com.gmail.hyd.laexcellence.Pages.TestPreviewActivity;
import com.gmail.hyd.laexcellence.Pages.TestWriteActivity;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class QuestionBankAdapter extends RecyclerView.Adapter<QuestionBankAdapter.QuestionBankViewHolder> {

    private Context context;
    private List<Datum> datumList;

    public QuestionBankAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public QuestionBankViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_bank_list_item,viewGroup,false);
        return new QuestionBankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionBankViewHolder questionBankViewHolder, int i) {

        final Datum datum = datumList.get(i);

        questionBankViewHolder.title.setText("#1000"+datum.getId());

        if (datum.getTestStatus().equals("2")){
            questionBankViewHolder.result.setVisibility(View.VISIBLE);
            questionBankViewHolder.analysis.setVisibility(View.VISIBLE);
            questionBankViewHolder.resume.setVisibility(View.GONE);
        }else {
            Toast.makeText(context,"Write Test",Toast.LENGTH_SHORT).show();
            questionBankViewHolder.result.setVisibility(View.GONE);
            questionBankViewHolder.analysis.setVisibility(View.GONE);
            questionBankViewHolder.resume.setVisibility(View.VISIBLE);
        }


        questionBankViewHolder.resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"resume",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TestWriteActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tid",datum.getId());
                intent.putExtra("time_mode",datum.getTimedMode());
                intent.putExtra("tutor_mode",datum.getTutorMode());
                intent.putExtra("duration", "10");
                context.startActivity(intent);
            }
        });

        questionBankViewHolder.result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Result",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TestPreviewActivity.class);
                intent.putExtra("tid",datum.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        questionBankViewHolder.analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Analysis",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AnalysisActivity.class);
                intent.putExtra("tid",datum.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    class QuestionBankViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,tutor_mode,no_questions;
        Button result,resume,analysis;

        public QuestionBankViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.test_title);
            date = itemView.findViewById(R.id.test_date);
            tutor_mode = itemView.findViewById(R.id.tutor_mode);
            no_questions = itemView.findViewById(R.id.no_questions);
            result = itemView.findViewById(R.id.test_results);
            resume = itemView.findViewById(R.id.test_resume);
            analysis = itemView.findViewById(R.id.test_analysis);

        }
    }
}
