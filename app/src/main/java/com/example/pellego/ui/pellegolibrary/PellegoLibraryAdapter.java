package com.example.pellego.ui.pellegolibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pellego.R;

public class PellegoLibraryAdapter extends RecyclerView.Adapter<PellegoLibraryAdapter.MyViewHolder> {

    String s1[], s2[];
    int img[];
    Context context;

    public PellegoLibraryAdapter(String[] s1, String[] s2, Context context) {
        this.s1 = s1;
        this.s2 = s2;
        this.img = img;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.library_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bookName.setText(s1[position]);
        holder.bookAuthor.setText(s2[position]);
        holder.imgView.setImageResource(R.drawable.harry_potter);
    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView bookName, bookAuthor;
        private ImageView imgView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            imgView = itemView.findViewById(R.id.imageView2);
        }
    }
}
