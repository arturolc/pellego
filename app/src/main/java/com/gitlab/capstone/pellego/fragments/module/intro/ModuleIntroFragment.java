package com.gitlab.capstone.pellego.fragments.module.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


/***************************************************
 *  Chris Bordoy
 *
 *  The Module Introduction Fragment
 **************************************************/
public class ModuleIntroFragment extends Fragment {
    private ModuleViewModel moduleViewModel;
    RelativeLayout parent_view;
    ViewPager2 viewPager2;
    DotsIndicator dotsIndicator;
    Button btn_register;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_module_intro, container, false);

        dotsIndicator = root.findViewById(R.id.dots_indicator);
        viewPager2 = root.findViewById(R.id.view_pager);
        parent_view = root.findViewById(R.id.parent_view);

        btn_register = root.findViewById(R.id.rsvp_intro_finish_btn);

        //set data
        ModuleIntroPagerAdapter pagerAdapter = new ModuleIntroPagerAdapter();
        pagerAdapter.setContentAndHeaders(moduleViewModel.getIntro_header_id(),
                moduleViewModel.getIntro_content_id(),
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
}