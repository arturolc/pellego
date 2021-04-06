package com.gitlab.capstone.pellego.fragments.library;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.github.axet.androidlibrary.widgets.CacheImagesAdapter;
import com.github.axet.androidlibrary.widgets.SearchView;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LibraryAdapter extends BooksAdapter {
    ArrayList<Storage.Book> all = new ArrayList<>();
    ArrayList<Storage.Book> list = new ArrayList<>();
    Storage storage;
    Context context;

    public LibraryAdapter(LibraryFragment.FragmentHolder holder, Context context) {
        super(context, holder);
        this.context = context;
        this.storage = new Storage(context);
    }

    @Override
    public int getItemViewType(int position) {
        return holder.layout;
    }

    @Override
    public String getAuthors(int position) {
        Storage.Book b = list.get(position);
        return b.info.authors;
    }

    @Override
    public String getTitle(int position) {
        Storage.Book b = list.get(position);
        return b.info.title;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Storage.Book getItem(int position) {
        return list.get(position);
    }

    public void load() {
        all = storage.list();
    }

    public boolean hasBookmarks() {
        for (Storage.Book b : all) {
            if (b.info.bookmarks != null)
                return true;
        }
        return false;
    }

    public void delete(Storage.Book b) {
        all.remove(b);
        int i = list.indexOf(b);
        list.remove(i);
        notifyItemRemoved(i);
    }

    public void refresh() {
        list.clear();
        if (filter == null || filter.isEmpty()) {
            list = new ArrayList<>(all);
            clearTasks();
        } else {
            for (Storage.Book b : all) {
                if (SearchView.filter(filter, Storage.getTitle(b.info)))
                    list.add(b);
            }
        }
        ((Activity)context).findViewById(R.id.nav_host_fragment);
        sort();
    }

    public void sort() {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());
        int selected = getContext().getResources().getIdentifier(shared.getString(App.PREFERENCE_SORT, getContext().getResources().getResourceEntryName(R.id.sort_add_ask)), "id", getContext().getPackageName());
        if (selected == R.id.sort_name_ask) {
            Collections.sort(list, new LibraryFragment.ByName());
        } else if (selected == R.id.sort_name_desc) {
            Collections.sort(list, Collections.reverseOrder(new LibraryFragment.ByName()));
        } else if (selected == R.id.sort_add_ask) {
            Collections.sort(list, new LibraryFragment.ByCreated());
        } else if (selected == R.id.sort_add_desc) {
            Collections.sort(list, Collections.reverseOrder(new LibraryFragment.ByCreated()));
        } else if (selected == R.id.sort_open_ask) {
            Collections.sort(list, new LibraryFragment.ByRecent());
        } else if (selected == R.id.sort_open_desc) {
            Collections.sort(list, Collections.reverseOrder(new LibraryFragment.ByRecent()));
        } else {
            Collections.sort(list, new LibraryFragment.ByCreated());
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final BookHolder h, int position) {
        super.onBindViewHolder(h, position);

        Storage.Book b = list.get(position);

        View convertView = h.itemView;

        if (b.cover == null || !b.cover.exists()) {
            downloadTask(b, convertView);
        } else {
            downloadTaskClean(convertView);
            downloadTaskUpdate(null, b, convertView);
        }
    }

    @Override
    public Bitmap downloadImageTask(CacheImagesAdapter.DownloadImageTask task) {
        Process.setThreadPriority(Process.THREAD_PRIORITY_LOWEST);
        Storage.Book book = (Storage.Book) task.item;
        Storage.FBook fbook = null;
        try {
            fbook = storage.read(book);
            File cover = Storage.coverFile(getContext(), book);
            if (!cover.exists() || cover.length() == 0)
                storage.createCover(fbook, cover);
            book.cover = cover;
            try {
                Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(cover));
                return bm;
            } catch (IOException e) {
                cover.delete();
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Unable to load cover", e);
        } finally {
            if (fbook != null)
                fbook.close();
        }
        return null;
    }


    @Override
    public void downloadTaskUpdate(CacheImagesAdapter.DownloadImageTask task, Object item, Object view) {
        super.downloadTaskUpdate(task, item, view);
        BookHolder h = new BookHolder((View) view);
        Storage.Book b = (Storage.Book) item;
        if (b.cover != null && b.cover.exists()) {
            try {
                Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(b.cover));
                h.image.setImageBitmap(bm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}