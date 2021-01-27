package com.example.pellego.ui.module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pellego.R;

/***********************************************
 *  Chris Bordoy
 *
 *  The Module Intro Pager Adapter
 */
public class ModuleIntroPagerAdapter extends RecyclerView.Adapter<ModuleIntroPagerAdapter.RsvpIntroViewHolder>{

    //color and icons
    int[][] color_icon_matrix = new int[][] {
            {R.color.pastel_blue},
            {R.color.pastel_purple},
            {R.color.pastel_green},
            {R.color.pastel_orange}
    };

    //Hard coded-text but can be populated from DB
    int[][] text = new int[][]{
            {R.string.rsvp_intro_header1, R.string.rsvp_intro_description1},
            {R.string.rsvp_intro_header2, R.string.rsvp_intro_description2},
            {R.string.rsvp_intro_header3, R.string.rsvp_intro_description3},
            {R.string.rsvp_intro_header4, R.string.rsvp_intro_description4}
    };

    @NonNull
    @Override
    public RsvpIntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RsvpIntroViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RsvpIntroViewHolder holder, int position) {
        holder.header_text_view.setText(text[position][0]);
        holder.description_text_view.setText(text[position][1]);
        holder.container.setBackgroundResource(color_icon_matrix[position][0]);
    }

    @Override
    public int getItemCount() {
        return color_icon_matrix.length;
    }

    //TODO: Add finish button to last slide that navigates back to RSVP submodules
    public class RsvpIntroViewHolder extends RecyclerView.ViewHolder {
        TextView header_text_view;
        TextView description_text_view;
        RelativeLayout container;

        public RsvpIntroViewHolder(@NonNull View itemView) {
            super(itemView);
            header_text_view = (TextView)itemView.findViewById(R.id.header_text_view);
            description_text_view = (TextView)itemView.findViewById(R.id.description_text_view);
            container = (RelativeLayout)itemView.findViewById(R.id.item_page_container);
        }
    }
}
