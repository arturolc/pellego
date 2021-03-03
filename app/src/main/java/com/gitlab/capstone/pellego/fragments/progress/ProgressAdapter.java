package com.gitlab.capstone.pellego.fragments.progress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.Storage;

import java.util.ArrayList;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {
    private static final String TAG = "ProgressAdapter";

    private ArrayList<ProgressResultsModel> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public ViewHolder(View v) {
            super(v);
            textView1 = (TextView) v.findViewById(R.id.words_read_list_item);
            textView2 = (TextView) v.findViewById(R.id.wpm_list_item);
            textView3 = (TextView) v.findViewById(R.id.date_list_item);
        }
    }

    public ProgressAdapter(ArrayList<ProgressResultsModel> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.progress_results_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.textView1.setText(String.valueOf(mDataSet.get(position).getWordsRead()));
        viewHolder.textView2.setText(String.valueOf(mDataSet.get(position).getWpm()));
        viewHolder.textView3.setText(mDataSet.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
/*    private LayoutInflater inflater;
    private List<ProgressResultsModel> progressList;

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }

    public ProgressAdapter(Context context, List<ProgressResultsModel> progressList) {
        inflater = LayoutInflater.from(context);
        this.progressList = progressList;
    }

    public int getCount() {
        return progressList.size();
    }

    public ProgressResultsModel getItem(int position) {
        return progressList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.progress_results_list_item, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.wpm_list_item);
            holder.textView2 = (TextView) convertView.findViewById(R.id.words_read_list_item);
            holder.textView3 = (TextView) convertView.findViewById(R.id.date_list_item);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView1.setText(progressList.get(position).getWordsRead());
        holder.textView1.setText(progressList.get(position).getWpm());
        holder.textView1.setText(progressList.get(position).getDate());
        //ProgressResultsModel progressResults = progressList.get(position);
        return convertView;
    }*/
}
