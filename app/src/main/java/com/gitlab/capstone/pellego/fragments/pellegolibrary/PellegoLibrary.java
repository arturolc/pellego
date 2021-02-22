package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gitlab.capstone.pellego.app.BookModel;
import com.gitlab.capstone.pellego.R;

import java.util.List;

/**********************************************
 Arturo Lara
 Displays all of the books of Pellego Library in a recycler view.
 **********************************************/
public class PellegoLibrary extends Fragment {

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PellegoLibraryViewModel.class);
        recyclerView = root.findViewById(R.id.recyclerView);

        // get data from the model and pass in to adapter
        mViewModel.getBooks().observe(this.getViewLifecycleOwner(), new Observer<List<BookModel>>() {
            @Override
            public void onChanged(@Nullable List<BookModel> mdl) {
                String names[] = new String[mdl.size()];
                String authors[] = new String[mdl.size()];
                String imgs[] = new String[mdl.size()];
                String ids[] = new String[mdl.size()];

                for (int i = 0; i < mdl.size(); i++) {
                    names[i] = mdl.get(i).getBookName();
                    authors[i] = mdl.get(i).getAuthor();
                    imgs[i] = mdl.get(i).getImgFilePath();
                    ids[i] = mdl.get(i).getbID();
                }
                PellegoLibraryAdapter adapter = new PellegoLibraryAdapter(ids, names, authors, imgs, getContext(), myFragment);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }
}