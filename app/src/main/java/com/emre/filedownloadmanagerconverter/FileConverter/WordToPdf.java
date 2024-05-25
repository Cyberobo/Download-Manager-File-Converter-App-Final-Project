package com.emre.filedownloadmanagerconverter.FileConverter;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aspose.words.Document;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import com.emre.filedownloadmanagerconverter.R;

public class WordToPdf extends AppCompatActivity {

    final String directory = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + File.separator;//+"/my_downloads/converted_files/word_to_pdf/";
    ImageButton backhomeBtn;
    ImageButton wordtopdfBtn;
    TextView wordotopdfText;
    String outputPDF;
    UUID uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_to_pdf);

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        backhomeBtn=findViewById(R.id.backhomebutton);
        wordtopdfBtn=findViewById(R.id.wordtopdfuploadbutton);
        wordotopdfText=findViewById(R.id.wordtopdftext);



        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(WordToPdf.this, FileConverterActivity.class));
            finish();
        });

        wordtopdfBtn.setOnClickListener(v -> {
            Intent myfileopener = new Intent(Intent.ACTION_GET_CONTENT);
            myfileopener.setType("*/*");

            startActivityForResult(Intent.createChooser(myfileopener,"Choose File"),10);
        });

    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(resultCode==RESULT_OK&&null != data) {
                try (InputStream inputStream = getContentResolver().openInputStream(data.getData())) {
                    uuid=UUID.randomUUID();
                    outputPDF=directory+uuid.toString()+".pdf";
                    Document doc=new Document(inputStream);
                    doc.save(outputPDF);
                    wordotopdfText.setText("File saved in downloads folder. You can convert other files by pressing upload button");
                }catch (Exception e){
                    wordotopdfText.setText("Error.Check the File type and storage permissions");

                }
            }
        }
    }
}