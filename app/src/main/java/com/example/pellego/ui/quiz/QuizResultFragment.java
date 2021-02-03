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
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.pellego.HomeActivity;
import com.example.pellego.R;
import com.example.pellego.ui.learn.LearnFragment;
import com.example.pellego.ui.rsvp.RsvpModuleFragment;
import com.example.pellego.ui.rsvp.RsvpViewModel;

/**********************************************
 Eli Hebdon

 Quiz Results Fragment
 **********************************************/
public class QuizResultFragment extends Fragment {

    private QuizViewModel quizViewModel;
    private RsvpViewModel rsvpViewModel;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        rsvpViewModel =
                new ViewModelProvider(requireActivity()).get(RsvpViewModel.class);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
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
                Bundle args = new Bundle();
                args.putString("difficulty", quizViewModel.getDifficulty());
                args.putString("wpm", quizViewModel.getWPM().toString());
                args.putString("module", quizViewModel.getModule());
                quizViewModel = new QuizViewModel();
                quizViewModel =
                        new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
                navController.navigate(R.id.action_nav_quiz_results_to_nav_quiz, args);
                return;
            }
        });

        // Return to learning modules
        Button ret = root.findViewById(R.id.button_results_return);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                quizViewModel.clear();
                rsvpViewModel.clear();
                navController.navigate(R.id.action_nav_quiz_results_to_nav_learn);
                return;
            }
        });

        return root;
    }

}