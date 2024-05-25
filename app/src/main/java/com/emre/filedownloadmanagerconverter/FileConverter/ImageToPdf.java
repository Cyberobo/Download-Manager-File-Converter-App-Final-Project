package com.emre.filedownloadmanagerconverter.FileConverter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.emre.filedownloadmanagerconverter.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.UUID;

public class ImageToPdf extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGES = 1;
    private ArrayList<String> selectedImagePath = new ArrayList<>();
    ImageButton backhomeBtn;
    ImageButton uploadBtn;

    InputStream img;
    Uri[] uri;
    int num;
    final String directory = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));//+"/my_downloads/converted_files/image_to_pdf";
    String pdfFile;
    File myPDFFile;
    TextView textView1;

    UUID uuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_pdf);

        backhomeBtn = findViewById(R.id.backhomebutton);
        uploadBtn = findViewById(R.id.imgtopdfuploadbutton);
        textView1=findViewById(R.id.imagetopdf);

        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(ImageToPdf.this, FileConverterActivity.class));
            finish();
        });

        uploadBtn.setOnClickListener(v -> {
            Intent myfileopener = new Intent(Intent.ACTION_GET_CONTENT);
            myfileopener.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            myfileopener.setType("image/*");
            startActivityForResult(Intent.createChooser(myfileopener,"Choose File"),10);
        });
    }

    @SuppressLint({"MissingSuperCall", "SetTextI18n"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                if (null != data) {
                    uuid = UUID.randomUUID();
                    pdfFile = directory + "/"+"example-"+uuid.toString()+".pdf";
                    myPDFFile = new File(pdfFile);

                    PdfDocument pdfDocument = new PdfDocument();

                    if (null != data.getClipData()) {
                        num = data.getClipData().getItemCount();
                        uri=new Uri[num];
                        Toast.makeText(getApplicationContext(), num + " Files selected", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < num; i++) {
                            uri[i]=data.getClipData().getItemAt(i).getUri();
                            Bitmap bitmap = null;
                            try {
                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri[i]));
                            } catch (FileNotFoundException e) {
                                textView1.setText("Error converting the files. please check the storage permissions.");
                            }

                            assert bitmap != null;
                            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth()+4, bitmap.getHeight()+4, i+1).create();
                            PdfDocument.Page page = pdfDocument.startPage(myPageInfo);
                            page.getCanvas().drawBitmap(bitmap, 2, 2, null);
                            pdfDocument.finishPage(page);
                        }

                    } else {
                        try {
                            Toast.makeText(getApplicationContext(), "1 file selected", Toast.LENGTH_SHORT).show();
                            img = getContentResolver().openInputStream(data.getData());
                            Bitmap bitmap = BitmapFactory.decodeStream(img);
                            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth()+4, bitmap.getHeight()+4, 1).create();
                            PdfDocument.Page page = pdfDocument.startPage(myPageInfo);
                            page.getCanvas().drawBitmap(bitmap, 2, 2, null);
                            pdfDocument.finishPage(page);

                        } catch (FileNotFoundException e) {
                            textView1.setText("Error converting the files. please check the storage permissions.");
                        }
                    }
                    try {
                        pdfDocument.writeTo(new FileOutputStream(myPDFFile));
                        textView1.setText("File saved in downloads folder.You can convert other files by pressing upload button");
                    } catch (IOException e) {
                        textView1.setText("Error converting the files. please check the storage permissions.");
                    }
                }
            }
        }
    }

}


