package com.example.pellego.ui.learn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pellego.R;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Adapter to populate learning module list with module items
 **********************************************/

public class ModuleListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ModuleItemModel> mNavItems;

    public ModuleListAdapter(Context context, ArrayList<ModuleItemModel> navItems) {
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
            view = inflater.inflate(R.layout.module_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        subtitleView.setTextColor(R.color.black);

        titleView.setText( mNavItems.get(position).getTitle() );
        subtitleView.setText( mNavItems.get(position).getSubtitle() );
        iconView.setImageResource(mNavItems.get(position).getIcon() );

        return view;
    }
}