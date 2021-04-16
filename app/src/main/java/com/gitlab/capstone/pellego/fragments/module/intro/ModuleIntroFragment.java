package com.gitlab.capstone.pellego.fragments.module.intro;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModelFactory;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.List;

/***************************************************
 *  Chris Bordoy and Eli Hebdon
 *
 *  The Module Introduction Fragment
 **************************************************/

public class ModuleIntroFragment extends BaseFragment {
    private ModuleViewModel moduleViewModel;
    private Button btn_register;
    private SharedPreferences sharedPref;
    private int totalPageCount;
    private String mID;
    private String submoduleID;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        System.out.println("DEBUG: In the Module Intro Fragment OnCreate");
        List<SMResponse> submoduleResponses = getArguments()
                .getParcelableArrayList("subModules");
        mID = getArguments().getString("moduleID");
        submoduleID = getArguments().getString("smID");

        moduleViewModel =
                new ViewModelProvider(
                        requireActivity(),
                        new ModuleViewModelFactory(
                                getActivity().getApplication(),
                                mID)).
                        get(ModuleViewModel.class);

        moduleViewModel.setSubModuleID(submoduleID);

        int[] colors = moduleViewModel.getModuleGradientColors(mID);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(colors[1]);
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(colors[1]);

        View root = inflater.inflate(R.layout.fragment_module_intro, container, false);

        DotsIndicator dotsIndicator = root.findViewById(R.id.dots_indicator);
        ViewPager2 viewPager2 = root.findViewById(R.id.view_pager);

        btn_register = root.findViewById(R.id.intro_finish_btn);

        viewPager2.setBackground(moduleViewModel.getGradient());

        //set data
        ModuleIntroPagerAdapter pagerAdapter = new ModuleIntroPagerAdapter(
                getContext(),
                submoduleResponses,
                moduleViewModel);
        viewPager2.setAdapter(pagerAdapter);
        totalPageCount = pagerAdapter.getItemCount();

        //Sets button visibility on the last page of the introduction slides
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == totalPageCount - 1) {
                    GradientDrawable gradientDrawable = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, //set a gradient direction
                            moduleViewModel.getModuleGradientColors()); //set the color of gradient
                    gradientDrawable.setCornerRadius(20f);
                    btn_register.setBackground(gradientDrawable);
                    btn_register.setVisibility(View.VISIBLE);
                    btn_register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            NavController navController = Navigation.findNavController(getActivity(),
                                    R.id.nav_host_fragment);
                            moduleViewModel.setSubModuleCompletion(mID, submoduleID);
                            // Store results in shared preference
                            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            updateModuleProgress();
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean(moduleViewModel.getTechnique() + "_intro_complete", true);
                            editor.apply();
                            navController.navigateUp();
                            return;
                        }
                    });
                } else {
                    btn_register.setVisibility(View.INVISIBLE);
                }
            }
        });

        dotsIndicator.setViewPager2(viewPager2);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        return root;
    }

    public void updateModuleProgress() {
        String key = moduleViewModel.getTechnique() + "_submodule_progress";
        int count = sharedPref.getInt(key, 0);
        // Store results in shared preference
        boolean complete = sharedPref.getBoolean(moduleViewModel.getTechnique() + "_intro_complete", false);
        if (!complete) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(key, ++count);
            editor.apply();
        }
    }
}