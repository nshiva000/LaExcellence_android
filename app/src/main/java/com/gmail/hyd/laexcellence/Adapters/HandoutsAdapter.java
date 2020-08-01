package com.gmail.hyd.laexcellence.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.HandoutsModel.Datum;
import com.gmail.hyd.laexcellence.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HandoutsAdapter extends RecyclerView.Adapter<HandoutsAdapter.HandoutsViewHolder> {

    private List<Datum> datumList;
    private Context context;
    private String path;

    public HandoutsAdapter(List<Datum> datumList, Context context) {
        this.datumList = datumList;
        this.context = context;
    }

    @NonNull
    @Override
    public HandoutsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.downloads_item, viewGroup, false);
        return new HandoutsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HandoutsViewHolder handoutsViewHolder, int i) {

        Datum datum = datumList.get(i);
        int s_no = datum.getId();
        String sno_text = s_no + ". ";

        // handoutsViewHolder.Sno.setText(sno_text);
        handoutsViewHolder.filename.setText(datum.getTitle());
        path = datum.getPath();
        Picasso.get().load(R.drawable.file).into(handoutsViewHolder.download_item);

        handoutsViewHolder.viewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);

               /*
                 DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                 Uri uri = Uri.parse(url);
                 DownloadManager.Request request = new DownloadManager.Request(uri);
                 request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                 Long ref = downloadManager.enqueue(request);
                 Toast.makeText(context.getApplicationContext(),
                         "Your file is now downloading...", Toast.LENGTH_LONG).show();

                         )*/

            }
        });

        handoutsViewHolder.downloadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(path);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long ref = downloadManager.enqueue(request);
                Toast.makeText(context.getApplicationContext(),
                        "Your file is now downloading...", Toast.LENGTH_LONG).show();

            }
        });

        s_no++;

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class HandoutsViewHolder extends RecyclerView.ViewHolder {

        CardView viewDoc, downloadDoc;
        TextView filename;
        ImageView download_item;

        public HandoutsViewHolder(@NonNull View itemView) {
            super(itemView);


            viewDoc = itemView.findViewById(R.id.viewDoc);
            downloadDoc = itemView.findViewById(R.id.downloadDoc);
            // Sno = itemView.findViewById(R.id.sid);
            filename = itemView.findViewById(R.id.file_name);
            download_item = itemView.findViewById(R.id.download_item);
        }
    }
}
