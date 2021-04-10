package com.gitlab.capstone.pellego.fragments.quiz;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.network.models.QuizResponse;

import java.util.ArrayList;
import java.util.List;

/**********************************************
 Eli Hebdon and Chris Bordoy

 Quiz fragment that contains logic for learning
 submodule quizzes
 **********************************************/

public class QuizFragment extends BaseFragment {

    private QuizViewModel quizViewModel;
    private ModuleViewModel moduleViewModel;
    private ListView moduleList;
    private ArrayList<QuizQuestionModel> mNavItems;
    NavController navController;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mNavItems = new ArrayList<>();

        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);

        this.setupHeader(root, moduleViewModel.getModuleID());
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        quizViewModel =
                new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        quizViewModel.clear();

        final TextView textView = root.findViewById(R.id.title_quiz);

        quizViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Set view model parameters
        quizViewModel.setQuizTextCount(getArguments().getInt("quizTextCount"));
        quizViewModel.setDifficulty(getArguments().getString("difficulty"));
        quizViewModel.setWPM(Integer.parseInt(getArguments().getString("wpm")));
        quizViewModel.setModule(getArguments().getString("module"));
        quizViewModel.setSMID(quizViewModel.convertSmid(moduleViewModel.getModuleID(), getArguments().getString("smID")));

        quizViewModel.getQuizResponse(
                moduleViewModel.getModuleID(),
                quizViewModel.getSMID(),
                quizViewModel.getQUID(quizViewModel.getSMID())).observe(getViewLifecycleOwner(), new Observer<List<QuizResponse>>() {
            @Override
            public void onChanged(List<QuizResponse> response1) {
                quizViewModel.getQuestions(response1);
                quizViewModel.populateQuestionBank(quizViewModel.getQuestions(response1), quizViewModel.getAnswers(response1));

                TextView quizQuestionTextView = root.findViewById(R.id.text_quiz_question);
                quizQuestionTextView.setText(quizViewModel.getNextQuestion());
                mNavItems = quizViewModel.getNextAnswers();

                // Populate the Navigation Drawer with options
                moduleList = root.findViewById(R.id.nav_question_list);
                QuizAnswerListAdapter adapter = new QuizAnswerListAdapter(getContext(), mNavItems, moduleViewModel.getGradient());
                moduleList.setAdapter(adapter);

                // Drawer Item click listeners
                moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (quizViewModel.isLastQuestion()) {
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                            Bundle args = new Bundle();
                            args.putInt("quizTextCount", getArguments().getInt("quizTextCount"));
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
                        if (!quizViewModel.isLastQuestion()) {
                            quizViewModel.incrementQuestionCount();
                        }

                        quizQuestionTextView.setText(quizViewModel.getNextQuestion());
                        mNavItems = quizViewModel.getNextAnswers();
                        // Populate the Navigation Drawer with options
                        moduleList = root.findViewById(R.id.nav_question_list);
                        QuizAnswerListAdapter adapter = new QuizAnswerListAdapter(getContext(), mNavItems, moduleViewModel.getGradient());
                        moduleList.setAdapter(adapter);
                    }
                });
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupHeader(View root, String moduleID) {
        int[] colors = moduleViewModel.getModuleGradientColors(moduleID);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(colors[1]);
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(colors[1]);
        LinearLayout header = root.findViewById(R.id.quiz_header_container);
        header.setBackground(moduleViewModel.getGradient());
    }
}