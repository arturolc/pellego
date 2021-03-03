package com.gitlab.capstone.pellego.fragments.progress;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;


/**********************************************
    Chris Bordoy

    The Progress Component
 **********************************************/
public class ProgressFragment extends Fragment {

    private ProgressViewModel progressViewModel;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =
                new ViewModelProvider(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);
/*
        final TextView textView = root.findViewById(R.id.text_progress);
*/
        final ImageView imgView = root.findViewById(R.id.progress_header_background);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        //setHasOptionsMenu(false);
        //toolbar.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitle("Seth's");
        toolbar.setSubtitle("Progress Reports");
/*        progressViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Progress Reports");
            }
        });*/

        final CardView wpmView = root.findViewById(R.id.progress_wpm);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        wpmView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                navController.navigate(R.id.action_nav_progress_to_fragment_wpm);
            }
        });

        final CardView wordsReadView = root.findViewById(R.id.progress_words_read);
        wordsReadView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                navController.navigate(R.id.action_nav_progress_to_wordsReadFragment2);
            }
        });

        final CardView modulesView = root.findViewById(R.id.progress_modules);
        modulesView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                navController.navigate(R.id.action_nav_progress_to_moduleProgressFragment);
            }
        });

        final CardView quizView = root.findViewById(R.id.progress_quiz);
        quizView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                navController.navigate(R.id.action_nav_progress_to_quizProgressFragment);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
       ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }
}