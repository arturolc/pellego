package com.example.pellego.ui.library;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pellego.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.app.Activity.RESULT_OK;

/**********************************************
    Chris Bordoy & Eli Hebdon

    The Library Component
 **********************************************/
public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    private TextView emptyBookCard;
    private String emptyBookTitle;

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
        emptyBookCard = root.findViewById(R.id.book_empty);
        // handle import button click
        View button_import = root.findViewById(R.id.button_import);
        button_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start file explorer
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);

            }
        });
        return root;
    }

    /**
     * File explorer has been closed or a file was selected
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedFile = data.getData(); //The uri with the location of the file
            System.out.println("File selected: " + getNameFromURI(selectedFile));
            this.emptyBookTitle = getNameFromURI(selectedFile);
            String content = readTextFile(selectedFile);
            addToLibrary(content);
        }
    }

    /**
     * Returns the filename for the given URI
     * @param uri
     * @return
     */
    public String getNameFromURI(Uri uri) {
        Cursor c = getContext().getContentResolver().query(uri, null, null, null, null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
    }

    /**
     * Reads the selected file and returns its text
     * @param uri
     * @return
     */
    private String readTextFile(Uri uri) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getActivity().getApplicationContext().getContentResolver().openInputStream(uri)));
            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    /**
     * Adds the String to the library, updates the DB with the added text
     * associated with the current user
     * @param content
     */
    @SuppressLint("ResourceAsColor")
    private void addToLibrary(String content) {
        emptyBookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: update DB with added text
                popupMessage(content);
            }
        });
        emptyBookCard.setTextColor(R.color.black);
        emptyBookCard.setText(emptyBookTitle);

        // TODO: add another blank book to represent the next one to be added

    }

    // just a quick method to display the text file contents
    // TODO: delete this method and navigate to book reader
    public void popupMessage(String content){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(content);
        alertDialogBuilder.setTitle("");
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}