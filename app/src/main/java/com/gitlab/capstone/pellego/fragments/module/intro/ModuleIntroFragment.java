package com.gitlab.capstone.pellego.fragments.module.intro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleListAdapter;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleOverviewFragment;
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
    RelativeLayout parent_view;
    ViewPager2 viewPager2;
    DotsIndicator dotsIndicator;
    Button btn_register;
    private SharedPreferences sharedPref;
    int totalPageCount;
    List<SMResponse> submoduleResponses;
    GradientDrawable gd;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        submoduleResponses = getArguments().getParcelableArrayList("subModules");

/*        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(null);*/
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(0xFFF9D976);
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFFF9D976);

        moduleViewModel =
                new ViewModelProvider(
                        requireActivity(),
                        new ModuleViewModelFactory(
                                getActivity().getApplication(),
                                getArguments().getString("moduleID"))).
                        get(ModuleViewModel.class);

        View root = inflater.inflate(R.layout.fragment_module_intro, container, false);

        dotsIndicator = root.findViewById(R.id.dots_indicator);
        viewPager2 = root.findViewById(R.id.view_pager);
        parent_view = root.findViewById(R.id.parent_view);

        gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFF9D976, 0xFFF39f86});
        gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);

        btn_register = root.findViewById(R.id.intro_finish_btn);

        viewPager2.setBackgroundDrawable(gd);

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
                    btn_register.setBackground(moduleViewModel.getGradient());
                    btn_register.setVisibility(View.VISIBLE);
                    btn_register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            NavController navController = Navigation.findNavController(getActivity(),
                                    R.id.nav_host_fragment);
                            // TODO: update DB that intro was completed
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