package com.gitlab.capstone.pellego.fragments.quiz;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;

import java.util.ArrayList;

/**********************************************
 Eli Hebdon & Chris Bordoy

 Adapter to populate quiz question list with module items
 **********************************************/

public class QuizAnswerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<QuizQuestionModel> mNavItems;
    Drawable color;

    public QuizAnswerListAdapter(Context context,
                                 ArrayList<QuizQuestionModel> mNavItems,
                                 Drawable color) {
        mContext = context;
        this.mNavItems = mNavItems;
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

        TextView titleView = view.findViewById(R.id.title);
        TextView iconView = view.findViewById(R.id.icon);
        iconView.setBackground(color);
        titleView.setText(mNavItems.get(position).mTitle);
        iconView.setText(mNavItems.get(position).mIcon);

        return view;
    }
}