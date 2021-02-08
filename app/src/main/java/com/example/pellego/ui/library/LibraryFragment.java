package com.example.pellego.ui.library;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
    private View root;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_library, container, false);
        libraryViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);
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
                // Start file explorer
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
            }
        });



        // TODO: populate library gridlayout by querying the DB and set on click listeners

        // This is a demo on click listener just on the first book in the library to show the transition to the default reader
        TextView book1 = (TextView) root.findViewById(R.id.book_1);
        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                Bundle args = new Bundle();
                args.putInt("string_id", R.string.content_test_book);
                navController.navigate(R.id.nav_default_pager, args);
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedFile = data.getData(); //The uri with the location of the file
            System.out.println("File selected: " + getNameFromURI(selectedFile));
            String title = getNameFromURI(selectedFile);
            String content = readTextFile(selectedFile);
            addToLibrary(content, title);
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    private void addToLibrary(String content, String title) {
        TextView book = new TextView(getActivity());
        book.setTextColor(R.color.black);
        book.setBackgroundResource(R.drawable.rounded_background);
        GridLayout grid =  root.findViewById(R.id.book_grid);
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        book.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.white_transparent));
        book.setGravity(Gravity.CENTER);
        int num_children = grid.getChildCount();
        int cur_row = num_children / 3;
        int cur_col = num_children % 3;
        param.columnSpec = GridLayout.spec(cur_col);
        param.rowSpec = GridLayout.spec(cur_row);
        param.setMargins(4, 4, 4, 4);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        param.height = (int) (160 * scale + 0.5f);
        param.width = (int) (110 * scale + 0.5f);
        param.setGravity(Gravity.CENTER);
        book.setLayoutParams (param);
        grid.addView(book);
        book.setText(title);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: update DB with added text
                popupMessage(content);
            }
        });

        // TODO: update DB with new book for current user
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