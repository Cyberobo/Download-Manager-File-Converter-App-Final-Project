package com.emre.filedownloadmanagerconverter.LinkDm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.emre.filedownloadmanagerconverter.Splash.SplashActivity;
import com.emre.filedownloadmanagerconverter.ui.DownloadManagerChose;
import com.emre.filedownloadmanagerconverter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LinkDmActivitiy extends AppCompatActivity {

    ImageButton backhomeBtn;
    private static final int PERMISSION_REQUEST_CODE = 1001;

    public static EditText url;
    public static Button downloadbtn;

    String urltext;
    private long downloadId;

    DownloadAdapter downloadAdapter;
    List<DownloadModel> downloadModels=new ArrayList<>();
    Activity ActivityCompat = this;
    Context context;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_dm_activitiy);

        backhomeBtn=findViewById(R.id.backhomebutton);

        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(LinkDmActivitiy.this, DownloadManagerChose.class));
            finish();
        });

        url=findViewById(R.id.urllink);
        downloadbtn=findViewById(R.id.downloadbutton);

        RecyclerView data_list=findViewById(R.id.data_list);
        registerReceiver(onComplate,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();

        if(currentUser!=null){
            String userId=currentUser.getUid();
            databaseReference= FirebaseDatabase.getInstance("https://downloadmananagerfileconverter-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users").child(userId);

            downloadbtn.setOnClickListener(v -> {
                urltext=url.getText().toString();

                if(urltext.isEmpty() || !urltext.contains("http") || !urltext.contains("https")) {
                    url.setError("it is not supporting link");
                }
                else{
                    downloadfile(urltext);
                    databaseReference.push().setValue(urltext);
                    url.setText("");

                }

            });

            downloadAdapter=new DownloadAdapter(LinkDmActivitiy.this,downloadModels);
            data_list.setLayoutManager(new LinearLayoutManager(LinkDmActivitiy.this));
            data_list.setAdapter(downloadAdapter);


        }


    }


    private void downloadfile(String url){
        String filename= URLUtil.guessFileName(url,null,null);
        String downloadPath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File file = new File( /*Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/my_downloads/downloadmanager/linkDm"*/downloadPath,filename);
        DownloadManager.Request request=null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            request=new DownloadManager.Request(Uri.parse(url))
                    .setTitle(filename)
                    .setDescription("Downloading")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationUri(Uri.fromFile(file))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);



        }
        else{
            request=new DownloadManager.Request(Uri.parse(url))
                    .setTitle(filename)
                    .setDescription("Downloading")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationUri(Uri.fromFile(file))
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);


        }

        DownloadManager downloadManager=(DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        long downloadId=downloadManager.enqueue(request);

        DownloadModel downloadModel = new DownloadModel();
        downloadModel.setId(11);
        downloadModel.setStatus("Downloading");
        downloadModel.setTitle(filename);
        downloadModel.setFile_size("0");
        downloadModel.setProgress("0");
        downloadModel.setIs_paused(false);
        downloadModel.setDownloadId(downloadId);
        downloadModel.setFile_path("");

        downloadModels.add(downloadModel);
        downloadAdapter.notifyItemInserted(downloadModels.size()-1);

        DownloadStatusTask downloadStatusTask=new DownloadStatusTask(downloadModel);
        runTask(downloadStatusTask,""+downloadId);


    }

    public class DownloadStatusTask extends AsyncTask<String,String,String> {

        DownloadModel downloadModel;

        public DownloadStatusTask(DownloadModel downloadModel){
            this.downloadModel=downloadModel;
        }

        @Override
        protected String doInBackground(String... strings) {
            downloadFileProcess(strings[0]);
            return null;
        }

        @SuppressLint("Range")
        private void downloadFileProcess(String downloadId) {
            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            boolean downloading=true;
            while(downloading){
                DownloadManager.Query query= new DownloadManager.Query();
                query.setFilterById(Long.parseLong(downloadId));
                Cursor cursor=downloadManager.query(query);
                cursor.moveToFirst();

                @SuppressLint("Range")
                int bytes_downloaded=cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int total_size=cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                if(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))==DownloadManager.STATUS_SUCCESSFUL){
                    downloading=false;

                }

                int progress=(int) ((bytes_downloaded*100L)/total_size);
                String status=getStatusMessage(cursor);
                publishProgress(new String[]{String.valueOf(progress), String.valueOf(bytes_downloaded),status});
                cursor.close();


            }

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            this.downloadModel.setFile_size(bytesIntoHumanReadable(Long.parseLong(values[1])));
            this.downloadModel.setProgress(values[0]);
            if(!this.downloadModel.getStatus().equalsIgnoreCase("PAUSE") && !this.downloadModel.getStatus().equalsIgnoreCase("RESUME")){
                this.downloadModel.setStatus(values[2]);
            }

            downloadAdapter.changeItem(downloadModel.getDownloadId());
        }

        @SuppressLint("Range")
        private String getStatusMessage(Cursor cursor) {

            String msg="-";
            switch (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))){
                case DownloadManager.STATUS_FAILED:
                    msg="Failed!";
                    break;
                case DownloadManager.STATUS_PAUSED:
                    msg="Paused!";
                    break;
                case DownloadManager.STATUS_RUNNING:
                    msg="Running!";
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    msg="Complated!";
                    break;
                case DownloadManager.STATUS_PENDING:
                    msg="Pending!";
                    break;
                default:
                    msg="Unknown!";
                    break;


            }

            return msg;
        }
    }

    BroadcastReceiver onComplate=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            boolean comp=downloadAdapter.ChangeItemWithStatus("Complated",id);

            if(comp){
                DownloadManager.Query query= new DownloadManager.Query();
                query.setFilterById(id);
                DownloadManager downloadManager=(DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Cursor cursor=downloadManager.query(new DownloadManager.Query().setFilterById(id));
                cursor.moveToFirst();

                @SuppressLint("Range")
                String downloaded_path=cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                downloadAdapter.setChangeItemFilePath(downloaded_path,id);

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplate);
    }

    public void runTask(DownloadStatusTask downloadStatusTask,String id){
        try {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                downloadStatusTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,new String[]{id});
            }
            else{
                downloadStatusTask.execute(new String[]{id});
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String bytesIntoHumanReadable(long bytes) {
        long kilobyte = 1024;
        long megabyte = kilobyte * 1024;
        long gigabyte = megabyte * 1024;
        long terabyte = gigabyte * 1024;

        if ((bytes >= 0) && (bytes < kilobyte)) {
            return bytes + " B";

        } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
            return (bytes / kilobyte) + " KB";

        } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
            return (bytes / megabyte) + " MB";

        } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
            return (bytes / gigabyte) + " GB";

        } else if (bytes >= terabyte) {
            return (bytes / terabyte) + " TB";

        } else {
            return bytes + " Bytes";
        }
    }

}
