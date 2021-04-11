package com.gitlab.capstone.pellego.fragments.rsvp;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.gitlab.capstone.pellego.widgets.PlayerWidget;

import java.util.List;

/**********************************************
 Eli Hebdon and Chris Bordoy

 RSVP module fragment that displays words, one at a time, at 'wpm'
 **********************************************/

public class RsvpModuleFragment extends BaseFragment {

    private Integer wpm;
    private int quizTextCount;
    public String difficulty;
    public String submoduleID;
    private AsyncUpdateText asyncUpdateText;
    private String content = "";
    private ModuleViewModel moduleViewModel;
    private FragmentActivity currentView;
    private PlayerWidget playerWidget;
    private List<SMResponse> submoduleResponses;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quizTextCount = getArguments().getInt("quizTextCount");
        wpm = Integer.parseInt(getArguments().getString("wpm"));
        difficulty = getArguments().getString("difficulty");
        submoduleID = getArguments().getString("smID");
        submoduleResponses = getArguments().getParcelableArrayList("subModules");

        // Set the displayed text to the appropriate level
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

        currentView = getActivity();
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);

        View root = inflater.inflate(R.layout.fragment_rsvp_module, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(null);

        this.setupHeader(root);
        // Only show popup if user navigated to the Rsvp module
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
        // Setup the custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
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
                args.putInt("quizTextCount", quizTextCount);
                args.putString("difficulty", difficulty);
                args.putString("wpm", String.valueOf(wpm));
                args.putString("module", "rsvp");
                args.putString("smID", submoduleID);
                navController.navigate(R.id.nav_quiz, args);
            }
        });
        dialog.show();
        moduleViewModel.setShowPopupDialog(false);
    }

    public void initRsvpReader(PlayerWidget playerWidget, View v, Activity a) {
        currentView = (FragmentActivity) a;
        this.playerWidget = playerWidget;
    }

    public void play() {
        if (content.equals("")) {
            content = playerWidget.selectNext();
        }
        if (PlayerWidget.playing) {
            asyncUpdateText = new AsyncUpdateText(); // start thread on ok
            asyncUpdateText.execute(PlayerWidget.wpm);
        }
    }

    public void startNext() {
        content = playerWidget.selectNext();
        asyncUpdateText = new AsyncUpdateText(); // start thread on ok
        asyncUpdateText.execute(PlayerWidget.wpm);
    }

    public void startPrev() {
        content = playerWidget.selectPrev();
        asyncUpdateText = new AsyncUpdateText(); // start thread on ok
        asyncUpdateText.execute(PlayerWidget.wpm);
    }

    public void stop() {
        asyncUpdateText.cancel(true);
    }

    private int wordCount = 0;
    int prevWPM = 0;
    /**
     * Asynchronously updates the text in the RSVP fragment at the provided WPM rate
     */
    public class AsyncUpdateText extends AsyncTask<Integer, String, Integer> {

        TextView rsvp_text;
        String[] words = content.split (" "); // split on non-word characters

        @Override
        protected void onPreExecute() {
            rsvp_text = currentView.findViewById(R.id.text_rsvp);
        }

        @SuppressLint("WrongThread")
        @Override
        protected Integer doInBackground(Integer... ints) {
            for (String word : words) {
                // Verify that user has not navigated away from the RSVP fragment
                NavHostFragment navHostFragment = (NavHostFragment) currentView.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                String currFragment = navHostFragment.getChildFragmentManager().getFragments().get(0).toString();

                if ((!currFragment.contains("RsvpModuleFragment") && (!currFragment.contains("ReaderFragment")))) {
                    cancel(true);
                    return 0;
                } else {
                    if (currFragment.contains("ReaderFragment")) {
                        if (!playerWidget.progressChanged && !playerWidget.playing) {
                            prevWPM = PlayerWidget.wpm;
                        } else if (playerWidget.progressChanged) {
                            playerWidget.setUserWordValues(wordCount, prevWPM);
                            wordCount = 0;
                            playerWidget.progressChanged = false;
                        }
                    }

                    if (!word.isEmpty()) {
                        wordCount++;
                        rsvp_text.setText(word);
                    }
                    if (currFragment.contains("ReaderFragment")) {
                        if (!playerWidget.marks.isEmpty() && word.isEmpty()) {
                            //toggle Play button to Pause

                            PlayerWidget.playing = false;
                            playerWidget.marks.clear();
                            if (wordCount != 0 && playerWidget.wpm != 0) {
                                playerWidget.setUserWordValues(wordCount, playerWidget.wpm);
                                wordCount = 0;
                            }
                            cancel(true);
                            return 0;
                        }
                    }
                    if (PlayerWidget.playing && !content.isEmpty()) {
                        content = content.replaceFirst(word, "");
                    }
                }
                try {
                    Thread.sleep((long) ((60.0 / (float) PlayerWidget.wpm) * 1000));
                } catch (Exception e) {
                    cancel(true);
                    e.printStackTrace();
                    return 0;
                }
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            try {
                if (PlayerWidget.playing) {
                    content = playerWidget.selectNext();
                    asyncUpdateText = new AsyncUpdateText();
                    asyncUpdateText.execute(PlayerWidget.wpm);
                }
                showQuizPopupDialog();
            } catch (Exception e) {
                Log.d("error" , e.getMessage());
            }

        }
    }

}

