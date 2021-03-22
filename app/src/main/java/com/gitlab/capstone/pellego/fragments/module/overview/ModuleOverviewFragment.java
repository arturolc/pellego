package com.gitlab.capstone.pellego.fragments.module.overview;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.app.Plugin;
import com.gitlab.capstone.pellego.fragments.learn.ModuleCardAdapter;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.reflect.TypeToken.get;

/**********************************************
 Eli Hebdon, Chris Bordoy & Arturo Lara

 Module Overview fragment that
 displays the technique submodules and gives a brief overview
 of the technique.
 **********************************************/

public class ModuleOverviewFragment extends BaseFragment {

    private ModuleViewModel moduleViewModel;
    ArrayList<ModuleListItemModel> mNavItems;
    private ListView moduleList;
    private TextView moduleTitle;
    private TextView moduleDescription;
    private List<SMResponse> response = new ArrayList<>();
    private LiveData<List<LMDescResponse>> response1;

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
        moduleTitle = root.findViewById(R.id.title_module_overview);
        moduleDescription = root.findViewById(R.id.text_module_description);

        response1 = moduleViewModel.getLMDescResponse();

        moduleTitle.setText(response1.getValue().get(0).getName());
        moduleDescription.setText(response1.getValue().get(0).getDescription());

        response.add(new SMResponse(
                response1.getValue().get(0).getMID(),
                response1.getValue().get(0).getSubmodules().get(0).getName(),
                response1.getValue().get(0).getSubmodules().get(0).getSubheader()));
        response.add(new SMResponse(
                response1.getValue().get(0).getMID(),
                response1.getValue().get(0).getSubmodules().get(1).getName(),
                response1.getValue().get(0).getSubmodules().get(1).getSubheader()));
        response.add(new SMResponse(
                response1.getValue().get(0).getMID(),
                response1.getValue().get(0).getSubmodules().get(2).getName(),
                response1.getValue().get(0).getSubmodules().get(2).getSubheader()));
        response.add(new SMResponse(
                response1.getValue().get(0).getMID(),
                response1.getValue().get(0).getSubmodules().get(3).getName(),
                response1.getValue().get(0).getSubmodules().get(3).getSubheader()));
        moduleViewModel.setSMResponses(response);
        moduleViewModel.getSMResponses().observe(getViewLifecycleOwner(), new Observer<List<SMResponse>>() {
            @Override
            public void onChanged(List<SMResponse> responses) {
                ModuleListAdapter adapter = new ModuleListAdapter(getContext(), responses, ModuleOverviewFragment.this);
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
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                Bundle args = new Bundle();
                moduleViewModel.showSubmodulePopupDialog = true;
                switch(position) {
                    case 0:
                        navController.navigate(R.id.nav_rsvp_intro);
                        break;
                    case 1:
                        args.putString("difficulty", "beginner");
                        args.putString("wpm", "120");
                        navController.navigate(moduleViewModel.getModule_id(), args);
                        break;
                    case 2:
                        args.putString("difficulty", "intermediate");
                        args.putString("wpm", "250");
                        navController.navigate(moduleViewModel.getModule_id(), args);
                        break;
                    case 3:
                        args.putString("difficulty", "advanced");
                        args.putString("wpm", "500");
                        navController.navigate(moduleViewModel.getModule_id(), args);
                        break;
                }
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupHeader(View root) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        new int[] {0xFFF9D976, 0xFFF39F86}
        toolbar.setBackgroundColor(0xFFF9D976);
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFFF39F86);
        ConstraintLayout header = root.findViewById(R.id.module_header_container);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFF9D976, 0xFFF39F86});
        gd.setCornerRadii(new float[] {0f, 0f, 0f, 0f, 0f, 0f, 90f, 90f});
        gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        header.setBackgroundDrawable(gd);
    }

    public Drawable getDrawable(String difficulty) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean complete = sharedPref.getBoolean(moduleViewModel.getTechnique() + "_" + difficulty + "_complete", false);
        Drawable r;
        if (complete) {
            r = getResources().getDrawable(R.drawable.ic_checked_circle);

        } else {
            r = getResources().getDrawable(R.drawable.ic_empty_circle);

        }
        return r;
    }
}
