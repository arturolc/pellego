package com.gitlab.capstone.pellego.fragments.learn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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
    int[] gradients = new int[] {0xFFF9D976, 0xFFF39F86, 0xFF20BF55, 0xFF01BAEF, 0xFFF53844, 0xFF42378F, 0xFF37D5D6, 0xFF36096D};
    // yellow, blue, grey, redblue,
    int idx;

    public ModuleCardAdapter(Context context, ArrayList<ModuleListItemModel> navItems) {
        mContext = context;
        mNavItems = navItems;
        idx = 0;
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

        if (idx == gradients.length) idx = 0;
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
        TextView descr = (TextView) view.findViewById(R.id.description);


        titleView.setText( mNavItems.get(position).getTitle() );
        subtitleView.setText( mNavItems.get(position).getSubtitle() );
        iconView.setImageDrawable(mNavItems.get(position).getIcon() );
        descr.setText( mNavItems.get(position).getDescription() );

        return view;
    }
}