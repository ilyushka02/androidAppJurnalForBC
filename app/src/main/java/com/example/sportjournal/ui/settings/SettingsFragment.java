package com.example.sportjournal.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportjournal.R;
import com.example.sportjournal.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private View root;
    private Button exit;
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        final TextView textView = binding.textSettings;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        initialization();
        return root;
    }

    private void initialization() {
        exit = (Button) root.findViewById(R.id.exit);
        exit.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit:
                System.exit(0);
                break;
        }
    }
}