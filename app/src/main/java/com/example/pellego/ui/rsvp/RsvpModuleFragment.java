package com.example.pellego.ui.rsvp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import com.example.pellego.ui.quiz.QuizFragment;
import com.example.pellego.ui.settings.SettingsViewModel;

/**********************************************
 Eli Hebdon

 RSVP module fragment that displays words, one at a time, at 'wpm'
 **********************************************/
public class RsvpModuleFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private View root;
    private Integer wpm;
    public String difficulty;
    private static AsyncUpdateText asyncUpdateText;
    private String content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wpm = Integer.parseInt(getArguments().getString("wpm"));
        difficulty = getArguments().getString("difficulty");
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        root = inflater.inflate(R.layout.fragment_rsvp_module, container, false);
        final TextView textView = root.findViewById(R.id.title_rsvp);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { textView.setText(difficulty); }
        });


        // Set the displayed text to the appropriate level
        switch(difficulty) {
            case "Beginner Submodule":
                content = getString(R.string.content_rsvp_beginner);
                break;
            case "Intermediate Submodule":
                content = getString(R.string.content_rsvp_intermediate);
                break;
            case "Advanced Submodule":
                content = getString(R.string.content_rsvp_advanced);
                break;
        }

        // Setup the custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ok_dialog);
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(R.string.text_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Kill the previous process if there was one and start a new one
                try {
                    asyncUpdateText.cancel(true);
                } catch (Exception e) {
                    System.out.println(e);
                }
                asyncUpdateText = new AsyncUpdateText(); // start thread on ok
                asyncUpdateText.execute(wpm);
            }
        });
        dialog.show();

        return root;
    }

    private class AsyncUpdateText extends AsyncTask<Integer, String, Integer> {

        TextView rsvp_text;
        String[] words = content.split ("\\W+"); // split on non-word characters

        @Override
        protected void onPreExecute() {
            rsvp_text = root.findViewById(R.id.text_rsvp);
        }

        @Override
        protected Integer doInBackground(Integer... ints) {
            long delay = (long) ((60.0 / (float) ints[0]) * 1000);
            for (String word : words) {
                rsvp_text.setText(word);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // TODO: navigate to quiz upon completion
            FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
            // TODO: navigate to fragment based on click id
            QuizFragment quizFragment = new QuizFragment();
            Bundle args = new Bundle();
            args.putString("difficulty", difficulty.toString());
            args.putString("wpm", String.valueOf(wpm));
            quizFragment.setArguments(args);
            fragmentTransaction.replace(R.id.host_fragment_container, quizFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            ((HomeActivity) getActivity()).setActionBarIconMenu();
        }
    }
}

