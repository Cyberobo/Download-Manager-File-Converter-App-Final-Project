package com.emre.filedownloadmanagerconverter.InstagramDm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.emre.filedownloadmanagerconverter.ui.DownloadManagerChose;
import com.emre.filedownloadmanagerconverter.R;

public class InstagramDmActivity extends AppCompatActivity {

    ImageButton backhomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_dm);

        backhomeBtn=findViewById(R.id.backhomebutton);

        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(InstagramDmActivity.this, DownloadManagerChose.class));
            finish();
        });
    }
}