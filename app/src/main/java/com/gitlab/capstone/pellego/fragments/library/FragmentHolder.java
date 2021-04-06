package com.gitlab.capstone.pellego.fragments.library;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;

public class FragmentHolder {
    RecyclerView grid;
    View import_button;
    public int layout;
    private int scrolledDistance = 0;
    private static final int HIDE_THRESHOLD = 20;
    private boolean controlsVisible = true;
    private TextView title;
    private final String TAG = this.getClass().getName();

    View header;
    View toolbar;
    View searchpanel;
    LinearLayout searchtoolbar;
    View footer;
    View footerButtons;
    View footerNext;
    View footerProgress;
    View footerStop;

    Context context;
    AdapterView.OnItemClickListener clickListener;
    AdapterView.OnItemLongClickListener longClickListener;

    public FragmentHolder(Context context) {
        this.context = context;
        this.header = ((Activity)context).findViewById(R.id.header_circular);
    }

    public void create(View v) {
        grid = (RecyclerView) v.findViewById(R.id.grid);
        import_button = v.findViewById(R.id.button_import_book);
        // DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        // grid.addItemDecoration(divider);

        LayoutInflater inflater = LayoutInflater.from(context);
        title = (TextView) v.findViewById(R.id.title_library);
        grid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrolledDistance = dy;
                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    hideViews();
                    controlsVisible = false;
                } else if (!grid.canScrollVertically(-1) && !controlsVisible) {
                    showViews();
                    controlsVisible = true;
                }
            }
        });
        toolbar = v.findViewById(R.id.search_header_toolbar_parent);
        searchpanel = v.findViewById(R.id.search_panel);
        searchtoolbar = (LinearLayout) v.findViewById(R.id.search_header_toolbar);
        toolbar.setVisibility(View.GONE);

        footer = inflater.inflate(R.layout.library_footer, null);
        footerButtons = footer.findViewById(R.id.search_footer_buttons);
        footerNext = footer.findViewById(R.id.search_footer_next);
        footerProgress = footer.findViewById(R.id.search_footer_progress);
        footerStop = footer.findViewById(R.id.search_footer_stop);


        footerNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "footer next");
            }
        });

        addFooterView(footer);

        updateGrid();
    }

    private void hideViews() {
        title.animate().translationY(-title.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        header.animate().translationY(-header.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        import_button.animate().translationY(import_button.getHeight() * 2).setInterpolator(new AccelerateInterpolator(2));

    }

    private void showViews() {
        title.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        header.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        import_button.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    public String getLayout() {
        return "library";
    }

    public void updateGrid() {
        final SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        if (shared.getString(App.PREFERENCE_LIBRARY_LAYOUT + getLayout(), "").equals("book_list_item")) {
            setNumColumns(1);
            layout = R.layout.book_list_item;
        } else {
            setNumColumns(3);
            layout = R.layout.book_item;
        }
    }

    public void onCreateOptionsMenu(Menu menu) {
        MenuItem grid = menu.findItem(R.id.action_grid);

        updateGrid();

        if (layout == R.layout.book_item)
            grid.setIcon(R.drawable.ic_view_module_black_24dp);
        else
            grid.setIcon(R.drawable.ic_view_list_black_24dp);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        final SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        int id = item.getItemId();
        if (id == R.id.action_grid) {
            SharedPreferences.Editor editor = shared.edit();
            if (layout == R.layout.book_list_item)
                editor.putString(App.PREFERENCE_LIBRARY_LAYOUT + getLayout(), "book_item");
            else
                editor.putString(App.PREFERENCE_LIBRARY_LAYOUT + getLayout(), "book_list_item");
            editor.commit();
            updateGrid();
            return true;
        }
        return false;
    }

    public void addFooterView(View v) {
    }

    public void setNumColumns(int i) {
        LinearLayoutManager reset = null;
        if (i == 1) {
            LinearLayoutManager lm = new LinearLayoutManager(context);
            RecyclerView.LayoutManager l = grid.getLayoutManager();
            if (l == null || l instanceof GridLayoutManager)
                reset = lm;
        } else {
            GridLayoutManager lm = new GridLayoutManager(context, i);
            lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return this.getSpanSize(position);
                }
            });
            RecyclerView.LayoutManager l = grid.getLayoutManager();
            if (l == null || !(l instanceof GridLayoutManager) || ((GridLayoutManager) l).getSpanCount() != i)
                reset = lm;
        }
        if (reset != null)
            grid.setLayoutManager(reset);
    }

    public int getSpanSize(int position) {
        return 1;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener l) {
        clickListener = l;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener l) {
        longClickListener = l;
    }
}