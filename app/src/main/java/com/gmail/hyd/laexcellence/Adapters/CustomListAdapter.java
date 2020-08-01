package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gmail.hyd.laexcellence.Models.QuestionsList.QuestionsList;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<QuestionsList> {

    private Context context;
    private List<QuestionsList> questionsListLists;

    public CustomListAdapter(Context context, List<QuestionsList> questionsListLists) {
        super(context, R.layout.qid_item, questionsListLists);
        this.context = context;
        this.questionsListLists = questionsListLists;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.qid_item, parent, false);

        QuestionsList questionsList = questionsListLists.get(position);
        TextView textView = convertView.findViewById(R.id.qid);
        textView.setText(questionsList.getId());
        return convertView;
    }


}
