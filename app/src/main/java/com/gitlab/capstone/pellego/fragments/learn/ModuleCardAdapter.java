package com.gitlab.capstone.pellego.fragments.learn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleListItemModel;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Adapter to populate learning module gridview with module items
 **********************************************/

public class ModuleCardAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ModuleListItemModel> mNavItems;

    public ModuleCardAdapter(Context context, ArrayList<ModuleListItemModel> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
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
            view = inflater.inflate(R.layout.layout_module_card, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView descr = (TextView) view.findViewById(R.id.description);


        titleView.setText( mNavItems.get(position).getTitle() );
        subtitleView.setText( mNavItems.get(position).getSubtitle() );
        iconView.setImageResource(mNavItems.get(position).getIcon() );
        descr.setText( mNavItems.get(position).getDescription() );

        return view;
    }
}