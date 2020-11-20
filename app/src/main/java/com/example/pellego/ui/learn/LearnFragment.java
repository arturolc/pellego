package com.example.pellego.ui.learn;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.pellego.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**********************************************
    Chris Bordoy

    The Learn Modules Component
 **********************************************/
public class LearnFragment extends Fragment {

    private LearnViewModel learnViewModel;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mDrawerLayout;

    ArrayList<ModuleItem> mNavItems = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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

        mNavItems.add(new ModuleItem("Rapid Serial Visualization", "10 minues, 3 lessons", R.drawable.ic_rsv));
        mNavItems.add(new ModuleItem("Clump Reading", "5 minutes, 2 lessons", R.drawable.ic_clump_reading));
        mNavItems.add(new ModuleItem("Reducing Subvocalization", "5 minutes, 3 lessons", R.drawable.ic_reducing_subvocalization));
        mNavItems.add(new ModuleItem("Meta Guiding", "6 minutes, 3 lessons", R.drawable.ic_meta_guiding));
        mNavItems.add(new ModuleItem("Pre-Reading", "4 minutes, 3 lessons", R.drawable.ic_pre_reading));
        mDrawerLayout = root.findViewById(R.id.nav_module_overview);
//
//        // Populate the Navigtion Drawer with options
        mDrawerPane = root.findViewById(R.id.module_pane);
        mDrawerList = root.findViewById(R.id.navList);
        ModuleListAdapter adapter = new ModuleListAdapter(getContext(), mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectItemFromDrawer(position);
//            }
//        });

        return root;
    }
}