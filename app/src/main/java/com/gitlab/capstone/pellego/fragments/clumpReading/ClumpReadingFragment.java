package com.gitlab.capstone.pellego.fragments.clumpReading;

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
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.fragments.rsvp.RsvpModuleFragment;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.gitlab.capstone.pellego.widgets.PlayerWidget;

import java.util.List;

/**********************************************
 Eli Hebdon

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
    private PlayerWidget playerWidget; //I think we need this
    private List<SMResponse> submoduleResponses;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //get submoduleID
        //set difficulty
        System.out.println("DEBUG: THIS IS A TEST");

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
        System.out.println("DEBUG: isShowSubmodulePopupDialog: " + moduleViewModel.isShowSubmodulePopupDialog());
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
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(R.string.submodule_popup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               // asyncUpdateText = new ClumpReadingFragment.AsyncUpdateText(); // start thread on ok
               // asyncUpdateText.execute(wpm);
                showQuizPopupDialog();

            }
        });
        dialog.show();
        moduleViewModel.setShowSubmodulePopupDialog(false);
    }

    private void showQuizPopupDialog() {
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
                args.putString("module", "clump reading");
                args.putString("smID", submoduleID);
                navController.navigate(R.id.nav_quiz, args);
            }
        });
        dialog.show();
        moduleViewModel.setShowPopupDialog(false);
    }

    //setup clump reader
    public class AsyncUpdateText {
        public void execute(Integer wpm) {
        }

        protected void onPostExecute(Integer result) {
            showQuizPopupDialog();
        }
        }
    }


