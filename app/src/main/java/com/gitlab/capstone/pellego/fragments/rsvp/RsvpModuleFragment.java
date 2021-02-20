package com.gitlab.capstone.pellego.fragments.rsvp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;


/**********************************************
 Eli Hebdon and Chris Bordoy

 RSVP module fragment that displays words, one at a time, at 'wpm'
 **********************************************/
public class RsvpModuleFragment extends Fragment {

    private View root;
    private Integer wpm;
    public String difficulty;
    private static AsyncUpdateText asyncUpdateText;
    private String content;
    private ModuleViewModel moduleViewModel;
    private FragmentActivity currentActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wpm = Integer.parseInt(getArguments().getString("wpm"));
        difficulty = getArguments().getString("difficulty");
        currentActivity = getActivity();
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);


        root = inflater.inflate(R.layout.fragment_rsvp_module, container, false);
        final TextView textView = root.findViewById(R.id.title_rsvp);



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
        // Only show popup if user navigated to the Rsvp module
       if (moduleViewModel.showSubmodulePopupDialog) showSubmodulePopupDialog();
        return root;
    }

    private void showSubmodulePopupDialog() {
        // Setup the custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ok_dialog);
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(R.string.submodule_popup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                asyncUpdateText = new AsyncUpdateText(); // start thread on ok
                asyncUpdateText.execute(wpm);
            }
        });
        dialog.show();
        moduleViewModel.showSubmodulePopupDialog = false;
    }

    private void showQuizPopupDialog() {
        // Setup the custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ok_dialog);
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(R.string.quiz_popup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                NavController navController = Navigation.findNavController(currentActivity, R.id.nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("difficulty", difficulty);
                args.putString("wpm", String.valueOf(wpm));
                args.putString("module", "rsvp");
                navController.navigate(R.id.nav_quiz, args);
            }
        });
        dialog.show();
        moduleViewModel.showPopupDialog = false;
    }

    /**
     * Asynchronously updates the text in the RSVP fragment at the provided WPM rate
     */
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
                // Verify that user has not navigated away from the RSVP fragment
                NavHostFragment navHostFragment = (NavHostFragment) currentActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                if (!navHostFragment.getChildFragmentManager().getFragments().get(0).toString().contains("RsvpModuleFragment")) {
                    cancel(true);
                    return 0;
                } else {
                    rsvp_text.setText(word);
                }
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
            try {
                showQuizPopupDialog();
            } catch (Exception e) {
                Log.d("error" , e.getMessage());
            }

        }
    }
}

