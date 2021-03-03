package com.gitlab.capstone.pellego.fragments.progress;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gitlab.capstone.pellego.R;

public class WordsReadFragment extends Fragment {

    private WordsReadViewModel mViewModel;

    public static WordsReadFragment newInstance() {
        return new WordsReadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_words_read, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WordsReadViewModel.class);
        // TODO: Use the ViewModel
    }

}