package com.example.pellego.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pellego.R;

/**********************************************
 Eli Hebdon

 Quiz Results Fragment
 **********************************************/
public class QuizResultFragment extends Fragment {

    private QuizViewModel quizViewModel;

    public QuizResultFragment(QuizViewModel model) {
        this.quizViewModel = model;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quiz_results, container, false);
        final TextView textView = root.findViewById(R.id.text_results);
        quizViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Results");
            }
        });
        return root;
    }
}