package com.gitlab.capstone.pellego.fragments.pellegolibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.network.models.LibraryResponse;

import java.util.List;

/**********************************************
 Arturo Lara

 Displays all of the books of Pellego Library in a recycler view.
 **********************************************/

public class PellegoLibraryFragment extends BaseFragment {

    private PellegoLibraryViewModel mViewModel;
    private View root;
    private Fragment myFragment = this;
    RecyclerView recyclerView;

    public static PellegoLibraryFragment newInstance() {
        return new PellegoLibraryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.pellego_library_fragment, container, false);
        ((ProgressBar)getActivity().findViewById(R.id.progress_loader)).setVisibility(View.VISIBLE);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PellegoLibraryViewModel.class);
        recyclerView = root.findViewById(R.id.recyclerView);

        mViewModel.getLibResp().observe(this.getViewLifecycleOwner(), new Observer<List<LibraryResponse>>() {
            @Override
            public void onChanged(List<LibraryResponse> libraryResponses) {
                int size = libraryResponses.size();
                String[] names = new String[size];
                String[] authors = new String[size];
                String[] imgs = new String[size];
                String[] ids = new String[size];
                String[] urls = new String[size];
                String[] hashStrings = new String[size];

                for (int i = 0; i < libraryResponses.size(); i++) {
                    LibraryResponse lib = libraryResponses.get(i);
                    names[i] = lib.getBookName();
                    authors[i] = lib.getAuthor();
                    imgs[i] = lib.getImageUrl();
                    ids[i] = lib.getBid().toString();
                    urls[i] = lib.getBookUrl();
                    hashStrings[i] = lib.getHashString();
                }
                PellegoLibraryAdapter adapter = new PellegoLibraryAdapter(ids, names, authors,
                        imgs, urls, hashStrings, getContext(), myFragment);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                ((ProgressBar)getActivity().findViewById(R.id.progress_loader)).setVisibility(View.INVISIBLE);
            }
        });
    }
}