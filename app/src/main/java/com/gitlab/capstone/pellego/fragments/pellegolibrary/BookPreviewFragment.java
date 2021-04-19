package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.activities.MainActivity;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.network.models.SynopsisResponse;

import java.util.List;

/****************************************
 * Arturo Lara
 *
 * Represents a Book Preview object for the
 * Pellego Library
 ***************************************/

public class BookPreviewFragment extends BaseFragment {
    private BookPreviewModel model;
    private MainActivity mainAct;
    private String title, author, hashString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.mainAct = (MainActivity)getActivity();
        super.onCreate(savedInstanceState);
        Storage storage = new Storage(getContext());
        title = getArguments().getString("title");
        author = getArguments().getString("author");
        hashString = getArguments().getString("hashString");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.titleTextView)).setText(title);
        ((TextView) view.findViewById(R.id.authorTextView)).setText(author);
        ImageView bookImg = view.findViewById(R.id.bookImageView);
        Glide.with(this)
                .load(getArguments().getString("image"))
                .into(bookImg);

        model = new ViewModelProvider(this).get(BookPreviewModel.class);
        model.getSynopsisResponse(getArguments().getString("id")).observe(getActivity(), new Observer<List<SynopsisResponse>>() {
            @Override
            public void onChanged(List<SynopsisResponse> synopsisResponses) {
                TextView syn = view.findViewById(R.id.synopsisTextView);
                syn.setText(synopsisResponses.get(0).getSynopsis());
            }
        });

        if (!model.inStorage(hashString)) {
            view.findViewById(R.id.addToLibraryBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("loadBook", Uri.parse(getArguments().getString("bookUrl")).toString());
                    mainAct.loadBook(Uri.parse(getArguments().getString("bookUrl")), new Runnable() {
                        @Override
                        public void run() {
                            Log.d("loadBook", "success");
                            model.updateStorage();
                        }
                    });
                }
            });
        }
        else {
            view.findViewById(R.id.addToLibraryBtn).setClickable(false);
            ((Button)view.findViewById(R.id.addToLibraryBtn)).setText("Already in Library");
        }
    }
}