package com.emre.filedownloadmanagerconverter.FileConverter;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import com.emre.filedownloadmanagerconverter.R;

public class PdfToWord extends AppCompatActivity {


    final String directory = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + File.separator;

    ImageButton backhomeBtn;
    ImageButton pdftowordBtn;
    TextView pdftowordText;

    String outputdoc;
    UUID uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_to_word);

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        backhomeBtn=findViewById(R.id.backhomebutton);
        pdftowordBtn=findViewById(R.id.pdftoworduploadbutton);
        pdftowordText=findViewById(R.id.pdftowordtext);

        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(PdfToWord.this, FileConverterActivity.class));
            finish();
        });

        pdftowordBtn.setOnClickListener(v -> {
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
                uuid=UUID.randomUUID();
                outputdoc=directory+uuid.toString()+".doc";
                try (InputStream inputStream = getContentResolver().openInputStream(data.getData())) {
                    Document pdf = new Document(inputStream);
                    pdf.save(outputdoc, SaveFormat.Doc);
                    pdftowordText.setText("File saved in Downloads folder. You can convert other files by pressing upload button");
                }catch (Exception e){
                    pdftowordText.setText("Error. Check Storage permission or Document format");
                }

            }
        }
    }

}