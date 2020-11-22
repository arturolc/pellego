package com.example.pellego.ui.rapid_serial_visualization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pellego.R;


public class RsvFragment extends Fragment  {

    private RsvViewModel techniqueOverviewViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        techniqueOverviewViewModel =
                new ViewModelProvider(this).get(RsvViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rsv, container, false);
        final TextView textView = root.findViewById(R.id.text_rsv);
        techniqueOverviewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }


}