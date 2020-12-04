package com.example.pellego.ui.quiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.example.pellego.ui.learn.LearnViewModel;
import com.example.pellego.ui.learn.ModuleItemModel;
import com.example.pellego.ui.learn.ModuleListAdapter;
import com.example.pellego.ui.rsvp.RsvpModuleFragment;
import com.example.pellego.ui.rsvp.RsvpOverviewFragment;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Quiz fragment that contains logic for learning submodule quizes
 **********************************************/
public class QuizFragment extends Fragment {

    private QuizViewModel quizViewModel;
    private ListView moduleList;
    private RelativeLayout modulePane;
    private String difficulty;
    private ArrayList<QuizQuestionModel> mNavItems;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mNavItems = new ArrayList<>();
        quizViewModel =
                new ViewModelProvider(this).get(QuizViewModel.class);
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
        modulePane = root.findViewById(R.id.question_pane);
        moduleList = root.findViewById(R.id.nav_question_list);
        QuizQuestionListAdapter adapter = new QuizQuestionListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);

        // Drawer Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Last question, navigate to results
                if (quizViewModel.isLastQuestion()) {
                    FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                    QuizResultFragment resultFragment = new QuizResultFragment(quizViewModel);
                    fragmentTransaction.replace(R.id.host_fragment_container, resultFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    ((HomeActivity) getActivity()).setActionBarIconMenu();
                }

                // TODO: update icons based on button click
                // Right answer
                if (quizViewModel.getCorrectIndex() == position) {
                    quizViewModel.score++;

                }
                // Wrong answer
                else {

                }

                ((TextView) root.findViewById(R.id.text_quiz_question)).setText(quizViewModel.getNextQuestion());
                mNavItems = quizViewModel.getNextAnswers();
                // Populate the Navigation Drawer with options
                modulePane = root.findViewById(R.id.question_pane);
                moduleList = root.findViewById(R.id.nav_question_list);
                QuizQuestionListAdapter adapter = new QuizQuestionListAdapter(getContext(), mNavItems);
                moduleList.setAdapter(adapter);

            }
        });

        return root;
    }
}