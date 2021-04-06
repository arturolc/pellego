package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.axet.androidlibrary.app.FileTypeDetector;
import com.github.axet.androidlibrary.widgets.ErrorDialog;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.activities.MainActivity;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.network.models.SynopsisResponse;

import java.util.List;

/**********************************************
 Arturo Lara
 Preview of the book when the user clicks on recycler view item.
 Contains button to add book to library
 **********************************************/
public class BookPreviewActivity extends AppCompatActivity {

    BookPreviewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_preview);
        Intent i = getIntent();

        ((TextView) findViewById(R.id.titleTextView)).setText(i.getStringExtra("title"));
        ((TextView) findViewById(R.id.authorTextView)).setText(i.getStringExtra("author"));
        ImageView bookImg = findViewById(R.id.bookImageView);
        Glide.with(this)
                .load(i.getStringExtra("image"))
                .into(bookImg);

        model = new ViewModelProvider(this).get(BookPreviewModel.class);
        model.getSynopsisResponse(i.getStringExtra("id")).observe(this, new Observer<List<SynopsisResponse>>() {
            @Override
            public void onChanged(List<SynopsisResponse> synopsisResponses) {
                TextView syn = findViewById(R.id.synopsisTextView);
                syn.setText(synopsisResponses.get(0).getSynopsis());
            }
        });

        findViewById(R.id.addToLibraryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}