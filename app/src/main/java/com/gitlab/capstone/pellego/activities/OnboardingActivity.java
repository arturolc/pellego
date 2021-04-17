package com.gitlab.capstone.pellego.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.github.axet.androidlibrary.activities.AppCompatThemeActivity;
import com.gitlab.capstone.pellego.R;

import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.fragments.module.intro.ModuleIntroPagerAdapter;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleListAdapter;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleOverviewFragment;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModelFactory;
import com.gitlab.capstone.pellego.fragments.onboarding.OnboardingFragment;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    int totalPageCount = 4;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding);

        DotsIndicator dotsIndicator = findViewById(R.id.dots_indicator);
        ViewPager2 viewPager2 = findViewById(R.id.onboard_pager);

        OnboardingPagerAdapter pagerAdapter = new OnboardingPagerAdapter();
        viewPager2.setAdapter(pagerAdapter);
        int[] colors = new int[]{0xFF37D5D6, 0xFF9B6DFF};

        btn_register = findViewById(R.id.onboard_finish_btn);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == totalPageCount - 1) {
                    GradientDrawable gradientDrawable = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, //set a gradient direction
                            colors); //set the color of gradient
                    gradientDrawable.setCornerRadius(20f);
                    btn_register.setBackground(gradientDrawable);
                    btn_register.setVisibility(View.VISIBLE);
                    btn_register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            finish();
                        }
                    });
                } else {
                    btn_register.setVisibility(View.INVISIBLE);
                }
            }
        });

        dotsIndicator.setViewPager2(viewPager2);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, OnboardingActivity.class);
        context.startActivity(intent);

    }

    private void finishOnboarding() {
        SharedPreferences preferences =
                getSharedPreferences("my_preferences", MODE_PRIVATE);

        preferences.edit()
                .putBoolean("onboarding_complete", true).apply();

        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);

        finish();
    }

    public class OnboardingPagerAdapter extends RecyclerView.Adapter<com.gitlab.capstone.pellego.activities.OnboardingActivity.OnboardingPagerAdapter.OnboardingViewHolder> {

        private Resources res;
        private String[] content;
        private String[] headers;



        public OnboardingPagerAdapter() {
            res = getResources();
            this.content = res.getStringArray(R.array.onboarding_content);
            this.headers = res.getStringArray(R.array.onboarding_header);
        }


        @NonNull
        @Override
        public com.gitlab.capstone.pellego.activities.OnboardingActivity.OnboardingPagerAdapter.OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OnboardingPagerAdapter.OnboardingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
            holder.header_text_view.setText(headers[position]);
            holder.description_text_view.setText(content[position]);
        }

        @Override
        public int getItemCount() {
            return totalPageCount;
        }

        public class OnboardingViewHolder extends RecyclerView.ViewHolder {
            TextView header_text_view;
            TextView description_text_view;
            ConstraintLayout container;

            public OnboardingViewHolder(@NonNull View itemView) {
                super(itemView);
                header_text_view = itemView.findViewById(R.id.header_text_view);
                description_text_view = itemView.findViewById(R.id.description_text_view);
                container = itemView.findViewById(R.id.item_page_container);
            }
        }
    }

}
