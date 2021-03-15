package com.gitlab.capstone.pellego.fragments.module.overview;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Module Overview fragment that
 displays the technique submodules and gives a brief overview
 of the technique.
 **********************************************/

public class ModuleOverviewFragment extends BaseFragment {

    private ModuleViewModel moduleViewModel;
    ArrayList<ModuleListItemModel> mNavItems;
    private ListView moduleList;
    NavigationView modulesView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_module_overview, container, false);
        final TextView textView = root.findViewById(R.id.title_module_overview);
        moduleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        mNavItems = new ArrayList<>();
        // Add nav items to the list of submodules
        mNavItems.add(new ModuleListItemModel(getResources().getString(R.string.title_module_intro), getResources().getString(R.string.descr_module_intro), getDrawable("intro")));
        mNavItems.add(new ModuleListItemModel(getResources().getString(R.string.title_module_beginner), getResources().getString(R.string.descr_module_beginner), getDrawable("beginner")));
        mNavItems.add(new ModuleListItemModel(getResources().getString(R.string.title_module_intermediate), getResources().getString(R.string.descr_module_intermediate), getDrawable("intermediate")));
        mNavItems.add(new ModuleListItemModel(getResources().getString(R.string.title_module_advanced), getResources().getString(R.string.descr_module_advanced), getDrawable("advanced")));
        TextView rsvDescription = (TextView) root.findViewById(R.id.text_module_description);
        rsvDescription.setText(moduleViewModel.getModuleDescription());

        // Populate the Navigation menu with options
        moduleList = root.findViewById(R.id.nav_module_list);
        ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);
        modulesView = root.findViewById(R.id.nav_submodule_overview);
        modulesView.setVisibility(View.VISIBLE);

        // menu Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                Bundle args = new Bundle();
                moduleViewModel.showSubmodulePopupDialog = true;
                switch(position) {
                    case 0:
                        navController.navigate(moduleViewModel.getIntro_id());
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

    public int getDrawable(String difficulty) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean complete = sharedPref.getBoolean(moduleViewModel.getTechnique() + "_" + difficulty + "_complete", false);
        if (complete) {
           return  R.drawable.ic_checked_circle;
        } else {
            return  R.drawable.ic_empty_circle;
        }
    }

}
