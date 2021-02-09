package com.example.pellego.ui.metaguiding;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pellego.R;
import com.example.pellego.ui.defaultPager.DefaultPagerFragment;
import com.example.pellego.ui.defaultPager.PagerTask;
import com.example.pellego.ui.module.overview.ModuleViewModel;
import com.example.pellego.ui.settings.SettingsViewModel;

import java.util.ArrayList;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

/**********************************************
 Eli Hebdon

 Metaguiding fragment that guides users through text with an underlined
 or highlighted word
 **********************************************/
public class MetaguidingModuleFragment extends DefaultPagerFragment {

    private SettingsViewModel settingsViewModel;
    private View root;
    private Integer wpm;
    public String difficulty;
    private static AsyncUpdateText asyncUpdateText;
    private SpannableString content;
    private ModuleViewModel moduleViewModel;
    private FragmentActivity currentActivity;
    private int idx =0;
    private int maxChars;
    private ArrayList<String> pageText;
    public TextView contentTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        difficulty = getArguments().getString("difficulty");
        // Set the displayed text to the appropriate level
        switch(difficulty) {
            case "Beginner Submodule":
                getArguments().putInt("string_id", R.string.content_metaguiding_beginner);
                break;
            case "Intermediate Submodule":
                getArguments().putInt("string_id", R.string.content_metaguiding_intermediate);
                break;
            case "Advanced Submodule":
                getArguments().putInt("string_id", R.string.content_metaguiding_advanced);

                break;
        }
        super.onCreateView(inflater, container, savedInstanceState);
        wpm = Integer.parseInt(getArguments().getString("wpm"));
        currentActivity = getActivity();
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        root = inflater.inflate(R.layout.fragment_default_pager, container, false);
        mPager = root.findViewById(R.id.pager);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mPageIndicator = root.findViewById(R.id.pageIndicator);
        ViewGroup textviewPage = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_pager_container, (ViewGroup) getActivity().getWindow().getDecorView().findViewById(android.R.id.content) , false);


        mDisplay = getActivity().getWindowManager().getDefaultDisplay();

        if (moduleViewModel.showSubmodulePopupDialog) showSubmodulePopupDialog();
        return root;
    }

    private ArrayList<String> getPageTextArray(String content) {
        int index = 0;
        ArrayList<String> result = new ArrayList<>();
        while (index + maxChars <= content.length()) {
            result.add(content.substring(index, index + maxChars));
            index = index + maxChars;
        }
        // remaining chars
        if (index < content.length()) {
            result.add(content.substring(index));
        }
        return result;
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
                                contentTextView.setText(Html.fromHtml(pageTxt.substring(0, idx) + "<u><font color='" + getResources().getColor(R.color.light_blue) + "'>" + pageTxt.substring(idx, idx + 9) + "</u>" + pageTxt.substring(idx + 9)));
                            }
                        }
                    });
                    idx++;
                    NavHostFragment navHostFragment = (NavHostFragment) currentActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
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
                NavController navController = Navigation.findNavController(currentActivity, R.id.nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("difficulty", difficulty);
                args.putString("wpm", String.valueOf(wpm));
                args.putString("module", "rsvp");
                navController.navigate(R.id.nav_quiz, args);
            } catch (Exception e) {
                Log.d("error" , e.getMessage());
            }

        }
    }
}

