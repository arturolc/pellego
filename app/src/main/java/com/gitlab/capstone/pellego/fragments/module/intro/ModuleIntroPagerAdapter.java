package com.gitlab.capstone.pellego.fragments.module.intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.network.models.SMResponse;

import java.util.List;

/***********************************************
 *  Chris Bordoy and Eli Hebdon
 *
 *  The Module Intro Pager Adapter
 ***********************************************/

public class ModuleIntroPagerAdapter extends RecyclerView.Adapter<ModuleIntroPagerAdapter.ModuleIntroViewHolder> {

    Context mContext;
    private final List<SMResponse> responses;
    private final ModuleViewModel moduleViewModel;

    public ModuleIntroPagerAdapter(Context context, List<SMResponse> responses, ModuleViewModel moduleViewModel) {
        this.mContext = context;
        this.responses = responses;
        this.moduleViewModel = moduleViewModel;
    }

    @NonNull
    @Override
    public ModuleIntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModuleIntroViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleIntroViewHolder holder, int position) {
        holder.header_text_view.setText(responses.get(0).getIntroContent().get(position).getHeader());
        holder.description_text_view.setText(responses.get(0).getIntroContent().get(position).getContent());
        holder.container.setBackground(moduleViewModel.getGradient());
        holder.header_text_view.setTextColor(App.getAppResources().getColor(R.color.gray_card));
        holder.description_text_view.setPadding(5, 5, 5, 5);
        holder.description_text_view.setTextColor(App.getAppResources().getColor(R.color.gray_card));
    }

    @Override
    public int getItemCount() {
        return responses.get(0).getIntroContent().size();
    }

    public static class ModuleIntroViewHolder extends RecyclerView.ViewHolder {
        TextView header_text_view;
        TextView description_text_view;
        ConstraintLayout container;

        public ModuleIntroViewHolder(@NonNull View itemView) {
            super(itemView);
            header_text_view = itemView.findViewById(R.id.header_text_view);
            description_text_view = itemView.findViewById(R.id.description_text_view);
            container = itemView.findViewById(R.id.item_page_container);
        }
    }
}
