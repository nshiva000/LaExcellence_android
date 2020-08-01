package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Pages.Exam;
import com.gmail.hyd.laexcellence.Models.ExamList.Datum;
import com.gmail.hyd.laexcellence.Pages.Preview;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.Helpers.Time;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Datum> list;

    public TestListAdapter(Context context, List<Datum> list) {
        this.context = context;
        this.list = list;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView exam_tile, exam_time, exam_status, test_attempted, rank_card;
        CardView exam_item, rank_green, rank_blue;
        ImageView completed;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            exam_tile = itemView.findViewById(R.id.test_title);
            exam_time = itemView.findViewById(R.id.test_date);
            exam_status = itemView.findViewById(R.id.test_status);
            exam_item = itemView.findViewById(R.id.test_item);
            completed = itemView.findViewById(R.id.image_completed);
            test_attempted = itemView.findViewById(R.id.test_attempted);
            rank_card = itemView.findViewById(R.id.test_rank);
            rank_green = itemView.findViewById(R.id.rank_green);
            rank_blue = itemView.findViewById(R.id.rank_blue);
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exam_list_item, viewGroup, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

        final Datum data = list.get(i);

        final String exam_id = data.getId();
        final String last_time = data.getEndTime();
        final String duration = data.getTime();
        final String start_time = data.getStartTime();


        recyclerViewHolder.exam_tile.setText(data.getTestTitle());


        Log.e("mmyanwers", data.getMyanswers().toString() + "sjhdbcjs");


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        final String strDate = sdf.format(c.getTime());

        if (!data.getTestAttempted()) {


            recyclerViewHolder.test_attempted.setText(data.getTestDate());

            if (Time.isExpire(data.getTestDate())) {
                Picasso.get().load(R.drawable.board2).into(recyclerViewHolder.completed);
                recyclerViewHolder.exam_status.setText("Today's Test");
                recyclerViewHolder.rank_blue.setVisibility(View.INVISIBLE);
                recyclerViewHolder.rank_green.setVisibility(View.INVISIBLE);

                boolean afterTime = false;
                try {
                    afterTime = Time.isAfterTime(last_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (afterTime) {
                    Picasso.get().load(R.drawable.board).into(recyclerViewHolder.completed);
                    recyclerViewHolder.exam_status.setText("Check the Answers");
                    recyclerViewHolder.exam_time.setText("Not Attempted");
                    recyclerViewHolder.rank_blue.setVisibility(View.INVISIBLE);
                    recyclerViewHolder.rank_green.setVisibility(View.INVISIBLE);
                }


            } else {

                recyclerViewHolder.exam_time.setText("Not Attempted");
                recyclerViewHolder.rank_green.setVisibility(View.INVISIBLE);

                Picasso.get().load(R.drawable.board).into(recyclerViewHolder.completed);
                recyclerViewHolder.exam_status.setText("Check the Answers");

            }


            // Log.e("completed",data.getTestAttempted()+"v");
        } else {
//            String str = data.getMarks();
//            String substr = ".";
//            String before = "0";
//
//            if (str.contains(".")){
//                before = str.substring(0, str.indexOf(substr));
//            }else {
//                before = str;
//            }

            recyclerViewHolder.exam_time.setText("Marks : " + data.getMarks());


            String str1 = (String) data.getRank().toString();
            String substr1 = ".";
            String before1 = "0";

            if (str1.contains(".")) {
                before1 = str1.substring(0, str1.indexOf(substr1));
            } else {
                before1 = str1;
            }

            boolean in = false;
            try {
                in = Time.isTimeBetweenTwoTime(data.getStartTime() + ":00", data.getEndTime() + ":00", strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (!in) {
                recyclerViewHolder.rank_card.setText("Rank : " + before1);
            } else {
                recyclerViewHolder.rank_green.setVisibility(View.INVISIBLE);
            }


            recyclerViewHolder.test_attempted.setText(data.getTestDate());
            Picasso.get().load(R.drawable.board).into(recyclerViewHolder.completed);
            recyclerViewHolder.exam_status.setText("Check the Answers");

        }


        recyclerViewHolder.exam_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!data.getTestAttempted()) {


                    if (Time.isExpire(data.getTestDate())) {

                        boolean i = false;
                        try {
                            i = Time.isTimeBetweenTwoTime(data.getStartTime() + ":00", data.getEndTime() + ":00", strDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Log.e("time", i + strDate + data.getStartTime() + ":00" + data.getEndTime() + ":00");


                        if (i) {
                            Intent intent = new Intent(context, Exam.class);
                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("examId", exam_id);
                            intent.putExtra("duration", duration);
                            intent.putExtra("start_time", start_time);
                            intent.putExtra("last_time", last_time);
                            context.startActivity(intent);
                        } else {


                            boolean afterTime = false;
                            try {
                                afterTime = Time.isAfterTime(last_time);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            if (afterTime) {
                                Intent intent = new Intent(context, Preview.class);
                                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("examId", exam_id);
                                intent.putExtra("marks", data.getMarks().toString());
                                intent.putExtra("rank", data.getRank().toString());
                                intent.putExtra("my_answers", data.getMyanswers().toString());
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "You can write the Test After " + data.getStartTime(), Toast.LENGTH_SHORT).show();
                            }


                        }

                    } else {

/*
                         Intent intent = new Intent(context, Exam.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("examId", exam_id);
                        intent.putExtra("last_time", last_time);
                        context.startActivity(intent);
*/

                        Intent intent = new Intent(context, Preview.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("examId", exam_id);
                        intent.putExtra("marks", data.getMarks().toString());
                        intent.putExtra("rank", data.getRank().toString());
                        intent.putExtra("my_answers", data.getMyanswers().toString());
                        context.startActivity(intent);


                    }

                } else {


                /*    Intent intent = new Intent(context, Exam.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("examId", exam_id);
                    intent.putExtra("duration", duration);
                    intent.putExtra("start_time", start_time);
                    intent.putExtra("last_time", last_time);
                    context.startActivity(intent);

                    */


                    boolean i = false;
                    try {
                        i = Time.isTimeBetweenTwoTime(data.getStartTime() + ":00", data.getEndTime() + ":00", strDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (i) {

                        Toast.makeText(context, "You can Review the Test After " + data.getEndTime(), Toast.LENGTH_SHORT).show();
                    } else {

                        Intent intent = new Intent(context, Preview.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("examId", exam_id);
                        intent.putExtra("marks", data.getMarks().toString());
                        intent.putExtra("rank", data.getRank().toString());
                        intent.putExtra("my_answers", data.getMyanswers().toString());
                        context.startActivity(intent);

                    }


                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
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
