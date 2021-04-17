package com.gitlab.capstone.pellego.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.gitlab.capstone.pellego.R;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

/**
 * Joanna Lowry
 * Activity for the Onboarding Tutorial
 */
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

    /**
     * When the tutorial is finished it updates the SharedPreference to make sure users only go thought it once
     */
    private void finishOnboarding() {
        SharedPreferences preferences =
                getSharedPreferences("my_preferences", MODE_PRIVATE);

        preferences.edit()
                .putBoolean("onboarding_complete", true).apply();

        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);

        finish();
    }

    /**
     * By Joanna Lowry
     * Adapter class used for the PageView in OnboardingActivity
     */
    public class OnboardingPagerAdapter extends RecyclerView.Adapter<com.gitlab.capstone.pellego.activities.OnboardingActivity.OnboardingPagerAdapter.OnboardingViewHolder> {

        private Resources res;
        private String[] content;
        private String[] headers;


        public OnboardingPagerAdapter() {
            res = getResources();
            this.content = res.getStringArray(R.array.onboarding_content);
            this.headers = res.getStringArray(R.array.onboarding_header);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @NonNull
        @Override
        public com.gitlab.capstone.pellego.activities.OnboardingActivity.OnboardingPagerAdapter.OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OnboardingPagerAdapter.OnboardingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
            holder.header_text_view.setText(headers[position]);
            holder.description_text_view.setText(content[position]);
            int[] colors = new int[]{0xEE3C5AEB, 0x774D5656};

            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd.setCornerRadius(20f);

            holder.container.setBackground(gd);
            
            if(position == 0) //The first slide diplays a little big, so let's adjust the fontsize a little
            {
                Typeface header_face = res.getFont(R.font.playfair);
                holder.description_text_view.setTypeface(header_face);
                holder.description_text_view.setTextSize((float) (holder.description_text_view.getTextSize() * .5));
                holder.header_text_view.setTextSize((float)holder.header_text_view.getTextSize() + 5);
            }

        }

        @Override
        public int getItemCount() {
            return totalPageCount;
        }

        /**
         *  Holder class used in the Adapter class, which is used in the OnboardingActivity to make the PageView work
         */
        public class OnboardingViewHolder extends RecyclerView.ViewHolder {
            TextView header_text_view;
            TextView description_text_view;
            ConstraintLayout container;

            @RequiresApi(api = Build.VERSION_CODES.O)
            public OnboardingViewHolder(@NonNull View itemView) {
                super(itemView);
                header_text_view = itemView.findViewById(R.id.header_text_view);
                Typeface header_face = res.getFont(R.font.playfair);
                header_text_view.setTypeface(header_face);
                description_text_view = itemView.findViewById(R.id.description_text_view);

                container = itemView.findViewById(R.id.item_page_container);
            }
        }
    }

}
