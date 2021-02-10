package com.example.pellego.ui.pellegolibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pellego.R;

/**********************************************
 Arturo Lara
 Adapter for Recycler view
 **********************************************/
public class PellegoLibraryAdapter extends RecyclerView.Adapter<PellegoLibraryAdapter.MyViewHolder> {

    String s1[], s2[], ids[];
    String imgs[];
    Context context;
    Fragment myFragment;

    public PellegoLibraryAdapter(String[] ids, String[] s1, String[] s2, String[] imgs, Context context, Fragment myFragment) {
        this.s1 = s1;
        this.s2 = s2;
        this.ids = ids;
        this.imgs = imgs;
        this.context = context;
        this.myFragment = myFragment;
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
        ImageView img = ((MyViewHolder) holder).imgView;
        Glide.with(myFragment).load(imgs[position]).into(img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, BookPreviewActivity.class);
                i.putExtra("id", ids[position]);
                i.putExtra("title", s1[position]);
                i.putExtra("author", s2[position]);
                i.putExtra("image", imgs[position]);
                context.startActivity(i);
            }
        });
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
