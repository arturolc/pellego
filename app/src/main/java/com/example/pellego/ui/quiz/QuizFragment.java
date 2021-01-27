package com.example.pellego.ui.quiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pellego.R;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Quiz fragment that contains logic for learning submodule quizes
 **********************************************/
public class QuizFragment extends Fragment {

    private QuizViewModel quizViewModel;
    private ListView moduleList;
    private ArrayList<QuizQuestionModel> mNavItems;
    NavController navController;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mNavItems = new ArrayList<>();
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        quizViewModel =
                new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);
        final TextView textView = root.findViewById(R.id.title_quiz);
        quizViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        // Set view model parameters
        quizViewModel.setDifficulty(getArguments().getString("difficulty"));
        quizViewModel.setWPM(Integer.parseInt(getArguments().getString("wpm")));
        quizViewModel.setModule(getArguments().getString("module"));

        quizViewModel.populateQuestionBank();
        ((TextView) root.findViewById(R.id.text_quiz_question)).setText(quizViewModel.getNextQuestion());
        mNavItems = quizViewModel.getNextAnswers();

        // Populate the Navigation Drawer with options
        moduleList = root.findViewById(R.id.nav_question_list);
        QuizQuestionListAdapter adapter = new QuizQuestionListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);

        // Drawer Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Last question, navigate to results
                Bundle args = new Bundle();
                if (quizViewModel.isLastQuestion()) {
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_quiz_results);
                }
                // TODO: update icons based on button click
                // Right answer
                if (quizViewModel.getCorrectIndex() == position) {
                    quizViewModel.score++;
                }
                // Wrong answer
                else {
                    // TODO: update UI to reflect incorrect response?
                }
                ((TextView) root.findViewById(R.id.text_quiz_question)).setText(quizViewModel.getNextQuestion());
                mNavItems = quizViewModel.getNextAnswers();
                // Populate the Navigation Drawer with options
                moduleList = root.findViewById(R.id.nav_question_list);
                QuizQuestionListAdapter adapter = new QuizQuestionListAdapter(getContext(), mNavItems);
                moduleList.setAdapter(adapter);
            }
        });

        return root;
    }
}