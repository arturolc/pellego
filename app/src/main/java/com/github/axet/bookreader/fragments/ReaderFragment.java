package com.github.axet.bookreader.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.axet.bookreader.R;
import com.github.axet.bookreader.activities.MainActivity;
import com.github.axet.bookreader.app.Storage;
import com.github.axet.bookreader.widgets.FBReaderView;

import org.geometerplus.android.fbreader.PopupPanel;
import org.geometerplus.fbreader.fbreader.ActionCode;

public class ReaderFragment extends Fragment {
    public static final String TAG = ReaderFragment.class.getSimpleName();

    Storage storage;
    FBReaderView view;
    ImageButton toc;
    ImageButton search;

    BroadcastReceiver battery = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            view.battery = level * 100 / scale;
        }
    };

    public ReaderFragment() {
    }

    public static ReaderFragment newInstance(Uri uri) {
        ReaderFragment fragment = new ReaderFragment();
        Bundle args = new Bundle();
        args.putParcelable("uri", uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = new Storage(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).clearMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reader, container, false);

        final MainActivity main = (MainActivity) getActivity();

        view = (FBReaderView) v.findViewById(R.id.main_view);

        Context context = getContext();
        context.registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        toc = (ImageButton) main.toolbar.findViewById(R.id.toolbar_toc);
        // toc.setVisibility(View.VISIBLE);
        toc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        search = (ImageButton) main.toolbar.findViewById(R.id.toolbar_search);
        // search.setVisibility(View.VISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.app.runAction(ActionCode.SEARCH);
            }
        });

        view.setWindow(getActivity().getWindow());
        view.setActivity(getActivity());

        Uri uri = getArguments().getParcelable("uri");

        try {
            Storage.Book b = storage.load(uri);
            if (!b.isLoaded())
                storage.load(b);
            view.loadBook(b);
        } catch (RuntimeException e) {
            main.Error(e);
            main.openLibrary();
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        savePosition();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        savePosition();
    }

    void savePosition() {
        if (view.book == null)
            return;
        view.book.info.position = view.getPosition();
        storage.save(view.book);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        search.setVisibility(View.GONE);
        Context context = getContext();
        context.unregisterReceiver(battery);
    }

}
