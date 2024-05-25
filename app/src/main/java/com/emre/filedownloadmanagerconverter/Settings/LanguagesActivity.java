package com.emre.filedownloadmanagerconverter.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.emre.filedownloadmanagerconverter.R;
import com.emre.filedownloadmanagerconverter.ui.MainActivity;

import java.util.Locale;

public class LanguagesActivity extends AppCompatActivity {

    ImageButton backhomeBtn;
    Spinner spinner;
    public static String[] languages = {"English", "Turkish"};


    String [] items ={"English","Turkish"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapteritems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);

        backhomeBtn = findViewById(R.id.backhomebutton);
        autoCompleteTextView=findViewById(R.id.auto_complate_txt);
        adapteritems = new ArrayAdapter<String>(this,R.layout.langauge_item,items);
        autoCompleteTextView.setAdapter(adapteritems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = parent.getItemAtPosition(position).toString();
                String langCode = "en"; // Varsayılan dil kodu

                if (selectedLang.equals("English")) {
                    langCode = "en";
                } else if (selectedLang.equals("Turkish")) {
                    langCode = "tr";
                }

                setLocale(LanguagesActivity.this, langCode);
                //recreate(); // Dil değişikliğinin etkili olması için activity'yi yeniden başlat
                Intent intent = getIntent();
                finish();
                startActivity(intent);

                overridePendingTransition(0,0);
            }
        });


        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(LanguagesActivity.this, MainActivity.class)); // SettingsFragment yerine SettingsActivity kullanın
            finish();
        });
    }

    public void setLocale(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());

    }
}
