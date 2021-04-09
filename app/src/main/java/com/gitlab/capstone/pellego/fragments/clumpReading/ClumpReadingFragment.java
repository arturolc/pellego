package com.gitlab.capstone.pellego.fragments.clumpReading;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
    private Integer wpm; //not sure we need
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
        difficulty = getArguments().getString("difficulty");
        submoduleID = getArguments().getString("smID");
        submoduleResponses = getArguments().getParcelableArrayList("subModules");

        //set content based on difficulty
        //TODO: this section doesn't work cause we have no submodules hehe
        switch(difficulty) {
            case "beginner":
                content = "Testing beginner";//(submoduleResponses.get(1).getText()).replaceAll("\\s+", " ");
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

        root = inflater.inflate(R.layout.fragment_clump_reading, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        final TextView textView = root.findViewById(R.id.title_rsvp);

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
    }

    private void showQuizPopupDialog() {
    }

    public class AsyncUpdateText {
    }
}

