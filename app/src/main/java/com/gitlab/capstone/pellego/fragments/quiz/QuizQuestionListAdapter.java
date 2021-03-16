package com.gitlab.capstone.pellego.fragments.quiz;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gitlab.capstone.pellego.R;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon

 Adapter to populate quiz question list with module items
 **********************************************/

public class QuizQuestionListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<QuizQuestionModel> mNavItems;
    int color;

    public QuizQuestionListAdapter(Context context, ArrayList<QuizQuestionModel> navItems, int color) {
        mContext = context;
        mNavItems = navItems;
        this.color = color;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.question_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView iconView = (TextView) view.findViewById(R.id.icon);
        iconView.setTextColor(color);
        titleView.setText( mNavItems.get(position).mTitle );
        iconView.setText(mNavItems.get(position).mIcon);

        return view;
    }
}