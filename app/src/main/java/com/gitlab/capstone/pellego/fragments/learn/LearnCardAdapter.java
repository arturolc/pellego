package com.gitlab.capstone.pellego.fragments.learn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.network.models.LMResponse;

import java.util.List;

public class LearnCardAdapter extends BaseAdapter {
    private List<LMResponse> response;
    private Context mContext;

    int[] gradients = new int[] {0xFFF9D976, 0xFFF39F86, 0xFF20BF55, 0xFF01BAEF, 0xFFD2CCC4, 0xFF2F4353, 0xFFF53844, 0xFF42378F, 0xFF37D5D6, 0xFF36096D};
    // yellow, blue, grey, redblue,
    int idx;

    public LearnCardAdapter(Context context, List<LMResponse> response) {
        this.response = response;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return response.size();
    }

    @Override
    public Object getItem(int position) {
        return response.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.module_item, null);
        }
        else {
            view = convertView;
        }

        if (idx == 10) idx = 0;
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {gradients[idx],gradients[idx + 1]});
        gd.setCornerRadius(45f);
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        idx += 2;
        view.setBackgroundDrawable(gd);

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView subheader = (TextView) view.findViewById(R.id.description);

        LMResponse res = response.get(position);
        titleView.setText( res.getName() );
        subtitleView.setText( res.getCompleted() + " of " +
                res.getTotalSubmodules() + " submodules completed");
        Glide.with(mContext).load(res.getIcon()).into(iconView);
        subheader.setText(res.getSubheader());

        return view;
    }
}
