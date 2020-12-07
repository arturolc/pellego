package com.example.pellego.ui.rsvp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pellego.R;

public class RsvpIntroPagerAdapter extends RecyclerView.Adapter<RsvpIntroPagerAdapter.RsvpIntroViewHolder>{

    //color and icons
    int[][] color_icon_matrix = new int[][] {
            {android.R.color.holo_red_light, R.drawable.ic_arrow_back},
            {android.R.color.holo_blue_bright, R.drawable.ic_arrow_back},
            {android.R.color.darker_gray, R.drawable.ic_arrow_back},
            {android.R.color.holo_green_dark, R.drawable.ic_arrow_back}
    };

    @NonNull
    @Override
    public RsvpIntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RsvpIntroViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RsvpIntroViewHolder holder, int position) {
        holder.img_view.setImageResource(color_icon_matrix[position][1]);
        holder.container.setBackgroundResource(color_icon_matrix[position][0]);
    }

    @Override
    public int getItemCount() {
        return color_icon_matrix.length;
    }

    public class RsvpIntroViewHolder extends RecyclerView.ViewHolder {
        ImageView img_view;
        TextView text_view;
        RelativeLayout container;

        public RsvpIntroViewHolder(@NonNull View itemView) {
            super(itemView);
            img_view = (ImageView)itemView.findViewById(R.id.img_view);
            text_view = (TextView)itemView.findViewById(R.id.text_view);
            container = (RelativeLayout)itemView.findViewById(R.id.item_page_container);
        }
    }
}
