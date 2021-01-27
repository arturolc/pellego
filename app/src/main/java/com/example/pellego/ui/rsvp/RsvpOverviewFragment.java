package com.example.pellego.ui.rsvp;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pellego.R;
import com.example.pellego.ui.learn.ModuleViewModel;
import com.example.pellego.ui.learn.ModuleItemModel;
import com.example.pellego.ui.learn.ModuleListAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Rapid Serial Visual Representation Overview fragment that
 displays the technique submodules and gives a brief overview
 of the technique.
 **********************************************/

public class RsvpOverviewFragment extends Fragment  {

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
                textView.setText(R.string.title_rsvp);
            }
        });
        mNavItems = new ArrayList<>();
        // Add nav items to the list of submodules
        mNavItems.add(new ModuleItemModel("Intro", "Learn the benefits of RSVP", R.drawable.ic_checked_circle));
        mNavItems.add(new ModuleItemModel("Beginner", "Demonstrate basic skills", R.drawable.ic_empty_circle));
        mNavItems.add(new ModuleItemModel("Intermediate", "Show some improvement", R.drawable.ic_empty_circle));
        mNavItems.add(new ModuleItemModel("Advanced", "Prove your mastery", R.drawable.ic_empty_circle));
        TextView rsvDescription = (TextView) root.findViewById(R.id.text_module_description);
        rsvDescription.setText(R.string.description_rsvp);

        // Populate the Navigation menu with options
        moduleList = root.findViewById(R.id.nav_module_list);
        ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);
        RsvpOverviewFragment rsvpOverviewFragment = new RsvpOverviewFragment();
        modulesView = root.findViewById(R.id.nav_module_overview);
        modulesView.setVisibility(View.VISIBLE);

        // menu Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                RsvpModuleFragment rsvpModuleFragment = new RsvpModuleFragment();

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                Bundle args = new Bundle();
                moduleViewModel.showDialog = true;
                switch(position) {
                    case 0:
                        navController.navigate(R.id.nav_rsvp_intro);
                        break;
                    case 1:
                        args.putString("difficulty", "Beginner Submodule");
                        args.putString("wpm", "120");
                        navController.navigate(R.id.nav_rsvp_module, args);
                        break;
                    case 2:
                        args.putString("difficulty", "Intermediate Submodule");
                        args.putString("wpm", "250");
                        rsvpModuleFragment.setArguments(args);
                        fragmentTransaction.replace(R.id.host_fragment_container, rsvpModuleFragment, "RsvpModuleFragment");
                        break;
                    case 3:
                        args.putString("difficulty", "Advanced Submodule");
                        args.putString("wpm", "500");
                        rsvpModuleFragment.setArguments(args);
                        fragmentTransaction.replace(R.id.host_fragment_container, rsvpModuleFragment, "RsvpModuleFragment");
                        break;
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        
        return root;
    }


}