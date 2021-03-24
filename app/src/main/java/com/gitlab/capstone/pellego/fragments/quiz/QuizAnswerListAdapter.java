package com.gitlab.capstone.pellego.fragments.quiz;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.network.models.Answer;
import com.gitlab.capstone.pellego.network.models.QuizResponse;

import java.util.ArrayList;
import java.util.List;

/**********************************************
 Eli Hebdon

 Adapter to populate quiz question list with module items
 **********************************************/

public class QuizAnswerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<QuizQuestionModel> mNavItems;
    Drawable color;
    List<QuizResponse> quizResponses;
    int questionID;

    public QuizAnswerListAdapter(Context context,
                                 int questionID,
                                 ArrayList<QuizQuestionModel> mNavItems,
                                 List<QuizResponse> responses,
                                 Drawable color) {
        mContext = context;
        this.questionID = questionID;
        this.mNavItems = mNavItems;
        quizResponses = responses;
        this.color = color;
    }

    @Override
    public int getCount() {
        return quizResponses.size();
    }

    @Override
    public Object getItem(int position) {
        return quizResponses.get(position);
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
        iconView.setBackground(color);
        titleView.setText(quizResponses.get(questionID).getAnswers().get(position).getAnswer());
        //titleView.setText( mNavItems.get(position).mTitle );
        iconView.setText(mNavItems.get(position).mIcon);

        return view;
    }
/*
    private List<Answer> getAnswers(List<QuizResponse> quizResponses, int qid) {
        return quizResponses.get(0).getAnswers();
    }*/
}