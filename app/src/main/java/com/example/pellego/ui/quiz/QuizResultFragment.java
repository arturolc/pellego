package com.example.pellego.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pellego.HomeActivity;
import com.example.pellego.R;
import com.example.pellego.ui.learn.LearnFragment;
import com.example.pellego.ui.rsvp.RsvpModuleFragment;

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
        final TextView textView = root.findViewById(R.id.title_results);
        quizViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Results");
            }
        });

        // Display score
        TextView txt = root.findViewById(R.id.text_results);
        txt.setText(quizViewModel.getFinalScore());
        // Display message
        TextView msg = root.findViewById(R.id.text_results_message);
        msg.setText(quizViewModel.getFinalMessage());

        // Setup button listeners
        Button retry = root.findViewById(R.id.button_results_retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                Fragment frag = quizViewModel.getModuleFragment();
                Bundle args = new Bundle();
                args.putString("difficulty", quizViewModel.getDifficulty());
                args.putString("wpm", quizViewModel.getWPM().toString());
                args.putString("module", quizViewModel.getModule());
                frag.setArguments(args);
                fragmentTransaction.replace(R.id.host_fragment_container, frag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ((HomeActivity) getActivity()).setActionBarIconArrow();
            }
        });

        Button ret = root.findViewById(R.id.button_results_return);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                LearnFragment learnFragment = new LearnFragment();
                fragmentTransaction.replace(R.id.host_fragment_container, learnFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ((HomeActivity) getActivity()).setActionBarIconMenu();
            }
        });

        return root;
    }

}