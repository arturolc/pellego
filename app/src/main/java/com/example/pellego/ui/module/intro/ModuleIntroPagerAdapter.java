package com.example.pellego.ui.module.intro;

import android.content.res.Resources;
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
public class ModuleIntroPagerAdapter extends RecyclerView.Adapter<ModuleIntroPagerAdapter.ModuleIntroViewHolder> {

    //color and icons
    private Resources res;
    private String[] content;
    private String[] headers;
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

    public void setContentAndHeaders(int headers, int content, Resources res) {
        this.content = res.getStringArray(content);
        this.headers = res.getStringArray(headers);
        this.res = res;
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleIntroViewHolder holder, int position) {
        holder.header_text_view.setText(headers[position]);
        holder.description_text_view.setText(content[position]);
        holder.container.setBackgroundResource(color_icon_matrix[position][0]);
    }

    @Override
    public int getItemCount() {
        return color_icon_matrix.length;
    }

    public class ModuleIntroViewHolder extends RecyclerView.ViewHolder {
        TextView header_text_view;
        TextView description_text_view;
        RelativeLayout container;

        public ModuleIntroViewHolder(@NonNull View itemView) {
            super(itemView);
            header_text_view = (TextView)itemView.findViewById(R.id.header_text_view);
            description_text_view = (TextView)itemView.findViewById(R.id.description_text_view);
            container = (RelativeLayout)itemView.findViewById(R.id.item_page_container);
        }
    }
}
