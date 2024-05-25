package com.emre.filedownloadmanagerconverter.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.emre.filedownloadmanagerconverter.FileConverter.FileConverterActivity;
import com.emre.filedownloadmanagerconverter.R;

public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton downloadManagerButton;
    private ImageButton fileConverterButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // ImageButton'ların referanslarını alıyoruz
        downloadManagerButton = rootView.findViewById(R.id.downloadmanagerchose);
        fileConverterButton = rootView.findViewById(R.id.fileconverter);

        // ImageButton'lara tıklama dinleyicilerini ekliyoruz
        downloadManagerButton.setOnClickListener(v -> {
            // DownloadManagerActivity'ye geçiş
            Intent intent = new Intent(getActivity(), DownloadManagerChose.class);
            startActivity(intent);
            // Fragment'ı kapatıyoruz
            getActivity().finish();
        });

        fileConverterButton.setOnClickListener(v -> {
            // FileConverterActivity'e geçiş
            Intent intent = new Intent(getActivity(), FileConverterActivity.class);
            startActivity(intent);
            // Fragment'ı kapatıyoruz
            getActivity().finish();
        });

        return rootView;
    }
}