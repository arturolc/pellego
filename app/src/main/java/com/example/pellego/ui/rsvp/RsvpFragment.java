package com.example.pellego.ui.rsvp;

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
import com.example.pellego.ui.learn.LearnFragment;
import com.example.pellego.ui.learn.LearnViewModel;
import com.example.pellego.ui.learn.ModuleItemModel;
import com.example.pellego.ui.learn.ModuleListAdapter;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Rapid Serial Visualization fragment
 **********************************************/

public class RsvpFragment extends Fragment  {

    private RsvpViewModel techniqueOverviewViewModel;
    ArrayList<ModuleItemModel> mNavItems = new ArrayList<>();
    private LearnViewModel learnViewModel;
    private ListView moduleList;
    private RelativeLayout modulePane;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        techniqueOverviewViewModel =
                new ViewModelProvider(this).get(RsvpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rsvp, container, false);
        final TextView textView = root.findViewById(R.id.text_rsvp);
        techniqueOverviewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(R.string.title_rsvp);
            }
        });

        // Add nav items to the list of submodules
        mNavItems.add(new ModuleItemModel("Intro", "Learn the benefits of RSVP", R.drawable.ic_checked_circle));
        mNavItems.add(new ModuleItemModel("Beginner", "Demonstrate basic skills", R.drawable.ic_empty_circle));
        mNavItems.add(new ModuleItemModel("Intermediate", "Show some improvement", R.drawable.ic_empty_circle));
        mNavItems.add(new ModuleItemModel("Advanced", "Prove your mastery", R.drawable.ic_empty_circle));
        TextView rsvDescription = (TextView) root.findViewById(R.id.rsvp_description);
        rsvDescription.setText("   Rapid Serial Visual Presentation is a method in which individual words are displayed in rapid succession. The words are fixed to the same " +
                "point on the screen which eliminates the need for saccades. This means your eyes can remain fixed while the words change.");

        // Populate the Navigation menu with options
        modulePane = root.findViewById(R.id.module_pane);
        moduleList = root.findViewById(R.id.nav_module_list);
        ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
        moduleList.setAdapter(adapter);
        RsvpFragment rsvpFragment = new RsvpFragment();

        // menu Item click listeners
        moduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                // TODO: navigate to fragment based on click id
                fragmentTransaction.replace(R.id.host_fragment_container, new LearnFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ((HomeActivity) getActivity()).setActionBarIconArrow();
            }
        });


        return root;
    }


}