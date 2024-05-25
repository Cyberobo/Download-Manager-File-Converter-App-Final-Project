package com.emre.filedownloadmanagerconverter.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.emre.filedownloadmanagerconverter.LinkList.DownloadListFragment;
import com.emre.filedownloadmanagerconverter.R;
import com.emre.filedownloadmanagerconverter.Settings.SettingsFragment;
import com.emre.filedownloadmanagerconverter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId()== R.id.home){
                replaceFragment(new HomeFragment());
                return true;
            }
            else if(item.getItemId()==R.id.dowloandlist){
                replaceFragment(new DownloadListFragment());
                return true;
            }
            else{
                replaceFragment(new SettingsFragment());
                return true;
            }


        });


    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }

}