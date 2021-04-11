package com.gitlab.capstone.pellego.fragments.learn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.network.models.CompletionResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;

import java.util.List;

import static java.lang.String.*;

/*****************************************************
 * Arturo Lara & Chris Bordoy
 *
 * Adapter that sets data for the Learn Fragment
 *****************************************************/

public class LearnCardAdapter extends BaseAdapter {
    private final int[] completionResponse;
    private final List<LMResponse> response;
    private final Context mContext;

    Drawable[] gradientBackgrounds;
    int idx;

    @SuppressLint("UseCompatLoadingForDrawables")
    public LearnCardAdapter(Context context, int[] cresponse, List<LMResponse> response) {
        this.completionResponse = cresponse;
        this.response = response;
        this.mContext = context;
        gradientBackgrounds = new Drawable[] {
                mContext.getResources().getDrawable(R.drawable.orange_gradient),
                mContext.getResources().getDrawable(R.drawable.green_gradient),
                mContext.getResources().getDrawable(R.drawable.pink_gradient),
                mContext.getResources().getDrawable(R.drawable.blue_gradient),
        };
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

    @SuppressLint({"ResourceAsColor", "DefaultLocale", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.module_item,null);
        }
        else {
            view = convertView;
        }

        if (idx == 4) idx = 0;
        view.setBackground(gradientBackgrounds[idx]);
        idx ++;

        TextView titleView = view.findViewById(R.id.title);
        TextView subtitleView = view.findViewById(R.id.subTitle);
        ImageView iconView = view.findViewById(R.id.icon);
        TextView subHeaderView = view.findViewById(R.id.description);

        LMResponse res = response.get(position);
        int completionRes = completionResponse[position];

        titleView.setText(res.getName());
        subtitleView.setText(
                format("%d of %d submodules completed",
                completionRes,
                res.getTotalSubmodules()));
        Glide.with(mContext).load(res.getIcon()).into(iconView);
        subHeaderView.setText(res.getSubheader());

        return view;
    }
}
