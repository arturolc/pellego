package com.gitlab.capstone.pellego.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.github.axet.androidlibrary.app.FileTypeDetector;
import com.github.axet.androidlibrary.preferences.RotatePreferenceCompat;
import com.github.axet.androidlibrary.widgets.CacheImagesAdapter;
import com.github.axet.androidlibrary.widgets.ErrorDialog;
import com.github.axet.androidlibrary.widgets.OpenChoicer;
import com.github.axet.androidlibrary.widgets.OpenFileDialog;
import com.github.axet.androidlibrary.widgets.ThemeUtils;
import com.github.axet.androidlibrary.widgets.WebViewCustom;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.fragments.library.LibraryFragment;
import com.gitlab.capstone.pellego.fragments.profile.ProfileFragment;
import com.gitlab.capstone.pellego.fragments.reader.ReaderFragment;
import com.gitlab.capstone.pellego.widgets.FBReaderView;
import com.google.android.material.navigation.NavigationView;

import org.geometerplus.fbreader.fbreader.options.ImageOptions;
import org.geometerplus.fbreader.fbreader.options.MiscOptions;
import org.geometerplus.zlibrary.text.view.ZLTextPosition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/****************************************
 * Eli Hebdon
 *
 * Represents the Main Screen Activity
 * which manages library functionality
 ***************************************/

public class MainActivity extends FullscreenActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static Bitmap bitmap;
    public static final int RESULT_FILE = 1;
    public static final int RESULT_ADD_CATALOG = 2;
    Storage storage;
    OpenChoicer choicer;
    boolean isRunning;

    LibraryFragment libraryFragment = LibraryFragment.newInstance();
    public boolean volumeEnabled = true; // tmp enabled / disable volume keys
    Context context;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(FBReaderView.ACTION_MENU))
                toggle();
        }
    };

    public interface SearchListener {
        String getHint();

        void search(String s);

        void searchClose();
    }

    public interface OnBackPressed {
        boolean onBackPressed();
    }

    public static class SortByPage implements Comparator<Storage.RecentInfo> {
        @Override
        public int compare(Storage.RecentInfo o1, Storage.RecentInfo o2) {
            int r = Integer.valueOf(o1.position.getParagraphIndex()).compareTo(o2.position.getParagraphIndex());
            if (r != 0)
                return r;
            return Integer.valueOf(o1.position.getElementIndex()).compareTo(o2.position.getElementIndex());
        }
    }

    public static class ProgressDialog extends AlertDialog.Builder {
        public Handler handler = new Handler();
        public ProgressBar load;
        public ProgressBar v;
        public TextView text;
        public Storage.Progress progress = new Storage.Progress() {
            @Override
            public void progress(final long bytes, final long total) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ProgressDialog.this.progress(bytes, total);
                    }
                });
            }
        };

        public ProgressDialog(Context context) {
            super(context);
            int dp10 = ThemeUtils.dp2px(context, 10);

            final LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            v = new ProgressBar(context);
            v.setIndeterminate(true);
            v.setPadding(dp10, dp10, dp10, dp10);
            ll.addView(v);
            load = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
            load.setPadding(dp10, dp10, dp10, dp10);
            load.setMax(100);
            Drawable draw = getContext().getResources().getDrawable(R.drawable.load_progressbar);

            load.setProgressDrawable(draw);
            ll.addView(load);
            text = new TextView(context);
            text.setPadding(dp10, dp10, dp10, dp10);
            ll.addView(text);
            load.setVisibility(View.GONE);
            text.setVisibility(View.GONE);

            setTitle(R.string.loading_book);
            setView(ll);
            setCancelable(false);
            setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }

        public void progress(long bytes, long total) {
            String str = App.formatSize(getContext(), bytes);
            if (total > 0) {
                str += " / " + App.formatSize(getContext(), total);
                load.setProgress((int) (bytes * 100 / total));
                load.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
            } else {
                load.setVisibility(View.GONE);
                v.setVisibility(View.VISIBLE);
            }
            str += String.format(" (%s%s)", App.formatSize(getContext(), progress.info.getCurrentSpeed()), getContext().getString(R.string.per_second));
            text.setText(str);
            text.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences =
                getSharedPreferences("my_preferences", MODE_PRIVATE);

        if(!preferences.getBoolean("onboarding_complete",false)){
            OnboardingActivity.startActivity(this);
        }

        storage = new Storage(this);
        registerReceiver(receiver, new IntentFilter(FBReaderView.ACTION_MENU));
        if (savedInstanceState == null && getIntent().getParcelableExtra(SAVE_INSTANCE_STATE) == null) {
            openLibrary();
            openIntent(getIntent());
        }

        context = getApplicationContext();

        RotatePreferenceCompat.onCreate(this, App.PREFERENCE_ROTATE);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> ff = fm.getFragments();
        if (ff != null) {
            for (Fragment f : ff) {
                if (f != null && f.isVisible() && f instanceof OnBackPressed) {
                    OnBackPressed s = (OnBackPressed) f;
                    if (s.onBackPressed())
                        return;
                }
            }
        }
        super.onBackPressed();
        if (fm.getBackStackEntryCount() == 0)
            onResume();
    }

    public static void loadImageFromStorage(Activity a)
    {
        try {
            File f=new File(ProfileFragment.profilePath, "profile.jpg");
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            bitmap = BitmapFactory.decodeResource(a.getResources(),
                    R.drawable.ic_default_user_profile);
            e.printStackTrace();
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            ImageView im = findViewById(R.id.profile_image_drawer);
            im.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.d("bitmap" , e.getMessage());
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        try {
            ImageView im = findViewById(R.id.profile_image_drawer);
            im.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.d("bitmap" , e.getMessage());
        }
        final SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SettingsActivity.startActivity(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_library)
            openLibrary();

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        openIntent(intent);
    }

    void openIntent(Intent intent) {
        if (intent == null)
            return;
        String a = intent.getAction();
        if (a == null)
            return;
        Uri u = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (u == null)
            u = intent.getData();
        if (u == null) {
            String t = intent.getStringExtra(Intent.EXTRA_TEXT); // handling SEND intents
            if (t != null && t.startsWith(WebViewCustom.SCHEME_HTTP))
                u = Uri.parse(t);
        }
        if (u == null)
            return;
        loadBook(u, null);
    }

    public void importPressed() {
        final SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        String last = shared.getString(App.PREFERENCE_LAST_PATH, null);
        Uri old = null;
        if (last != null) {
            old = Uri.parse(last);
            File f = Storage.getFile(old);
            while (f != null && !f.exists())
                f = f.getParentFile();
            if (f != null)
                old = Uri.fromFile(f);
        } else {
            old = Uri.parse(ContentResolver.SCHEME_CONTENT + Storage.CSS); // show SAF default
        }
        choicer = new OpenChoicer(OpenFileDialog.DIALOG_TYPE.FILE_DIALOG, true) {
            @Override
            public void onResult(Uri uri) {
                String s = uri.getScheme();
                if (s.equals(ContentResolver.SCHEME_FILE)) {
                    File f = Storage.getFile(uri);
                    f = f.getParentFile();
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString(App.PREFERENCE_LAST_PATH, f.toString());
                    editor.commit();
                }
                loadBook(uri, null);
            }
        };
        choicer.setStorageAccessFramework(this, RESULT_FILE);
        choicer.setPermissionsDialog(this, Storage.PERMISSIONS_RO, RESULT_FILE);
        choicer.show(old);

    }

    public void loadBook(final Uri u, final Runnable success) {
        final ProgressDialog builder = new ProgressDialog(this);
        final AlertDialog d = builder.create();
        d.show();
        Thread thread = new Thread("load book") {
            @Override
            public void run() {
                final Thread t = Thread.currentThread();
                d.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        t.interrupt();
                    }
                });
                try {
                    final Storage.Book book = storage.load(u, builder.progress);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isFinishing() || !isRunning)
                                return;
                            loadBook(book);
                            if (success != null)
                                success.run();
                        }
                    });
                } catch (FileTypeDetector.DownloadInterrupted e) {
                    Log.d(TAG, "interrupted", e);
                } catch (Throwable e) {
                    ErrorDialog.Post(MainActivity.this, e);
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            d.cancel();
                        }
                    });
                }
            }
        };
        thread.start();
    }

    @SuppressLint("RestrictedApi")
    public void loadBook(final Storage.Book book) {
        final List<Uri> uu = storage.recentUris(book);
        if (uu.size() > 1) {
            LayoutInflater inflater = LayoutInflater.from(this);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final ArrayList<ZLTextPosition> selected = new ArrayList<>();

            selected.clear();
            selected.add(book.info.position);

            final Storage.FBook fbook = storage.read(book);

            final Runnable done = new Runnable() {
                @Override
                public void run() {
                    fbook.close();

                    for (Uri u : uu) {
                        try {
                            Storage.RecentInfo info = new Storage.RecentInfo(MainActivity.this, u);
                            book.info.merge(info);
                        } catch (Exception e) {
                            Log.d(TAG, "unable to merge info", e);
                        }
                        Storage.delete(MainActivity.this, u);
                    }
                    book.info.position = selected.get(0);
                    storage.save(book);

                    openBook(book.url);
                }
            };

            builder.setTitle(R.string.sync_conflict);

            View v = inflater.inflate(R.layout.recent, null);

            final FBReaderView r = (FBReaderView) v.findViewById(R.id.recent_fbview);
            r.config.setValue(r.app.MiscOptions.WordTappingAction, MiscOptions.WordTappingActionEnum.doNothing);
            r.config.setValue(r.app.ImageOptions.TapAction, ImageOptions.TapActionEnum.doNothing);

            SharedPreferences shared = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
            String mode = shared.getString(App.PREFERENCE_VIEW_MODE, "");
            r.setWidget(mode.equals(FBReaderView.Widgets.CONTINUOUS.toString()) ? FBReaderView.Widgets.CONTINUOUS : FBReaderView.Widgets.PAGING);

            r.loadBook(fbook);

            LinearLayout pages = (LinearLayout) v.findViewById(R.id.recent_pages);

            List<Storage.RecentInfo> rr = new ArrayList<>();

            for (Uri u : uu) {
                try {
                    Storage.RecentInfo info = new Storage.RecentInfo(MainActivity.this, u);
                    if (info.position != null) {
                        boolean found = false;
                        for (int i = 0; i < rr.size(); i++) {
                            Storage.RecentInfo ii = rr.get(i);
                            if (ii.position.getParagraphIndex() == info.position.getParagraphIndex() && ii.position.getElementIndex() == info.position.getElementIndex())
                                found = true;
                        }
                        if (!found) {
                            rr.add(info);
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Unable to read info", e);
                }
            }

            Collections.sort(rr, new SortByPage());

            if (rr.size() == 1) {
                done.run();
                return;
            }

            for (int i = 0; i < rr.size(); i++) {
                final Storage.RecentInfo info = rr.get(i);


                TextView p = (TextView) inflater.inflate(R.layout.recent_item, pages, false);
                if (info.position != null)
                    p.setText(info.position.getParagraphIndex() + "." + info.position.getElementIndex());
                p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        r.gotoPosition(info.position);
                        selected.clear();
                        selected.add(info.position);
                    }
                });
                pages.addView(p);
            }

            builder.setView(v);

            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    fbook.close();
                }
            });

            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    done.run();
                }
            });

            builder.show();
            return;
        }
        openBook(book.url);
    }

    @SuppressLint("RestrictedApi")
    public void popBackStack(String tag, int flags) { // only pop existing TAG
        FragmentManager fm = getSupportFragmentManager();
        if (tag == null) {
            fm.popBackStack(null, flags);
            return;
        }
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            String n = fm.getBackStackEntryAt(i).getName();
            if (n != null && n.equals(tag)) {
                fm.popBackStack(tag, flags);
                return;
            }
        }
    }

    @SuppressLint("RestrictedApi")
    public Fragment getCurrentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> ff = fm.getFragments();
        if (ff == null)
            return null;
        for (Fragment f : ff) {
            if (f != null && f.isVisible())
                return f;
        }
        return null;
    }

    public void openBook(Uri uri) {
        popBackStack(ReaderFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.reader, ReaderFragment.newInstance(uri));
    }

    public void openBook(Uri uri, FBReaderView.ZLTextIndexPosition pos) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.reader, ReaderFragment.newInstance(uri, pos));

        popBackStack(ReaderFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void openLibrary() {
        FragmentManager fm = getSupportFragmentManager();
        popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        openFragment(libraryFragment, LibraryFragment.TAG);
        onResume(); // update theme if changed
    }

    public void addFragment(Fragment f, String tag) {
        openFragment(f, tag);
    }

    public void openFragment(Fragment f, String tag) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        FragmentManager fm = getSupportFragmentManager();
        if (f instanceof LibraryFragment) {
            navController.navigate(R.id.nav_library);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        RotatePreferenceCompat.onDestroy(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RESULT_FILE:
            case RESULT_ADD_CATALOG:
                if (choicer != null) // called twice or activity reacated
                    choicer.onRequestPermissionsResult(permissions, grantResults);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_FILE:
            case RESULT_ADD_CATALOG:
                if (choicer != null) // called twice or activity reacated
                    choicer.onActivityResult(resultCode, data);
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        if (volumeEnabled && shared.getBoolean(App.PREFERENCE_VOLUME_KEYS, false)) {
            FragmentManager fm = getSupportFragmentManager();
            for (Fragment f : fm.getFragments()) {
                if (f != null && f.isVisible() && f instanceof ReaderFragment) {
                    if (((ReaderFragment) f).onKeyDown(keyCode, event))
                        return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        if (volumeEnabled && shared.getBoolean(App.PREFERENCE_VOLUME_KEYS, false)) {
            FragmentManager fm = getSupportFragmentManager();
            for (Fragment f : fm.getFragments()) {
                if (f != null && f.isVisible() && f instanceof ReaderFragment) {
                    if (((ReaderFragment) f).onKeyUp(keyCode, event))
                        return true;
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        RotatePreferenceCompat.onResume(this, App.PREFERENCE_ROTATE);
        CacheImagesAdapter.cacheClear(this);
    }
}
