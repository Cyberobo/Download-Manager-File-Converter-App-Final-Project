package com.emre.filedownloadmanagerconverter.FileConverter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageButton;
import android.widget.TextView;
import com.emre.filedownloadmanagerconverter.R;
import java.io.File;
import java.util.UUID;
import androidx.annotation.Nullable;
import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ProgressBar;


import com.aspose.pdf.facades.PdfFileEditor;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MargePdf extends AppCompatActivity {

    private final String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator;//+"/my_downloads/converted_files/marge_pdf/";

    TextView text2;

    ImageButton margeuploadBtn;

    ImageButton backhomeBtn;

    String outputPDF;

    UUID uuid;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marge_pdf);

        backhomeBtn=findViewById(R.id.backhomebutton);
        text2=findViewById(R.id.margetext);
        margeuploadBtn=findViewById(R.id.margeuploadbutton);

        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(MargePdf.this, FileConverterActivity.class));
            finish();
        });

        margeuploadBtn.setOnClickListener(v -> {
            Intent myfileopener = new Intent(Intent.ACTION_GET_CONTENT);
            myfileopener.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            myfileopener.setType("*/*");
            startActivityForResult(Intent.createChooser(myfileopener,"Choose File"),10);
        });




    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(resultCode==RESULT_OK&&null != data) {
                uuid=UUID.randomUUID();
                outputPDF=directory+uuid.toString()+".pdf";
                if (null != data.getClipData()) {
                    InputStream[] list =new InputStream[data.getClipData().getItemCount()];
                    PdfFileEditor fileEditor=new PdfFileEditor();
                    for(int i=0;i<data.getClipData().getItemCount();i++){
                        try {
                            list[i]=getContentResolver().openInputStream(data.getClipData().getItemAt(i).getUri());
                        } catch (FileNotFoundException e) {
                            text2.setText("Error. please check the app storage permissions");
                        }
                    }
                    try {
                        OutputStream out=new FileOutputStream(outputPDF);
                        fileEditor.concatenate(list,out);
                        text2.setText("File saved in downloads folder. you can convert more files by pressing upload button");
                    } catch (FileNotFoundException e) {
                        text2.setText("Error. please check the app storage permissions");
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }


}