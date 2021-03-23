package com.gitlab.capstone.pellego.fragments.module.overview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.network.models.SMResponse;

import java.util.List;

/**********************************************
 Eli Hebdon & Chris Bordoy

 Adapter to populate learning module list with module items
 **********************************************/

public class ModuleListAdapter extends BaseAdapter {

    Context mContext;
    ModuleOverviewFragment fragment;
    private List<SMResponse> responses;

    public ModuleListAdapter(Context context,
                             List<SMResponse> responses,
                             ModuleOverviewFragment fragment) {
        this.mContext = context;
        this.responses = responses;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return responses.size();
    }

    @Override
    public Object getItem(int position) {
        return responses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //LIST OF SUBMODULES USING THE MODULE PANE
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.submodule_item, null);
        }
        else {
            view = convertView;
        }

        //view.setBackground(mContext.getResources().getDrawable(R.drawable.orange_gradient));

        TextView titleView = view.findViewById(R.id.title);
        TextView subtitleView = view.findViewById(R.id.subTitle);
        ImageView iconView = view.findViewById(R.id.icon);

        SMResponse res = responses.get(position);

        titleView.setText(res.getName());
        subtitleView.setText(res.getSubheader());
        iconView.setImageDrawable(fragment.getDrawable(res.getName().toLowerCase()));

        return view;
    }
}