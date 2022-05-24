package com.example.sportjournal.ui.update;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportjournal.R;

public class UpdateFragment extends Fragment {

    private UpdateViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(UpdateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_update, container, false);
        return root;
    }
}