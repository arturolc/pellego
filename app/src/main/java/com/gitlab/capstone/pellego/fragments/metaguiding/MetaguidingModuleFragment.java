package com.gitlab.capstone.pellego.fragments.metaguiding;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
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

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

/**********************************************
 Eli Hebdon and Chris Bordoy

 Metaguiding fragment that guides users through text with an underlined
 or highlighted word
 **********************************************/

public class MetaguidingModuleFragment extends BaseFragment {

    private Integer wpm;
    public String difficulty;
    private static AsyncUpdateReaderText asyncUpdateReaderText;
    private TextView mtext;
    private ScrollView scroller;
    private String content = "";
    private ModuleViewModel moduleViewModel;
    private int idx = 0;
    private FragmentActivity currentView;
    private PlayerWidget playerWidget;
    private int txtColor;
    private int backgroundColor;
    public String submoduleID;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wpm = Integer.parseInt(getArguments().getString("wpm"));
        difficulty = getArguments().getString("difficulty");
        submoduleID = getArguments().getString("smID");
        List<SMResponse> submoduleResponses = getArguments().getParcelableArrayList("subModules");

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

        super.onCreateView(inflater, container, savedInstanceState);

        currentView = getActivity();
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);

        View root = inflater.inflate(R.layout.fragment_metaguiding_module, container, false);

        this.setupHeader(root);
        mtext = root.findViewById(R.id.mText);
        mtext.setBackgroundColor(Color.WHITE);
        scroller = root.findViewById(R.id.mscroller);
        mtext.setText(content);
        
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
                NavController navController = Navigation.findNavController(currentView, R.id.nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("difficulty", difficulty);
                args.putString("wpm", String.valueOf(wpm));
                args.putString("module", "metaguiding");
                args.putString("smID", submoduleID);
                navController.navigate(R.id.nav_quiz, args);
            }
        });
        dialog.show();
        moduleViewModel.setShowPopupDialog(false);
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
                asyncUpdateReaderText = new AsyncUpdateReaderText(); // start thread on ok
                asyncUpdateReaderText.execute(wpm);
            }
        });
        dialog.show();
        moduleViewModel.setShowSubmodulePopupDialog(false);
    }

    public void initMetaguidingReader(PlayerWidget playerWidget, Activity a, String theme) {
        currentView = (FragmentActivity) a;
        mtext = currentView.findViewById(R.id.mText);
        configColorProfile(theme);
        mtext.setBackgroundColor(backgroundColor);
        mtext.setTextColor(txtColor);
        scroller = currentView.findViewById(R.id.mscroller);
        this.playerWidget = playerWidget;
        if (content.equals("")) {
            content = getNextPage();
        }
        mtext.setText(content);
    }

    public void play() {
        if (content.equals("")) {
            content = getNextPage();
        }
        if (PlayerWidget.playing) {
            mtext.setText(content);
            asyncUpdateReaderText = new MetaguidingModuleFragment.AsyncUpdateReaderText(); // start thread on ok
            asyncUpdateReaderText.execute(PlayerWidget.wpm);
        }
    }

    public String getNextPage() {
        StringBuilder txt = new StringBuilder();
        while(txt.length() < 900) {
            txt.append(playerWidget.selectNext());
        }
        return txt.toString();
    }

    public String getPrevPage() {
        StringBuilder txt = new StringBuilder();
        while(txt.length() < 900) {
            txt.append(playerWidget.selectPrev());
        }
        return txt.toString();
    }

    public void startNext() {
        if (content.equals("")) {
            content = getNextPage();
        }
        mtext.setText(content);
        idx += 200;
        asyncUpdateReaderText = new MetaguidingModuleFragment.AsyncUpdateReaderText(); // start thread on ok
        asyncUpdateReaderText.execute(PlayerWidget.wpm);
    }

    public void startPrev() {
        if (content.equals("")) {
            content = getPrevPage();
        }
        mtext.setText(content);
        idx = 0;
        asyncUpdateReaderText = new MetaguidingModuleFragment.AsyncUpdateReaderText(); // start thread on ok
        asyncUpdateReaderText.execute(PlayerWidget.wpm);
    }

    public void stop() {
        asyncUpdateReaderText.cancel(true);
    }

    public void configColorProfile(String thm) {
        if (thm.equals("Theme_Dark")) {
            txtColor = Color.parseColor("#FFFFFF");
            backgroundColor = Color.parseColor("#000000");
        } else if (thm.equals("Theme_Light")) {
            txtColor = Color.parseColor("#000000");
            backgroundColor = Color.parseColor("#FFFFFF");
        } else {
            txtColor = Color.parseColor("#000000");
            backgroundColor = Color.parseColor("#F5E5CC");
        }
    }

    /**
     * Asynchronously updates the text in the RSVP fragment at the provided WPM rate
     */
    private class AsyncUpdateReaderText extends AsyncTask<Integer, String, Integer> {

        TextView mtext;
        @Override
        protected void onPreExecute() {
            mtext = currentView.findViewById(R.id.mText);
        }

        @Override
        protected Integer doInBackground(Integer... ints) {
            String pageTxt = content;
            Layout layout = mtext.getLayout();
            mtext.setText(Html.fromHtml(pageTxt));
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            scroller.scrollTo(0, layout.getLineTop(layout.getLineForOffset(idx)));
            NavHostFragment navHostFragment = (NavHostFragment) currentView.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            while (idx < pageTxt.length() - 9) {
                String currFragment = navHostFragment.getChildFragmentManager().getFragments().get(0).toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!Thread.interrupted()) {
                            if (currFragment.contains("MetaguidingModuleFragment") || (currFragment.contains("ReaderFragment") && PlayerWidget.playing)) {
                                try {
                                    String txt = "<font color='" + Integer.toHexString(txtColor) + "'>"+ pageTxt.substring(0, idx) + "<u>" + pageTxt.substring(idx, idx + 9) + "</u>" + pageTxt.substring(idx + 9) +  "</font>";
                                    mtext.setText(Html.fromHtml(txt));
                                    ObjectAnimator.ofInt(scroller, "scrollY",  layout.getLineBottom(layout.getLineForOffset(idx))).setDuration(0).start();
                                } catch (Exception e) {
                                    cancel(true);
                                }

                            }
                        }
                    }
                });
                idx++;
                if (!currFragment.contains("MetaguidingModuleFragment") && (!currFragment.contains("ReaderFragment") || !PlayerWidget.playing) || idx > pageTxt.length()) {
                    cancel(true);
                    return 0;
                }
                try {
                    Thread.sleep((long) (((60.0 / (float) PlayerWidget.wpm) * 90)));
                } catch (InterruptedException e) {
                    cancel(true);
                    e.printStackTrace();
                }
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            try {
                if (PlayerWidget.playing) {
                    content = getNextPage();
                    mtext.setText(content);
                    idx = 0;
                    asyncUpdateReaderText = new MetaguidingModuleFragment.AsyncUpdateReaderText();
                    asyncUpdateReaderText.execute(PlayerWidget.wpm);
                } else {
                    try {
                        showQuizPopupDialog();
                    } catch (Exception e) {
                        Log.d("error" , e.getMessage());
                    }
                }
            } catch (Exception e) {
                Log.d("error" , e.getMessage());
            }
        }
    }
}

