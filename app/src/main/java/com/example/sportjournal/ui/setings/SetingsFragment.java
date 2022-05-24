package com.example.sportjournal.ui.setings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportjournal.R;

public class SetingsFragment extends Fragment {

    private SetingsViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SetingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        return root;
    }
}