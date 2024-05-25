package com.emre.filedownloadmanagerconverter.LinkDm;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emre.filedownloadmanagerconverter.R;

import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    List<DownloadModel> downloadModels = new ArrayList<>();

    public DownloadAdapter(Context context,List<DownloadModel> downloadModels){
        this.context=context;
        this.downloadModels=downloadModels;

    }

    public class DownloadViewHolder extends RecyclerView.ViewHolder{

        TextView file_title;
        TextView file_size;
        ProgressBar file_progress;
        Button pause_resume;

        TextView file_status;


        public DownloadViewHolder(@NonNull View itemView) {
            super(itemView);

            file_title=itemView.findViewById(R.id.file_title);
            file_size=itemView.findViewById(R.id.file_size);
            file_progress=itemView.findViewById(R.id.file_progress);
            file_status=itemView.findViewById(R.id.file_status);
            pause_resume=itemView.findViewById(R.id.pause_resume);


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.downlod_row,parent,false);
        vh = new DownloadViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DownloadModel downloadModel = downloadModels.get(position);
        DownloadViewHolder downloadViewHolder = (DownloadViewHolder) holder;

        downloadViewHolder.file_title.setText(downloadModel.getTitle());
        downloadViewHolder.file_status.setText(downloadModel.getStatus());
        downloadViewHolder.file_progress.setProgress(Integer.parseInt(downloadModel.getProgress()));
        downloadViewHolder.file_size.setText("Downloaded : "+downloadModel.getFile_size());

        if(downloadModel.isIs_paused()){
            downloadViewHolder.pause_resume.setText("RESUME");
        }
        else{
            downloadViewHolder.pause_resume.setText("PAUSE");
        }

        if(downloadModel.getStatus().equalsIgnoreCase("RESUME")){
            downloadViewHolder.file_status.setText("Running");

        }



        downloadViewHolder.pause_resume.setOnClickListener(v -> {
            if(downloadModel.isIs_paused()){

                downloadModel.setIs_paused(false);
                downloadViewHolder.pause_resume.setText("PAUSE");
                downloadModel.setStatus("RESUME");
                downloadViewHolder.file_status.setText("Running");
                if(!resumeDownload(downloadModel)){
                    Toast.makeText(context, "Failed to Resume!", Toast.LENGTH_SHORT).show();

                }
                notifyItemChanged(position);

            }
            else{

                downloadModel.setIs_paused(true);
                downloadViewHolder.pause_resume.setText("RESUME");
                downloadModel.setStatus("PAUSE");
                downloadViewHolder.file_status.setText("PAUSE");
                if(!pauseDownload(downloadModel)){
                    Toast.makeText(context, "Failed to Pause!", Toast.LENGTH_SHORT).show();

                }
                notifyItemChanged(position);

            }
        });
    }

    private boolean pauseDownload(DownloadModel downloadModel) {
        int updateRow=0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("control",1);

        try{
            updateRow=context.getContentResolver().update(Uri.parse("content://downloads/my_downloads"),contentValues,"title=?",new String[]{downloadModel.getTitle()});

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0<updateRow;
    }

    private boolean resumeDownload(DownloadModel downloadModel) {
        int updateRow=0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("control",0);

        try{
            updateRow=context.getContentResolver().update(Uri.parse("content://downloads/my_downloads"),contentValues,"title=?",new String[]{downloadModel.getTitle()});

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0<updateRow;
    }

    @Override
    public int getItemCount() {


        return downloadModels.size();
    }

    public void changeItem(long downloadid){
        int i=0;
        for(DownloadModel downloadModel:downloadModels){
            if(downloadid==downloadModel.getDownloadId()){
                notifyItemChanged(i);
            }

            i++;
        }
    }

    public boolean ChangeItemWithStatus(String message,long downloadid){
        boolean comp=false;
        int i=0;
        for(DownloadModel downloadModel:downloadModels){
            if(downloadid==downloadModel.getDownloadId()){
                downloadModels.get(i).setStatus(message);
                notifyItemChanged(i);
                comp=true;
            }

            i++;
        }

        return comp;
    }

    public void setChangeItemFilePath(String path, long id){
        int i=0;
        for(DownloadModel downloadModel:downloadModels){
            if(id==downloadModel.getDownloadId()){
                downloadModels.get(i).setFile_path(path);
                notifyItemChanged(i);
            }

            i++;
        }

    }

}
