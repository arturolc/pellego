package com.example.pellego.ui.learn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pellego.HomeActivity;
import com.example.pellego.R;
import com.example.pellego.ui.clumpReading.ClumpReadingFragment;
import com.example.pellego.ui.rsvp.RsvpOverviewFragment;

import java.util.ArrayList;

/**********************************************
    Chris Bordoy & Eli Hebdon

    The Learn Modules Component
 **********************************************/
public class LearnFragment extends Fragment {

    private LearnViewModel learnViewModel;
    private ListView moduleList;
    private RelativeLayout modulePane;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ArrayList<ModuleItemModel> mNavItems = new ArrayList<>();
        learnViewModel =
                new ViewModelProvider(this).get(LearnViewModel.class);

        View root = inflater.inflate(R.layout.fragment_learn, container, false);
        final TextView textView = root.findViewById(R.id.text_learn);
        learnViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Add nav items to the list of learning techniques
        mNavItems.add(new ModuleItemModel("Rapid Serial Visual Presentation", "1 out of 4 submodules completed", R.drawable.ic_rsvp));
        mNavItems.add(new ModuleItemModel("Clump Reading", "0 out of 4 submodules completed", R.drawable.ic_clump_reading));
        mNavItems.add(new ModuleItemModel("Reducing Subvocalization", "3 out of 4 submodules completed", R.drawable.ic_reducing_subvocalization));
        mNavItems.add(new ModuleItemModel("Meta Guiding", "2 out of 4 submodules completed", R.drawable.ic_meta_guiding));
        mNavItems.add(new ModuleItemModel("Pre-Reading", "1 out of 4 submodules completed", R.drawable.ic_pre_reading));
        // Populate the Navigation Drawer with options
        modulePane = root.findViewById(R.id.module_pane);
        moduleList = root.findViewById(R.id.nav_module_list);
        ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);

        // Drawer Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                // TODO: navigate to fragment based on click id
                switch(position) {
                    case 0:
                        fragmentTransaction.replace(R.id.host_fragment_container, new RsvpOverviewFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        ((HomeActivity) getActivity()).setActionBarIconArrow();
                        break;
                    case 1:
                        fragmentTransaction.replace(R.id.host_fragment_container, new ClumpReadingFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        ((HomeActivity) getActivity()).setActionBarIconArrow();
                        break;
                }

            }
        });

        return root;
    }

}