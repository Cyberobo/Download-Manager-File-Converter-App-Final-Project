package com.emre.filedownloadmanagerconverter.FileConverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.emre.filedownloadmanagerconverter.ui.MainActivity;
import com.emre.filedownloadmanagerconverter.R;

public class FileConverterActivity extends AppCompatActivity {

    ImageButton backhomeBtn;

    ImageButton imgtopdfBtn,margepdfBtn,pdftoexelBtn,exeltopdfBtn,wordtopdfBtn,pdftowordBtn;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_converter);

        backhomeBtn=findViewById(R.id.backhomebutton);
        imgtopdfBtn=findViewById(R.id.imgtopdf);
        margepdfBtn=findViewById(R.id.margepdf);
        pdftoexelBtn=findViewById(R.id.pdftoexel);
        exeltopdfBtn=findViewById(R.id.exeltopdf);
        wordtopdfBtn=findViewById(R.id.wordtopdf);
        pdftowordBtn=findViewById(R.id.pdftoword);


        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(FileConverterActivity.this, MainActivity.class));
            finish();
        });


        imgtopdfBtn.setOnClickListener(v -> {
            startActivity(new Intent(FileConverterActivity.this, ImageToPdf.class));
            finish();
        });


        margepdfBtn.setOnClickListener(v -> {
            startActivity(new Intent(FileConverterActivity.this, MargePdf.class));
            finish();
        });


        pdftoexelBtn.setOnClickListener(v -> {
            startActivity(new Intent(FileConverterActivity.this, PdfToExel.class));
            finish();
        });


        exeltopdfBtn.setOnClickListener(v -> {
            startActivity(new Intent(FileConverterActivity.this, ExelToPdf.class));
            finish();
        });


        wordtopdfBtn.setOnClickListener(v -> {
            startActivity(new Intent(FileConverterActivity.this, WordToPdf.class));
            finish();
        });


        pdftowordBtn.setOnClickListener(v -> {
            startActivity(new Intent(FileConverterActivity.this, PdfToWord.class));
            finish();
        });











    }
}