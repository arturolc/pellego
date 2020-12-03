package com.example.pellego.ui.clumpReading;

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
import com.example.pellego.ui.library.ClumpReadingViewModel;

/**********************************************
 Eli Hebdon

 The Clump Reading Fragment
 **********************************************/
public class ClumpReadingFragment extends Fragment {

    private com.example.pellego.ui.library.ClumpReadingViewModel clumpReadingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clumpReadingViewModel =
                new ViewModelProvider(this).get(ClumpReadingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clump_reading, container, false);
        final TextView textView = root.findViewById(R.id.text_clump_reading);
        clumpReadingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}