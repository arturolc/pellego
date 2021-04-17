package com.gitlab.capstone.pellego.fragments.prereading;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
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

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.metaguiding.MetaguidingModuleFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.gitlab.capstone.pellego.widgets.PlayerWidget;

import java.util.List;

public class PreReadingFragment extends BaseFragment {
    private Integer wpm;
    private int quizTextCount;
    public String difficulty;
    private TextView mtext;
    private String content = "";
    private ModuleViewModel moduleViewModel;
    private int idx = 0;
    private FragmentActivity currentView;
    public String submoduleID;
    private Button btnNext;
    private boolean finishedReading = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quizTextCount = getArguments().getInt("quizTextCount");
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

        View root = inflater.inflate(R.layout.fragment_pre_reading, container, false);
        btnNext = root.findViewById(R.id.btn_pre_reading);
        mtext = root.findViewById(R.id.pre_reading_container);
        if (moduleViewModel.isShowSubmodulePopupDialog()) showSubmodulePopupDialog(getResources().getString(R.string.prereading_popup_dialog));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finishedReading) {
                    showQuizPopupDialog();
                } else {
                    showSubmodulePopupDialog(getResources().getString(R.string.prereading_main_popup_dialog));
                    finishedReading = true;
                }
            }
        });
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
                args.putInt("quizTextCount", quizTextCount);
                args.putString("difficulty", difficulty);
                args.putString("wpm", String.valueOf(wpm));
                args.putString("module", "prereading");
                args.putString("smID", submoduleID);
                navController.navigate(R.id.nav_quiz, args);
            }
        });
        dialog.show();
        moduleViewModel.setShowPopupDialog(false);
    }

    private void showSubmodulePopupDialog(String txt) {
        // Setup the custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ok_dialog);
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(txt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mtext.setText(content);
                btnNext.setBackground(moduleViewModel.getGradient());
            }
        });
        dialog.show();
        moduleViewModel.setShowSubmodulePopupDialog(false);
    }
}
