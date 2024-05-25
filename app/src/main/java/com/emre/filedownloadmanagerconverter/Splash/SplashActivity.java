package com.emre.filedownloadmanagerconverter.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.emre.filedownloadmanagerconverter.AccountManagment.Loginactivity;
import com.emre.filedownloadmanagerconverter.R;

import java.io.File;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {


    // WRITE_EXTERNAL_STORAGE izni talep kodu
    private static final int WRITE_PERMISSION_REQUEST_CODE = 100;
    // MANAGE_EXTERNAL_STORAGE izni talep kodu (Sadece Android 11 ve sonrası)
    private static final int MANAGE_STORAGE_PERMISSION_REQUEST_CODE = 101;

    String my_downloads = "my_downloads";
    String downloadmanager= "downloadManager";

    String link_dm="linkDm";
    String youtube_videos="youtubeDm";
    String instagram_videos="instagramDm";
    String facebook_videos="facebookDm";
    String converted_files="converted_files";

    String image_to_pdf_files="image_to_pdf";
    String marge_files="marge_pdf";
    String pdf_to_exel_files="pdf_to_exel";
    String exel_to_pdf_files="exel_to_pdf";
    String word_to_pdf_files="word_to_pdf";
    String pdf_to_word_files="pdf_to_word";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // WRITE_EXTERNAL_STORAGE iznini kontrol et ve talep et
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST_CODE);
            }
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                splashskip();
            }
        }
        // MANAGE_EXTERNAL_STORAGE iznini kontrol et ve talep et (Sadece Android 11 ve sonrası)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if(checkSelfPermission(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.MANAGE_EXTERNAL_STORAGE}, MANAGE_STORAGE_PERMISSION_REQUEST_CODE);
            }
            if(checkSelfPermission(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                splashskip();
            }
        }
        else {
            // Android 6.0 öncesi versiyonlarda izinler otomatik olarak verilir
            // İzinlerin verildiği kod bloğu
            createFolder(my_downloads,downloadmanager,link_dm,youtube_videos,instagram_videos,facebook_videos,converted_files,image_to_pdf_files,marge_files,pdf_to_exel_files,
                    exel_to_pdf_files,word_to_pdf_files,pdf_to_word_files);
            splashskip();
        }



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // WRITE_EXTERNAL_STORAGE izni verildi, işlemlere devam et
                createFolder(my_downloads,downloadmanager,link_dm,youtube_videos,instagram_videos,facebook_videos,converted_files,image_to_pdf_files,marge_files,pdf_to_exel_files,
                        exel_to_pdf_files,word_to_pdf_files,pdf_to_word_files);
                splashskip();

            } else {
                // Kullanıcı WRITE_EXTERNAL_STORAGE iznini vermedi, uygulamayı kapat
                finish();
            }
        } else if (requestCode == MANAGE_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // MANAGE_EXTERNAL_STORAGE izni verildi, işlemlere devam et
                createFolder(my_downloads,downloadmanager,link_dm,youtube_videos,instagram_videos,facebook_videos,converted_files,image_to_pdf_files,marge_files,pdf_to_exel_files,
                        exel_to_pdf_files,word_to_pdf_files,pdf_to_word_files);
                splashskip();

            } else {
                // Kullanıcı MANAGE_EXTERNAL_STORAGE iznini vermedi, uygulamayı kapat
                finish();
            }
        }
    }

    public static void createFolder(String my_downloads,String downloadmanager,String link_dm,String youtube_videos,String instagram_videos,String facebook_videos,
                                    String converted_files,String image_to_pdf_files,String marge_files,String pdf_to_exel_files,String exel_to_pdf_files,String word_to_pdf_files,String pdf_to_word_files){
        File my_downloads_folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + my_downloads);
        if (!my_downloads_folder.exists()) {
            my_downloads_folder.mkdirs(); // Klasörü oluştur
        }


        //downloadmanager root
        File downloadmanager_folder = new File(my_downloads_folder.getPath() + File.separator + downloadmanager);
        if (!downloadmanager_folder.exists()) {
            downloadmanager_folder.mkdirs(); // Alt klasörü oluştur
        }

        File linkDm_folder = new File(downloadmanager_folder.getPath() + File.separator + link_dm);
        if (!linkDm_folder.exists()) {
            linkDm_folder.mkdirs(); // Alt klasörü oluştur
        }


        File youtube_videos_folder = new File(downloadmanager_folder.getPath() + File.separator + youtube_videos);
        if (!youtube_videos_folder.exists()) {
            youtube_videos_folder.mkdirs(); // Alt klasörü oluştur
        }


        File instagram_videos_folder = new File(downloadmanager_folder.getPath() + File.separator + instagram_videos);
        if (!instagram_videos_folder.exists()) {
            instagram_videos_folder.mkdirs(); // Alt klasörü oluştur
        }


        File facebook_videos_folder = new File(downloadmanager_folder.getPath() + File.separator + facebook_videos);
        if (!facebook_videos_folder.exists()) {
            facebook_videos_folder.mkdirs(); // Alt klasörü oluştur
        }

        //fileconverter root
        File converted_files_folder = new File(my_downloads_folder.getPath() + File.separator + converted_files);
        if (!converted_files_folder.exists()) {
            converted_files_folder.mkdirs(); // Klasörü oluştur
        }

        File image_to_pdf_folder = new File(converted_files_folder.getPath() + File.separator + image_to_pdf_files);
        if (!image_to_pdf_folder.exists()) {
            image_to_pdf_folder.mkdirs(); // Klasörü oluştur
        }

        File marge_pdf_folder = new File(converted_files_folder.getPath() + File.separator + marge_files);
        if (!marge_pdf_folder.exists()) {
            marge_pdf_folder.mkdirs(); // Klasörü oluştur
        }

        File pdf_to_exel_folder = new File(converted_files_folder.getPath() + File.separator + pdf_to_exel_files);
        if (!pdf_to_exel_folder.exists()) {
            pdf_to_exel_folder.mkdirs(); // Klasörü oluştur
        }

        File exel_to_pdf_folder = new File(converted_files_folder.getPath() + File.separator + exel_to_pdf_files);
        if (!exel_to_pdf_folder.exists()) {
            exel_to_pdf_folder.mkdirs(); // Klasörü oluştur
        }

        File word_to_pdf_folder = new File(converted_files_folder.getPath() + File.separator + word_to_pdf_files);
        if (!word_to_pdf_folder.exists()) {
            word_to_pdf_folder.mkdirs(); // Klasörü oluştur
        }

        File pdf_to_word_folder = new File(converted_files_folder.getPath() + File.separator + pdf_to_word_files);
        if (!pdf_to_word_folder.exists()) {
            pdf_to_word_folder.mkdirs(); // Klasörü oluştur
        }

    }

    public void splashskip(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, Loginactivity.class));
                finish();
            }
        },3000);
    }

}