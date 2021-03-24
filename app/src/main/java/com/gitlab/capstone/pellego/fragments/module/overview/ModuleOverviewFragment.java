package com.gitlab.capstone.pellego.fragments.module.overview;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import java.util.ArrayList;
import java.util.List;

/**********************************************
 Eli Hebdon, Chris Bordoy & Arturo Lara

 Module Overview fragment that
 displays the technique submodules and gives a brief overview
 of the technique.
 **********************************************/

public class ModuleOverviewFragment extends BaseFragment {

    private ModuleViewModel moduleViewModel;
    private ListView moduleList;
    private List<SMResponse> submoduleResponse;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        String moduleID = getArguments().getString("moduleID");
        moduleViewModel =
                new ViewModelProvider(
                        requireActivity(),
                        new ModuleViewModelFactory(
                                getActivity().getApplication(),
                                moduleID)).
                        get(ModuleViewModel.class);

        View root = inflater.inflate(R.layout.fragment_module_overview, container, false);
        moduleList = root.findViewById(R.id.nav_module_list);
        moduleViewModel.setTechniqueLabel(moduleViewModel.getModuleID());
        moduleViewModel.setTechnique(moduleViewModel.getTechniqueLabel());
        moduleViewModel.setGradient(moduleGradientPicker(moduleViewModel.getModuleID()));
        TextView moduleTitle = root.findViewById(R.id.title_module_overview);
        TextView moduleDescription = root.findViewById(R.id.text_module_description);

        moduleViewModel.getLMDescResponse(moduleViewModel.getModuleID()).observe(getViewLifecycleOwner(), new Observer<List<LMDescResponse>>() {
            @Override
            public void onChanged(List<LMDescResponse> response) {
                moduleTitle.setText(response.get(0).getName());
                moduleDescription.setText(response.get(0).getDescription());
            }
        });

        moduleViewModel.getSubmodulesResponse(moduleViewModel.getModuleID()).observe(getViewLifecycleOwner(), new Observer<List<SMResponse>>() {
            @Override
            public void onChanged(List<SMResponse> response1) {
                submoduleResponse = response1;
                ModuleListAdapter adapter = new ModuleListAdapter(getContext(), response1, ModuleOverviewFragment.this);
                moduleList.setAdapter(adapter);
            }
        });
        this.setupHeader(root);

        //TODO: Currently here with progress, case 0 usually navigates with moduleViewModel.getModule_id()
        //TODO: need to figure out how to give it the right id based on position
        // menu Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController navController = Navigation.findNavController(view);
                Bundle args = new Bundle();
                args.putString("moduleID", moduleID);
                args.putParcelableArrayList("subModules", (ArrayList<? extends Parcelable>) submoduleResponse);
                moduleViewModel.setShowSubmodulePopupDialog(true);
                switch(position) {
                    case 0:
                        navController.navigate(R.id.action_nav_module_overview_to_nav_rsvp_intro, args);
                        break;
                    case 1:
                        args.putString("difficulty", "beginner");
                        args.putString("wpm", "120");
                        if (moduleID.equals("1")){
                            navController.navigate(R.id.action_nav_module_overview_to_nav_rsvp_beginner, args);
                        }
                        else {
                            navController.navigate(R.id.action_nav_module_overview_to_nav_metaguiding_beginner, args);
                        }
                        break;
                    case 2:
                        args.putString("difficulty", "intermediate");
                        args.putString("wpm", "250");
                        if (moduleID.equals("1")){
                            navController.navigate(R.id.action_nav_module_overview_to_nav_rsvp_beginner, args);
                        }
                        else {
                            navController.navigate(R.id.action_nav_module_overview_to_nav_metaguiding_beginner, args);
                        }
                        break;
                    case 3:
                        args.putString("difficulty", "advanced");
                        args.putString("wpm", "500");
                        if (moduleID.equals("1")){
                            navController.navigate(R.id.action_nav_module_overview_to_nav_rsvp_beginner, args);
                        }
                        else {
                            navController.navigate(R.id.action_nav_module_overview_to_nav_metaguiding_beginner, args);
                        }
                        break;
                }
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupHeader(View root) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(0xFFF9D976);
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFFF9D976);
        ConstraintLayout header = root.findViewById(R.id.module_header_container);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFF9D976, 0xFFF39F86});
        gd.setCornerRadii(new float[] {0f, 0f, 0f, 0f, 90f, 90f, 90f, 90f});
        gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        header.setBackground(gd);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getDrawable(String difficulty) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean complete = sharedPref.getBoolean(moduleViewModel.getTechnique() + "_" + difficulty + "_complete", false);

        Drawable r = (complete ? getResources().getDrawable(R.drawable.ic_checked_circle) : getResources().getDrawable(R.drawable.ic_empty_circle));
        r.setColorFilter(0xFFF9D976, PorterDuff.Mode.MULTIPLY);
        return r;
    }

    private Drawable moduleGradientPicker(String moduleID) {
        Drawable n = null;

        switch(moduleID) {
            case "1":
                n = getResources().getDrawable(R.drawable.orange_gradient);
                break;
            case "2":
                n =  getResources().getDrawable(R.drawable.silver_gradient);
                break;
            default:
                break;
        }

        return n;
    }
}
