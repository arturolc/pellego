package com.gitlab.capstone.pellego.fragments.metaguiding;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.fragments.metaguiding.defaultPager.DefaultPagerFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.fragments.rsvp.RsvpModuleFragment;
import com.gitlab.capstone.pellego.widgets.PlayerWidget;

import org.geometerplus.fbreader.fbreader.options.ColorProfile;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

/**********************************************
 Eli Hebdon

 Metaguiding fragment that guides users through text with an underlined
 or highlighted word
 **********************************************/
public class MetaguidingModuleFragment extends DefaultPagerFragment {

    private View root;
    private Integer wpm;
    public String difficulty;
    private static AsyncUpdateText asyncUpdateText;
    private static AsyncUpdateReaderText asyncUpdateReaderText;
    private TextView mtext;
    private ScrollView scroller;
    private String content = "";
    private ModuleViewModel moduleViewModel;
    private int idx =0;
    private int color;
    public TextView contentTextView;
    private FragmentActivity currentView;
    private PlayerWidget playerWidget;
    private String txtColor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        difficulty = getArguments().getString("difficulty");
        // Set the displayed text to the appropriate level
        switch(difficulty) {
            case "beginner":
                getArguments().putInt("string_id", R.string.content_metaguiding_beginner);
                break;
            case "intermediate":
                getArguments().putInt("string_id", R.string.content_metaguiding_intermediate);
                break;
            case "advanced":
                getArguments().putInt("string_id", R.string.content_metaguiding_advanced);

                break;
        }
        super.onCreateView(inflater, container, savedInstanceState);
        wpm = Integer.parseInt(getArguments().getString("wpm"));
        currentView = getActivity();
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);

        root = inflater.inflate(R.layout.fragment_default_pager, container, false);
        mPager = root.findViewById(R.id.pager);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mPageIndicator = root.findViewById(R.id.pageIndicator);
        ViewGroup textviewPage = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_pager_container, (ViewGroup) getActivity().getWindow().getDecorView().findViewById(android.R.id.content) , false);

        if (moduleViewModel.showSubmodulePopupDialog) showSubmodulePopupDialog();
        return root;
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
                navController.navigate(R.id.nav_quiz, args);
            }
        });
        dialog.show();
        moduleViewModel.showPopupDialog = false;
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


    public void initMetaguidingReader(PlayerWidget playerWidget, Activity a, String theme) {
        currentView = (FragmentActivity) a;
        mtext = currentView.findViewById(R.id.mText);
        scroller = currentView.findViewById(R.id.mscroller);
        this.playerWidget = playerWidget;
        color = a.getResources().getColor(R.color.light_blue);
        configColorProfile(theme);
        if (content == "") {
            content = getNextPage();
        }
        mtext.setText(content);
    }

    public void play() {
        if (content == "") {
            content = getNextPage();
        }
        if (PlayerWidget.playing) {
            mtext.setText(content);
            asyncUpdateReaderText = new MetaguidingModuleFragment.AsyncUpdateReaderText(); // start thread on ok
            asyncUpdateReaderText.execute(PlayerWidget.wpm);
        }

    }

    public String getNextPage() {
        String txt = "";
        while(txt.length() < 1500) {
            txt += playerWidget.selectNext();
        }
        return txt;
    }

    public String getPrevPage() {
        String txt = "";
        while(txt.length() < 1500) {
            txt += playerWidget.selectPrev();
        }
        return txt;
    }

    public void startNext() {
        if (content == "") {
            content = getNextPage();
        }
        mtext.setText(content);
        idx += 200;
        asyncUpdateReaderText = new MetaguidingModuleFragment.AsyncUpdateReaderText(); // start thread on ok
        asyncUpdateReaderText.execute(PlayerWidget.wpm);
    }

    public void startPrev() {
        if (content == "") {
            content = getNextPage();
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
            txtColor = "#FFFFFF";
        } else if (thm.equals("Theme_Light")) {
            txtColor = "#000000";
        } else {
            txtColor = "#000000";
        }
    }


    /**
     * Asynchronously updates the text in the RSVP fragment at the provided WPM rate
     */
    private class AsyncUpdateReaderText extends AsyncTask<Integer, String, Integer> {

        TextView mtext;
        String[] words = content.split (" "); // split on non-word characters
        @Override
        protected void onPreExecute() {
            mtext = currentView.findViewById(R.id.mText);
        }

        @Override
        protected Integer doInBackground(Integer... ints) {
            String pageTxt = content;
            Layout layout = mtext.getLayout();
            scroller.scrollTo(0, layout.getLineBottom(layout.getLineForOffset(idx)));
            NavHostFragment navHostFragment = (NavHostFragment) currentView.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            while (idx < pageTxt.length() - 9) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!Thread.interrupted()) {
                            if ((PlayerWidget.playing)) {
                                mtext.setText(Html.fromHtml("<font color='#FFFFFF'>"+ pageTxt.substring(0, idx) + "<u>" + pageTxt.substring(idx, idx + 9) + "</u>" + pageTxt.substring(idx + 9) +  "</font>"));
                                ObjectAnimator.ofInt(scroller, "scrollY",  layout.getLineBottom(layout.getLineForOffset(idx))).setDuration(100).start();
                            }
                        }
                    }
                });
                idx++;
                String currFragment = navHostFragment.getChildFragmentManager().getFragments().get(0).toString();
                if (!currFragment.contains("MetaguidingModuleFragment") && (!currFragment.contains("ReaderFragment") || !PlayerWidget.playing)) {
                    cancel(true);
                    return 0;
                }
                try {
                    Thread.sleep((long) (((60.0 / (float) PlayerWidget.wpm) * 70)));
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
                }
            } catch (Exception e) {
                Log.d("error" , e.getMessage());
            }

        }
    }

    /**
     *
     * Asynchronously updates the text in the metaguiding fragment at the provided WPM rate
     */
    private class AsyncUpdateText extends AsyncTask<Integer, String, Integer> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Integer doInBackground(Integer... ints) {
            // This delay requires some fine tuning because word length varies
            long delay = (long) (((60.0 / (float) ints[0]) * 70));
            contentTextView = (TextView) root.findViewById(R.id.mText);
            for (int i = 0; i < mPages.size(); i++) {
                String pageTxt = getContents(i);
                showPageIndicator(i);
                while (idx < pageTxt.length() - 9) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!Thread.interrupted()) {
                                NavHostFragment navHostFragment = (NavHostFragment) currentView.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                                if (navHostFragment.getChildFragmentManager().getFragments().get(0).toString().contains("MetaguidingModuleFragment")) {
                                    contentTextView.setText(Html.fromHtml(pageTxt.substring(0, idx) + "<u><font color='" + getResources().getColor(R.color.light_blue) + "'>" + pageTxt.substring(idx, idx + 9) + "</u>" + pageTxt.substring(idx + 9)));
                                }
                            }
                        }
                    });
                    idx++;
                    NavHostFragment navHostFragment = (NavHostFragment) currentView.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    if (!navHostFragment.getChildFragmentManager().getFragments().get(0).toString().contains("MetaguidingModuleFragment")) {
                        cancel(true);
                        return 0;
                    }
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        cancel(true);
                        e.printStackTrace();
                    }
                }
                idx = 0;
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

