package com.gitlab.capstone.pellego.fragments.module.intro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


/***************************************************
 *  Chris Bordoy
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_module_intro, container, false);

        dotsIndicator = root.findViewById(R.id.dots_indicator);
        viewPager2 = root.findViewById(R.id.view_pager);
        parent_view = root.findViewById(R.id.parent_view);

        btn_register = root.findViewById(R.id.rsvp_intro_finish_btn);
        btn_register.setBackground(getResources().getDrawable(R.drawable.rounded_background));
        btn_register.setBackgroundColor(moduleViewModel.getGradient()[0]);

        //set data
        ModuleIntroPagerAdapter pagerAdapter = new ModuleIntroPagerAdapter();
        pagerAdapter.setContentAndHeaders(moduleViewModel,
                getResources());
        viewPager2.setAdapter(pagerAdapter);

        //Sets button visibility on the last page of the introduction slides
        int totalPageCount = pagerAdapter.getItemCount();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == totalPageCount - 1) {
                    btn_register.setVisibility(View.VISIBLE);
                    btn_register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            NavController navController = Navigation.findNavController(getActivity(),
                                    R.id.nav_host_fragment);
                            // TODO: update DB that intro was completed
                            // Store results in shared preference
                            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean(moduleViewModel.getTechnique() + "_intro_complete", true);
                            editor.apply();
                            updateModuleProgress();
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
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, ++count);
        editor.apply();
    }
}