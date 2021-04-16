package com.gitlab.capstone.pellego.fragments.clumpReading;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.gitlab.capstone.pellego.widgets.PlayerWidget;

import java.util.List;

/**********************************************
 Eli Hebdon and Joanna Lowry

 The Clump Reading Fragment
 **********************************************/
public class ClumpReadingFragment extends BaseFragment {

    private View root;
    private Integer wpm;
    public String difficulty;
    public String submoduleID;
    private static ClumpReadingFragment.AsyncUpdateText asyncUpdateText;
    private String content = "";
    private ModuleViewModel moduleViewModel;
    private FragmentActivity currentView;
    private List<SMResponse> submoduleResponses;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        wpm = Integer.parseInt(getArguments().getString("wpm"));
        difficulty = getArguments().getString("difficulty");
        submoduleID = getArguments().getString("smID");
        submoduleResponses = getArguments().getParcelableArrayList("subModules");

        //set content based on difficulty
        switch(difficulty) {
            case "beginner":
                content = (submoduleResponses.get(1).getText()).replaceAll("\\s+", " ");
                break;
            case "intermediate":
                content = (submoduleResponses.get(2).getText()).replaceAll("\\s+", " ");
                break;
            case "advanced":
                content = (submoduleResponses.get(3).getText()).replaceAll("\\s+", " ");
                break;
        }

        super.onCreateView(inflater, container, savedInstanceState);

        currentView = getActivity();
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);

        root = inflater.inflate(R.layout.fragment_clump_reading, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(null);

        this.setupHeader(root);
        if (moduleViewModel.isShowSubmodulePopupDialog()) showSubmodulePopupDialog();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupHeader(View root) {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.color, typedValue, true);
        int color = typedValue.data;
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(color);
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }

    private void showSubmodulePopupDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ok_dialog);
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(R.string.clumpreading_popup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                asyncUpdateText = new ClumpReadingFragment.AsyncUpdateText(); // start thread on ok
                asyncUpdateText.execute(wpm);
            }
        });
        dialog.show();
        moduleViewModel.setShowSubmodulePopupDialog(false);
    }

    private void showQuizPopupDialog() {
        // Setup the custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.ok_dialog);
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(R.string.quiz_popup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                NavController navController = Navigation.findNavController(currentView, R.id.nav_host_fragment);
                Bundle args = new Bundle();
                //args.putInt("quizTextCount", quizTextCount);
                args.putString("difficulty", difficulty);
                args.putString("wpm", String.valueOf(wpm));
                args.putString("module", "ClumpReading");
                args.putString("smID", submoduleID);
                navController.navigate(R.id.nav_quiz, args);
            }
        });
        dialog.show();
        moduleViewModel.setShowPopupDialog(false);
    }


    /**
     * Asynchronously updates the text in the Clump Reading fragment at the provided WPM rate
     */
    public class AsyncUpdateText extends AsyncTask<Integer, String, Integer> {

        TextView clump_text;
        String[] words = content.split (" "); // split on non-word characters

        @Override
        protected void onPreExecute() {
            clump_text = currentView.findViewById(R.id.text_clump_reading);
        }

        @SuppressLint("WrongThread")
        @Override
        protected Integer doInBackground(Integer... ints) {
            int i;
            for(i = 0; i < words.length -5; i+= 5) {
                String word = getClump(words, i);

                // Verify that user has not navigated away from the ClumpReading fragment
                NavHostFragment navHostFragment = (NavHostFragment) currentView.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                String currFragment = navHostFragment.getChildFragmentManager().getFragments().get(0).toString();
                if (!currFragment.contains("ClumpReadingFragment")) {
                    cancel(true);
                    return 0;
                }
                if (!word.isEmpty()) {
                    clump_text.setText(word);
                }

                try {
                    Thread.sleep((long) ((60.0 / ((float) PlayerWidget.wpm) / .25) * 1000));
                } catch (Exception e) {
                    cancel(true);
                    e.printStackTrace();
                    return 0;
                }
            }

            String word = "";
            for(int k = i; k < words.length; k++)
                word += words[k] + " ";

            clump_text.setText(word);

            return 0;
        }

        /**
         * Gets the next 5 words in the array and concatenate them together
         * @param words
         * @param i
         * @return the string clump
         */
        private String getClump(String [] words, int i)
        {

            String[] clump = new String[]{words[i], words[i+1], words[i+2], words[i+3], words[i+4]};

            String final_clump = "";
            int j = 5;
            for(int k = 0; k < j; k++)
            {
                final_clump += (clump[k] + " ");
            }

            return final_clump;
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


