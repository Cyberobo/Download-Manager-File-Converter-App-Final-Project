package com.emre.filedownloadmanagerconverter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.emre.filedownloadmanagerconverter.FacebookDm.FacebookDmActivity;
import com.emre.filedownloadmanagerconverter.InstagramDm.InstagramDmActivity;
import com.emre.filedownloadmanagerconverter.LinkDm.LinkDmActivitiy;
import com.emre.filedownloadmanagerconverter.R;
import com.emre.filedownloadmanagerconverter.YoutubeDm.YoutubeDmActivity;


public class DownloadManagerChose extends AppCompatActivity {
    ImageButton backhomeBtn,linkDm,ytDm,instaDm,faceDm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager_chose);

        backhomeBtn=findViewById(R.id.backhomebutton);
        linkDm=findViewById(R.id.linkdm);
        ytDm=findViewById(R.id.ytdm);
        instaDm=findViewById(R.id.instadm);
        faceDm=findViewById(R.id.facedm);


        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(DownloadManagerChose.this,MainActivity.class));
            finish();
        });



        linkDm.setOnClickListener(v -> {
            startActivity(new Intent(DownloadManagerChose.this, LinkDmActivitiy.class));
            finish();
        });


        ytDm.setOnClickListener(v -> {
            startActivity(new Intent(DownloadManagerChose.this, YoutubeDmActivity.class));
            finish();
        });

        instaDm.setOnClickListener(v -> {
            startActivity(new Intent(DownloadManagerChose.this, InstagramDmActivity.class));
            finish();
        });

        faceDm.setOnClickListener(v -> {
            startActivity(new Intent(DownloadManagerChose.this, FacebookDmActivity.class));
            finish();
        });

    }
}