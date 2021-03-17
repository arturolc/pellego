package com.gitlab.capstone.pellego.fragments.module.intro;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;

/***********************************************
 *  Chris Bordoy
 *
 *  The Module Intro Pager Adapter
 */
public class ModuleIntroPagerAdapter extends RecyclerView.Adapter<ModuleIntroPagerAdapter.ModuleIntroViewHolder> {

    //color and icons
    private Resources res;
    private String[] content;
    private String[] headers;
    private int[] bkg_colors;
    int[][] color_icon_matrix = new int[][] {
            {R.color.pastel_blue},
            {R.color.pastel_purple},
            {R.color.pastel_green},
            {R.color.pastel_orange}
    };


    @NonNull
    @Override
    public ModuleIntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModuleIntroViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));
    }

    public void setContentAndHeaders(ModuleViewModel moduleViewModel, Resources res) {
        bkg_colors = moduleViewModel.getGradient();
        this.content = res.getStringArray(moduleViewModel.getIntro_content_id());
        this.headers = res.getStringArray(moduleViewModel.getIntro_header_id());
        this.res = res;
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleIntroViewHolder holder, int position) {
        holder.header_text_view.setText(headers[position]);
        holder.description_text_view.setText(content[position]);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {bkg_colors[0],bkg_colors[1]});
        gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        holder.container.setBackgroundDrawable(gd);
    }

    @Override
    public int getItemCount() {
        return color_icon_matrix.length;
    }

    public class ModuleIntroViewHolder extends RecyclerView.ViewHolder {
        TextView header_text_view;
        TextView description_text_view;
        ConstraintLayout container;

        public ModuleIntroViewHolder(@NonNull View itemView) {
            super(itemView);
            header_text_view = (TextView)itemView.findViewById(R.id.header_text_view);
            description_text_view = (TextView)itemView.findViewById(R.id.description_text_view);
            container = (ConstraintLayout)itemView.findViewById(R.id.item_page_container);
        }
    }
}
