package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.app.BookModel;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.network.models.LibraryResponse;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**********************************************
 Arturo Lara
 Displays all of the books of Pellego Library in a recycler view.
 **********************************************/
public class PellegoLibrary extends BaseFragment {

    private PellegoLibraryViewModel mViewModel;
    private View root;
    private Fragment myFragment = this;
    RecyclerView recyclerView;


    public static PellegoLibrary newInstance() {
        return new PellegoLibrary();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.pellego_library_fragment, container, false);

        return root;
    }

    private void setupRecyclerView() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back, null);
//        drawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTint(drawable, Color.WHITE);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(drawable);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PellegoLibraryViewModel.class);
        recyclerView = root.findViewById(R.id.recyclerView);

        // get data from the model and pass in to adapter
        mViewModel.getLibResp().observe(this.getViewLifecycleOwner(), new Observer<List<LibraryResponse>>() {
            @Override
            public void onChanged(List<LibraryResponse> libraryResponses) {
                String names[] = new String[libraryResponses.size()];
                String authors[] = new String[libraryResponses.size()];
                String imgs[] = new String[libraryResponses.size()];
                String ids[] = new String[libraryResponses.size()];

                for (int i = 0; i < libraryResponses.size(); i++) {
                    names[i] = libraryResponses.get(i).getBookName();
                    authors[i] = libraryResponses.get(i).getAuthor();
                    imgs[i] = libraryResponses.get(i).getImageUrl();
                    ids[i] = libraryResponses.get(i).getBid().toString();
                }
                PellegoLibraryAdapter adapter = new PellegoLibraryAdapter(ids, names, authors, imgs, getContext(), myFragment);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }
}