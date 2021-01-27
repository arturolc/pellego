package com.example.pellego.ui.module;
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

import com.example.pellego.R;
import com.example.pellego.ui.learn.ModuleListAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Module Overview fragment that
 displays the technique submodules and gives a brief overview
 of the technique.
 **********************************************/

public class ModuleOverviewFragment extends Fragment  {

    private ModuleViewModel moduleViewModel;
    ArrayList<ModuleItemModel> mNavItems;
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
        mNavItems.add(new ModuleItemModel(getResources().getString(R.string.title_module_intro), getResources().getString(R.string.descr_module_intro), R.drawable.ic_checked_circle));
        mNavItems.add(new ModuleItemModel(getResources().getString(R.string.title_module_beginner), getResources().getString(R.string.descr_module_beginner), R.drawable.ic_empty_circle));
        mNavItems.add(new ModuleItemModel(getResources().getString(R.string.title_module_intermediate), getResources().getString(R.string.descr_module_intermediate), R.drawable.ic_empty_circle));
        mNavItems.add(new ModuleItemModel(getResources().getString(R.string.title_module_advanced), getResources().getString(R.string.descr_module_advanced), R.drawable.ic_empty_circle));
        TextView rsvDescription = (TextView) root.findViewById(R.id.text_module_description);
        rsvDescription.setText(moduleViewModel.getModuleDescription());

        // Populate the Navigation menu with options
        moduleList = root.findViewById(R.id.nav_module_list);
        ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);
        modulesView = root.findViewById(R.id.nav_module_overview);
        modulesView.setVisibility(View.VISIBLE);

        // menu Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                Bundle args = new Bundle();
                moduleViewModel.showDialog = true;
                switch(position) {
                    case 0:
                        navController.navigate(moduleViewModel.getIntro_id());
                        break;
                    case 1:
                        args.putString("difficulty", "Beginner Submodule");
                        args.putString("wpm", "120");
                        navController.navigate(moduleViewModel.getModule_id(), args);
                        break;
                    case 2:
                        args.putString("difficulty", "Intermediate Submodule");
                        args.putString("wpm", "250");
                        navController.navigate(moduleViewModel.getModule_id(), args);
                        break;
                    case 3:
                        args.putString("difficulty", "Advanced Submodule");
                        args.putString("wpm", "500");
                        navController.navigate(moduleViewModel.getModule_id(), args);
                        break;
                }
            }
        });

        return root;
    }



}
