package com.gitlab.capstone.pellego.fragments.library;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.axet.androidlibrary.net.HttpClient;
import com.github.axet.androidlibrary.services.StorageProvider;
import com.github.axet.androidlibrary.widgets.CacheImagesAdapter;
import com.github.axet.androidlibrary.widgets.CacheImagesRecyclerAdapter;
import com.github.axet.androidlibrary.widgets.InvalidateOptionsMenuCompat;
import com.github.axet.androidlibrary.widgets.OpenFileDialog;
import com.github.axet.androidlibrary.widgets.SearchView;
import com.github.axet.androidlibrary.widgets.TextMax;
import com.gitlab.capstone.pellego.R;

import com.gitlab.capstone.pellego.activities.MainActivity;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.fragments.profile.ProfileFragment;
import com.gitlab.capstone.pellego.widgets.BookmarksDialog;
import com.gitlab.capstone.pellego.widgets.FBReaderView;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LibraryFragment extends Fragment implements MainActivity.SearchListener {
    public static final String TAG = LibraryFragment.class.getSimpleName();
    private static LibraryFragment INSTANCE;

    LibraryAdapter books;
    Storage storage;
    String lastSearch = "";
    FragmentHolder holder;
    Runnable invalidateOptionsMenu;
    public static View header;

    public LibraryFragment() {
    }

    public static LibraryFragment newInstance() {
        if(INSTANCE == null) {
            INSTANCE = new LibraryFragment();
            Bundle args = new Bundle();
            INSTANCE.setArguments(args);
        }

        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = new Storage(getContext());
        holder = new FragmentHolder(getContext());
        books = new LibraryAdapter(holder, getContext());
        setHasOptionsMenu(true);

        Log.d("LibraryFragment", storage.list().toString());
//        Log.d("LibraryFragment", storage.list().get(0).toString());
//        Log.d("LibraryFragment", storage.list().get(1).toString());
//        Log.d("LibraryFragment", storage.list().get(2).toString());
//        Log.d("LibraryFragment", Storage.getTitle(storage.list().get(0).info));

    }

    @Override
    public void onResume() {
        super.onResume();
        books.load();
        books.refresh();
        // Emtpy library
        LinearLayout emptyLibraryMsg =  getActivity().findViewById(R.id.empty_library_container);
        LottieAnimationView lottieAnimationView = getActivity().findViewById(R.id.empty_anim);
        if (books.all.size() == 0) {
            emptyLibraryMsg.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
        } else {
            emptyLibraryMsg.setVisibility(View.INVISIBLE);
            lottieAnimationView.cancelAnimation();
        }
//        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, null);
//        drawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTint(drawable, Color.WHITE);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(drawable);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_library, container, false);
        header = (View) getActivity().findViewById(R.id.header_circular);

        holder.create(v);
        holder.footer.setVisibility(View.GONE);
        setupHeader(v);
        final MainActivity main = (MainActivity) getActivity();
        main.toolbar.setTitle(null);
        main.toolbar.setVisibility(View.VISIBLE);
        FrameLayout constraintLayout = main.findViewById(R.id.host_fragment_container);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();
        layoutParams.bottomToTop = R.id.container_bottom;
        layoutParams.topToTop = R.id.main_content;
        constraintLayout.setLayoutParams(layoutParams);
        // handle import button click
        holder.import_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.PopupMenu popup = new android.widget.PopupMenu(getContext(), holder.import_button);
                popup.setOnMenuItemClickListener((item) -> {
                    switch (item.getItemId()) {
                        case R.id.importFileItem:
                            //archive(item);
                            Log.i("LIBRARY", "import file item clicked");
                            // Start file explorer
                            main.importPressed();
                            return true;
                        case R.id.importPellegoItem:
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                            navController.navigate(R.id.pellegoLibrary);
                            return true;
                        default:
                            return false;
                    }
                } );

                popup.inflate(R.menu.popup_libary);
                popup.show();
            }
        });
        main.findViewById(R.id.bottom_nav_view).setVisibility(View.VISIBLE);

        holder.grid.setAdapter(books);
        holder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Storage.Book b = books.getItem(position);
                main.loadBook(b);
            }
        });
        holder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Storage.Book b = books.getItem(position);
                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.inflate(R.menu.bookitem_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_rename) {
                            final OpenFileDialog.EditTextDialog e = new OpenFileDialog.EditTextDialog(getContext());
                            e.setTitle(R.string.book_rename);
                            e.setText(b.info.title);
                            e.setPositiveButton(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String name = e.getText();
                                    b.info.title = name;
                                    storage.save(b);
                                    books.notifyDataSetChanged();
                                }
                            });
                            AlertDialog d = e.create();
                            d.show();
                        }
                        if (item.getItemId() == R.id.action_open) {
                            String ext = Storage.getExt(getContext(), b.url);
                            String n = Storage.getTitle(b.info) + "." + ext;
                            Intent open = StorageProvider.getProvider().openIntent(b.url, n);
                            startActivity(open);
                        }
                        if (item.getItemId() == R.id.action_share) {
                            String ext = Storage.getExt(getContext(), b.url);
                            String t = Storage.getTitle(b.info) + "." + ext;
                            String name = Storage.getName(getContext(), b.url);
                            String type = Storage.getTypeByName(name);
                            Intent share = StorageProvider.getProvider().shareIntent(b.url, t, type, t);
                            startActivity(share);
                        }
                        if (item.getItemId() == R.id.action_delete) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(R.string.book_delete);
                            builder.setMessage(R.string.are_you_sure);
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    storage.delete(b);
                                    books.delete(b);
                                }
                            });
                            builder.show();
                        }
                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });

        return v;
    }

    public static class ByRecent implements Comparator<Storage.Book> {
        @Override
        public int compare(Storage.Book o1, Storage.Book o2) {
            return Long.valueOf(o1.info.last).compareTo(o2.info.last);
        }
    }

    public static class ByCreated implements Comparator<Storage.Book> {
        @Override
        public int compare(Storage.Book o1, Storage.Book o2) {
            return Long.valueOf(o1.info.created).compareTo(o2.info.created);
        }
    }

    public static class ByName implements Comparator<Storage.Book> {
        @Override
        public int compare(Storage.Book o1, Storage.Book o2) {
            return Storage.getTitle(o1.info).compareTo(Storage.getTitle(o2.info));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupHeader(View root) {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.light_blue_solid));
        toolbar.setTitle(null);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.light_blue_solid));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity main = ((MainActivity) getActivity());
        main.setFullscreen(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        books.clearTasks();
    }

        @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        invalidateOptionsMenu = InvalidateOptionsMenuCompat.onCreateOptionsMenu(this, menu, inflater);
        MenuItem homeMenu = menu.findItem(R.id.action_home);
        MenuItem tocMenu = menu.findItem(R.id.action_toc);
        MenuItem bookmarksMenu = menu.findItem(R.id.action_bm);
        MenuItem fontsize = menu.findItem(R.id.action_fontsize);
        MenuItem rtl = menu.findItem(R.id.action_rtl);
        MenuItem mode = menu.findItem(R.id.action_mode);
        MenuItem sort = menu.findItem(R.id.action_sort);
        MenuItem tts = menu.findItem(R.id.action_tts);
        MenuItem settings = menu.findItem(R.id.action_settings);
        settings.setVisible(true);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());
        int selected = getContext().getResources().getIdentifier(shared.getString(App.PREFERENCE_SORT, getContext().getResources().getResourceEntryName(R.id.sort_add_ask)), "id", getContext().getPackageName());
        SubMenu sorts = sort.getSubMenu();
        for (int i = 0; i < sorts.size(); i++) {
            MenuItem m = sorts.getItem(i);
            m.setCheckable(false);
            if (m.getItemId() == selected)
                m.setIcon(R.drawable.ic_checked_circle);
            else {
                m.setIcon(R.drawable.ic_empty_circle);
            }

            // sort clicked
            m.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
        }

//        reflow.setVisible(false);
//        searchMenu.setVisible(true);
        homeMenu.setVisible(false);
        tocMenu.setVisible(false);
        bookmarksMenu.setVisible(books.hasBookmarks());
        fontsize.setVisible(false);
//        debug.setVisible(false);
        rtl.setVisible(false);
        mode.setVisible(false);
        tts.setVisible(false);

        holder.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (holder.onOptionsItemSelected(item)) {
            invalidateOptionsMenu.run();
            return true;
        }
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());
        int id = item.getItemId();
        if (id == R.id.sort_add_ask || id == R.id.sort_add_desc || id == R.id.sort_name_ask || id == R.id.sort_name_desc || id == R.id.sort_open_ask || id == R.id.sort_open_desc) {
            shared.edit().putString(App.PREFERENCE_SORT, getContext().getResources().getResourceEntryName(item.getItemId())).commit();
            books.sort();
            invalidateOptionsMenu.run();
            return true;
        } else if (id == R.id.action_bm) {
            BookmarksDialog dialog = new BookmarksDialog(getContext()) {
                @Override
                public void onSelected(Storage.Book b, Storage.Bookmark bm) {
                    MainActivity main = ((MainActivity) getActivity());
                    main.openBook(b.url, new FBReaderView.ZLTextIndexPosition(bm.start, bm.end));
                }

                @Override
                public void onSave(Storage.Book book, Storage.Bookmark bm) {
                    storage.save(book);
                }

                @Override
                public void onDelete(Storage.Book book, Storage.Bookmark bm) {
                    book.info.bookmarks.remove(bm);
                    storage.save(book);
                }
            };
            dialog.load(books.all);
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void search(String s) {
        books.filter = s;
        books.refresh();
        lastSearch = books.filter;
    }

    @Override
    public void searchClose() {
        search("");
    }

    @Override
    public String getHint() {
        return getString(R.string.search_local);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
