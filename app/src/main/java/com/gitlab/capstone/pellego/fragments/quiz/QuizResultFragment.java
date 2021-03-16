package com.gitlab.capstone.pellego.fragments.quiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;


/**********************************************
 Eli Hebdon

 Quiz Results Fragment
 **********************************************/
public class QuizResultFragment extends BaseFragment {

    private QuizViewModel quizViewModel;
    private ModuleViewModel moduleViewModel;
    private NavController navController;
    private SharedPreferences sharedPref;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        View root = inflater.inflate(R.layout.fragment_quiz_results, container, false);
        super.setupHeader(root);
        final TextView textView = root.findViewById(R.id.title_results);
        quizViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(App.getAppResources().getString(R.string.quiz_results_label));
            }
        });

        // TODO: POST quiz result data to DB
        // Display score
        TextView txt = root.findViewById(R.id.text_results);
        txt.setText(quizViewModel.getFinalScore());
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        // Store results in shared preference
        if (quizViewModel.quizPassed()) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(quizViewModel.generateSubmoduleCompleteKey(), quizViewModel.quizPassed());
            updateModuleProgress();
            editor.apply();
        }


        // Display message
        TextView msg = root.findViewById(R.id.text_results_message);
        msg.setText(quizViewModel.getFinalMessage());

        // Setup button listeners
        // retry the current module
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
                navController.navigate(R.id.nav_quiz, args);
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
                moduleViewModel.clear();
                navController.navigate(R.id.nav_learn);
                return;
            }
        });

        return root;
    }

    public void updateModuleProgress() {
        // TODO: post to DB and default to local data if connection can't be established
        String key = moduleViewModel.getTechnique() + "_submodule_progress";
        int count = sharedPref.getInt(key, 0);
        // Store results in shared preference
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, ++count);
        editor.apply();
    }

}