package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.gitlab.capstone.pellego.R;

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


        model = new BookPreviewModel(getApplication(), i.getStringExtra("id"));

        model.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String mdl) {
                TextView syn = findViewById(R.id.synopsisTextView);
                syn.setText(mdl);
            }
        });
    }

    public void addToLibrary(View view) {
        model.addBookToUserLibrary();
    }
}