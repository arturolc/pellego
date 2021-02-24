package com.example.pellego.ui.pellegolibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.pellego.BookModel;
import com.example.pellego.R;
import com.example.pellego.SingletonRequestQueue;

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