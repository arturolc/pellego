package com.gitlab.capstone.pellego.fragments.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.axet.androidlibrary.net.HttpClient;
import com.github.axet.androidlibrary.widgets.CacheImagesAdapter;
import com.github.axet.androidlibrary.widgets.CacheImagesRecyclerAdapter;
import com.github.axet.androidlibrary.widgets.TextMax;
import com.gitlab.capstone.pellego.R;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class BooksAdapter extends CacheImagesRecyclerAdapter<BooksAdapter.BookHolder> {
    String filter;
    FragmentHolder holder;
    HttpClient client = new HttpClient(); // images client

    public static class BookHolder extends RecyclerView.ViewHolder {
        TextView aa;
        TextView tt;
        ImageView image;
        ProgressBar progress;

        public BookHolder(View itemView) {
            super(itemView);
            aa = (TextView) itemView.findViewById(R.id.book_authors);
            tt = (TextView) itemView.findViewById(R.id.book_title);
            image = (ImageView) itemView.findViewById(R.id.book_cover);
            progress = (ProgressBar) itemView.findViewById(R.id.book_progress);
        }
    }

    public BooksAdapter(Context context, FragmentHolder holder) {
        super(context);
        this.holder = holder;
    }

    public Uri getCover(int position) {
        return null;
    }

    public String getAuthors(int position) {
        return "";
    }

    public String getTitle(int position) {
        return "";
    }

    public void refresh() {
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return -1;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View convertView = inflater.inflate(viewType, parent, false);
        return new BooksAdapter.BookHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final BookHolder h, int position) {
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.clickListener != null)
                    holder.clickListener.onItemClick(null, v, h.getAdapterPosition(), -1);
            }
        });
        h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.longClickListener != null)
                    holder.longClickListener.onItemLongClick(null, v, h.getAdapterPosition(), -1);
                return true;
            }
        });
        setText(h.aa, getAuthors(position));
        setText(h.tt, getTitle(position));
    }

    @Override
    public Bitmap downloadImage(Uri cover, File f) throws IOException {
        HttpClient.DownloadResponse w = client.getResponse(null, cover.toString());
        FileOutputStream out = new FileOutputStream(f);
        IOUtils.copy(w.getInputStream(), out);
        w.getInputStream().close();
        out.close();
        Bitmap bm = CacheImagesAdapter.createScaled(new FileInputStream(f));
        FileOutputStream os = new FileOutputStream(f);
        bm.compress(Bitmap.CompressFormat.PNG, 100, os);
        os.close();
        return bm;
    }

    @Override
    public void downloadTaskUpdate(CacheImagesAdapter.DownloadImageTask task, Object item, Object view) {
       BookHolder h = new BookHolder((View) view);
        updateView(task, h.image, h.progress);
    }

    @Override
    public Bitmap downloadImageTask(CacheImagesAdapter.DownloadImageTask task) {
        Uri u = (Uri) task.item;
        return downloadImage(u);
    }

    void setText(TextView t, String s) {
        if (t == null)
            return;
        TextMax m = null;
        if (t.getParent() instanceof TextMax)
            m = (TextMax) t.getParent();
        ViewParent p = t.getParent();
        if (s == null || s.isEmpty()) {
            t.setVisibility(View.GONE);
            if (m != null)
                m.setVisibility(View.GONE);
            return;
        }
        t.setVisibility(View.VISIBLE);
        t.setText(s);
        if (m != null)
            m.setVisibility(View.VISIBLE);
    }
}