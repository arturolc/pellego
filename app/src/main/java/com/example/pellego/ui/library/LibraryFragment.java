package com.example.pellego.ui.library;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pellego.FileDialog;
import com.example.pellego.R;

import java.io.File;

/**********************************************
    Chris Bordoy & Eli Hebdon

    The Library Component
 **********************************************/
public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    private FileDialog fileDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        final TextView textView = root.findViewById(R.id.text_library);
        libraryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // handle import button click
        View button_import = root.findViewById(R.id.button_import);
        button_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File mPath = new File(Environment.getExternalStorageDirectory() + "//DIR//");
                fileDialog = new FileDialog(getActivity(), mPath);
                fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                    public void fileSelected(File file) {
                        Log.d(getClass().getName(), "selected file " + file.toString());
                    }
                });

                fileDialog.showDialog();

            }
        });
        return root;


    }
}