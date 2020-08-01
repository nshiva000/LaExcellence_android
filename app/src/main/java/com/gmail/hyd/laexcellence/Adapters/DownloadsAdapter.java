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
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hyd.laexcellence.Models.DownloadsModel.Datum;
import com.gmail.hyd.laexcellence.R;

import java.util.List;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.DownloadViewHolder> {


    private List<Datum> datumList;
    private Context context;
    private String path;

    public DownloadsAdapter(List<Datum> datumList, Context context) {
        this.datumList = datumList;
        this.context = context;
    }

    @NonNull
    @Override
    public DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.downloads_item, viewGroup, false);
        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadViewHolder downloadViewHolder, int i) {

        Datum datum = datumList.get(i);
        int s_no = datum.getId();
        String sno_text = s_no + ". ";

        //downloadViewHolder.Sno.setText(sno_text);
        downloadViewHolder.filename.setText(datum.getTitle());
        path = datum.getPath();

        downloadViewHolder.viewDoc.setOnClickListener(new View.OnClickListener() {
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

        downloadViewHolder.downloadDoc.setOnClickListener(new View.OnClickListener() {
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


    public class DownloadViewHolder extends RecyclerView.ViewHolder {

        CardView viewDoc, downloadDoc;
        TextView filename;

        public DownloadViewHolder(@NonNull View itemView) {
            super(itemView);

            viewDoc = itemView.findViewById(R.id.viewDoc);
            downloadDoc = itemView.findViewById(R.id.downloadDoc);
            filename = itemView.findViewById(R.id.file_name);
        }
    }
}
