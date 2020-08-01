package com.gmail.hyd.laexcellence.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Pages.Downloads;
import com.gmail.hyd.laexcellence.Pages.Handouts;
import com.gmail.hyd.laexcellence.Pages.NoticeBoard;
import com.gmail.hyd.laexcellence.Pages.NotificationActivity;
import com.gmail.hyd.laexcellence.Pages.QuestionBankActivity;
import com.gmail.hyd.laexcellence.R;
import com.gmail.hyd.laexcellence.Pages.Schedules;
import com.gmail.hyd.laexcellence.Pages.TestList;
import com.gmail.hyd.laexcellence.Pages.UserdoubtsActivity;
import com.squareup.picasso.Picasso;

public class WelcomeGridAdapter extends RecyclerView.Adapter<WelcomeGridAdapter.WelcomeGridViewHolder> {


    private Context context;
    private int images[];
    private String names[];

    public WelcomeGridAdapter(Context context, int[] images, String[] names) {
        this.context = context;
        this.images = images;
        this.names = names;
    }

    @NonNull
    @Override
    public WelcomeGridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_welcome_item, viewGroup, false);
        return new WelcomeGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeGridViewHolder welcomeGridViewHolder, int i) {

        final int names_string = i;
        welcomeGridViewHolder.textView.setText(names[i]);
        Picasso.get().load(images[i]).into(welcomeGridViewHolder.imageView);
        welcomeGridViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(names_string);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class WelcomeGridViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        TextView textView;
        CardView cardView;

        public WelcomeGridViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    private void Toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private void navigate(int i) {
        switch (i) {
            case 0:
                Intent test_list_intent = new Intent(context, TestList.class);
                test_list_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(test_list_intent);
                break;
            case 1:
                Toast("question Bank");
                Intent questionBank_intent = new Intent(context, QuestionBankActivity.class);
                questionBank_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(questionBank_intent);
                break;
            case 2:
                Intent handouts_intent = new Intent(context, Handouts.class);
                handouts_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(handouts_intent);
                break;
            case 3:
                Intent your_doubts_intent = new Intent(context, UserdoubtsActivity.class);
                your_doubts_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(your_doubts_intent);
                break;
            case 4:
                Intent notification_intent = new Intent(context, NotificationActivity.class);
                notification_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(notification_intent);
                break;
            case 5:
                Intent downloads_intent = new Intent(context, Downloads.class);
                downloads_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(downloads_intent);
                break;
            case 6:
                Intent schedule_intent = new Intent(context, Schedules.class);
                schedule_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(schedule_intent);
                break;
            case 7:
                Intent notice_board_intent = new Intent(context, NoticeBoard.class);
                notice_board_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(notice_board_intent);
                break;

        }
    }
}
