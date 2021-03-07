package com.gitlab.capstone.pellego.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.github.axet.androidlibrary.widgets.ThemeUtils;
import com.github.axet.androidlibrary.widgets.Toast;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.Plugin;
import com.gitlab.capstone.pellego.app.Reflow;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.fragments.reader.ReaderFragment;
import com.gitlab.capstone.pellego.fragments.rsvp.RsvpModuleFragment;

import org.geometerplus.fbreader.fbreader.TextBuildTraverser;
import org.geometerplus.zlibrary.core.view.ZLViewEnums;
import org.geometerplus.zlibrary.text.view.ZLTextElement;
import org.geometerplus.zlibrary.text.view.ZLTextElementArea;
import org.geometerplus.zlibrary.text.view.ZLTextElementAreaVector;
import org.geometerplus.zlibrary.text.view.ZLTextFixedPosition;
import org.geometerplus.zlibrary.text.view.ZLTextParagraphCursor;
import org.geometerplus.zlibrary.text.view.ZLTextPosition;
import org.geometerplus.zlibrary.text.view.ZLTextWord;
import org.geometerplus.zlibrary.text.view.ZLTextWordCursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static android.view.View.INVISIBLE;

public class PlayerWidget {
    public static String[] EOL = {"\n", "\r"};
    public static String[] STOPS = {".", ";"}; // ",", "\"", "'", "!", "?", "“", "”", ":", "(", ")"};
    public static int MAX_COUNT = getMaxSpeechInputLength(200);
    public static boolean playing = false;
    public int technique_id = -1;
    public Activity activity;
    private RsvpModuleFragment rsvpModuleFragment;
    private TextView progressTextView;
    public static int wpm = 250;

    public Context context;
    public static FBReaderView fb;
    Fragment fragment;
    public Storage.Bookmarks marks = new Storage.Bookmarks();
    public View panel;
    public View view;
    ArrayList<Runnable> onScrollFinished = new ArrayList<>();
    Handler handler = new Handler();
    int gravity;
    Runnable updateGravity = new Runnable() {
        @Override
        public void run() {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) panel.getLayoutParams();
            lp.gravity = gravity;
            panel.setLayoutParams(lp);
        }
    };

    public static int getMaxSpeechInputLength(int max) {
        if (Build.VERSION.SDK_INT >= 18) {
            if (max > TextToSpeech.getMaxSpeechInputLength())
                return TextToSpeech.getMaxSpeechInputLength();
        }
        return max;
    }

    public static Rect getRect(Plugin.View pluginview, ScrollWidget.ScrollAdapter.PageView v, Storage.Bookmark bm) {
        Plugin.View.Selection.Page page = pluginview.selectPage(bm.start, v.info, v.getWidth(), v.getHeight());
        Plugin.View.Selection s = pluginview.select(bm.start, bm.end);
        if (s != null) {
            Plugin.View.Selection.Bounds bb = s.getBounds(page);
            s.close();
            return SelectionView.union(Arrays.asList(bb.rr));
        } else {
            return null;
        }
    }

    public static boolean isStopSymbol(ZLTextElement e) {
        if (e instanceof ZLTextWord) {
            String str = ((ZLTextWord) e).getString();
            return isStopSymbol(str);
        }
        return false;
    }

    public static boolean isStopSymbol(String str) {
        if (str == null || str.isEmpty())
            return true;
        for (String s : STOPS) {
            if (str.contains(s))
                return true;
        }
        return false;
    }

    public static boolean stopOnLeft(ZLTextElement e) {
        if (e instanceof ZLTextWord) {
            String str = ((ZLTextWord) e).getString();
            return stopOnLeft(str);
        }
        return false;
    }

    public static boolean stopOnLeft(String str) {
        if (str == null || str.length() <= 1)
            return false;
        for (String s : STOPS) {
            if (str.startsWith(s))
                return true;
        }
        return false;
    }

    public static boolean stopOnRight(ZLTextElement e) {
        if (e instanceof ZLTextWord) {
            String str = ((ZLTextWord) e).getString();
            return stopOnRight(str);
        }
        return false;
    }

    public static boolean stopOnRight(String str) {
        if (str == null || str.isEmpty())
            return false;
        for (String s : STOPS) {
            if (str.endsWith(s))
                return true;
        }
        return false;
    }

    public static boolean isEmpty(Storage.Bookmark bm) {
        if (bm == null)
            return true;
        return bm.start == null || bm.end == null;
    }

    public static class Fragment {
        public Storage.Bookmark fragment; // paragraph or line
        public String fragmentText;
        public ArrayList<Bookmark> fragmentWords;
        public Storage.Bookmark word = new Storage.Bookmark();
        public int retry; // retry count
        public long last; // last time text was played

        public class Bookmark extends Storage.Bookmark {
            public int strStart;
            public int strEnd;

            public Bookmark(String z, ZLTextPosition s, ZLTextPosition e) {
                super(z, s, e);
                color = Color.BLACK;
            }
        }

        public Fragment(Storage.Bookmark bm) {
            String str = "";
            ArrayList<Bookmark> list = new ArrayList<>();
            if (fb.pluginview != null) {
                ZLTextPosition start = bm.start;
                ZLTextPosition end = bm.end;
                PluginWordCursor k = new PluginWordCursor(start);
                if (k.nextWord()) {
                    while (k.compareTo(end) <= 0) {
                        Bookmark b = new Bookmark(k.getText(), new ZLTextFixedPosition(start), new ZLTextFixedPosition(k));
                        b.strStart = str.length();
                        str += k.getText();
                        b.strEnd = str.length();
                        str += " ";
                        list.add(b);
                        start = new ZLTextFixedPosition(k);
                        k.nextWord();
                    }
                }
                k.close();
            } else {
                ZLTextParagraphCursor paragraphCursor = new ZLTextParagraphCursor(fb.app.Model.getTextModel(), bm.start.getParagraphIndex());
                ZLTextWordCursor wordCursor = new ZLTextWordCursor(paragraphCursor);
                wordCursor.moveTo(bm.start);
                for (ZLTextElement e = wordCursor.getElement(); wordCursor.compareTo(bm.end) < 0; e = wordCursor.getElement()) {
                    if (e instanceof ZLTextWord) {
                        String z = ((ZLTextWord) e).getString();
                        Bookmark b = new Bookmark(z, new ZLTextFixedPosition(wordCursor), new ZLTextFixedPosition(wordCursor.getParagraphIndex(), wordCursor.getElementIndex(), wordCursor.getCharIndex() + ((ZLTextWord) e).Length));
                        b.strStart = str.length();
                        str += z;
                        b.strEnd = str.length();
                        str += " ";
                        list.add(b);
                    }
                    wordCursor.nextWord();
                }
            }
            fragmentText = str;
            fragmentWords = list;
            fragment = new Storage.Bookmark(bm);
            fragment.color = Color.BLACK;
            word = null;
        }

        public Storage.Bookmark findWord(int start, int end) {
            for (Bookmark bm : fragmentWords) {
                if (bm.strStart == start)
                    return bm;
            }
            return null;
        }

        public boolean isEmpty() {
            if (fragment == null || fragmentText == null)
                return true;
            return fragmentText.trim().length() == 0;
        }
    }

    public static class PluginWordCursor extends ZLTextPosition {
        int p, e, c; // points to symbol
        Plugin.View.Selection all;
        String allText;
        String text;

        public PluginWordCursor(ZLTextPosition k) {
            p = k.getParagraphIndex();
            e = k.getElementIndex();
            c = k.getCharIndex();
        }

        public ZLTextPosition getCurrent() {
            return new ZLTextFixedPosition(p, e, c);
        }

        public boolean left() {
            if (all == null)
                return false;
            e--;
            if (e < 0) {
                e = 0;
                p--;
                if (p < 0) {
                    p = 0;
                    return false;
                } else {
                    all();
                    e = all.getEnd().getElementIndex() - 1;
                }
            }
            return true;
        }

        public boolean right() {
            if (all == null)
                return false;
            e++;
            if (e >= all.getEnd().getElementIndex()) {
                e = 0;
                p++;
                int last = fb.pluginview.pagePosition().Total - 1;
                if (p > last) {
                    p = last;
                    return false;
                } else {
                    all();
                }
            }
            return true;
        }

        public void all() {
            close();
            all = fb.pluginview.select(p);
            if (all != null) // empty page
                allText = all.getText();
        }

        public void close() {
            if (all != null)
                all.close();
            all = null;
        }

        public String select() {
            return allText.substring(getCurrent().getElementIndex(), getCurrent().getElementIndex() + 1);
        }

        public boolean prevWord() {
            all();
            if (all == null)
                return false;
            int sp = p;
            int se = e;
            String s;
            do {
                if (!left())
                    break;
                s = select();
            } while (!isWord(s));
            int k = e;
            if (sp != p)
                k = se;
            int last;
            do {
                last = e;
                if (!left())
                    break;
                s = select();
            } while (isWord(s) && !stopOnLeft(s));
            e = last;
            Plugin.View.Selection m = fb.pluginview.select(new ZLTextFixedPosition(p, e, 0), new ZLTextFixedPosition(sp, k, 0));
            if (m != null) {
                text = m.getText();
                m.close();
            }
            return true;
        }

        public boolean isWord(String str) {
            if (str.length() != 1)
                return false;
            for (String z : STOPS) {
                if (str.contains(z))
                    return true;
            }
            return all.isWord(str.charAt(0));
        }

        public boolean nextWord() {
            all();
            if (all == null)
                return false;
            int sp = p;
            int se = e;
            String s;
            do {
                if (!right())
                    break;
                s = select();
            } while (!isWord(s));
            int k = e;
            if (sp != p)
                k = se;
            int last;
            do {
                last = e;
                if (!right())
                    break;
                s = select();
            } while (isWord(s) && !stopOnRight(s));
            e = last;
            Plugin.View.Selection m = fb.pluginview.select(new ZLTextFixedPosition(sp, k, 0), new ZLTextFixedPosition(p, e, 0));
            if (m != null) {
                text = m.getText();
                m.close();
            }
            return true;
        }

        public String getText() {
            return text;
        }

        @Override
        public int getParagraphIndex() {
            return p;
        }

        @Override
        public int getElementIndex() {
            return e;
        }

        @Override
        public int getCharIndex() {
            return c;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PlayerWidget(FBReaderView v, Activity activity) {
        this.context = v.getContext();
        fb = v;
        this.activity = activity;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.tts_popup, null);
        ImageView myFab = activity.findViewById(R.id.button_play);

        // skip back button pressed
        View prev = activity.findViewById(R.id.button_prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (technique_id) {
                    case R.id.rsvp_menu_item:
                        if (playing) {
                            rsvpModuleFragment.stop();
                        } else {
                            togglePlay(myFab);
                        }
                        rsvpModuleFragment.startPrev();
                        break;
                    default:
                        break;
                }


            }
        });
        // skip forward button pressed
        View next = activity.findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (technique_id) {
                    case R.id.rsvp_menu_item:
                        if (playing) {
                            rsvpModuleFragment.stop();
                        } else {
                            togglePlay(myFab);
                        }
                        rsvpModuleFragment.startNext();
                        break;
                    default:
                        break;
                }

            }
        });

        // technique selector pressed
        Button techniqueSelector = (Button) activity.findViewById(R.id.button_technique);
        techniqueSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(activity, techniqueSelector);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.techniques_menu, popup.getMenu());
                if (playing) {
                    rsvpModuleFragment.stop();
                    togglePlay(myFab);
                }
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        technique_id = item.getItemId();
                        item.setChecked(true);
                        ReaderFragment.hideOptionsMenu();
                        switch(item.getItemId()) {
                            case R.id.rsvp_menu_item:
                                activity.findViewById(R.id.rsvp_reader).setVisibility(View.VISIBLE);
                                activity.findViewById(R.id.main_view).setVisibility(INVISIBLE);
                                rsvpModuleFragment = new RsvpModuleFragment();
                                rsvpModuleFragment.initRsvpReader(PlayerWidget.this, v, activity);
                                break;
                            case R.id.none_menu_item:
                                ReaderFragment.showOptionsMenu();
                                activity.findViewById(R.id.rsvp_reader).setVisibility(INVISIBLE);
                                activity.findViewById(R.id.main_view).setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        // wpm pressed
        Button wpmSelector = (Button) activity.findViewById(R.id.button_wpm);
        addDropDownSeekBar(wpmSelector);

        // play pressed
        myFab.setOnClickListener((View.OnClickListener) fb -> {
            switch (technique_id) {
                case R.id.rsvp_menu_item:
                    togglePlay(myFab);
                    rsvpModuleFragment.play();
                    break;
                default:
                    Toast.makeText(activity, "Select a technique to start speed reading",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        int dp20 = ThemeUtils.dp2px(context, 20);
        FrameLayout f = new FrameLayout(context);
        FrameLayout round = new FrameLayout(context);
        round.setBackgroundResource(R.drawable.panel);
        round.addView(view);
        gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        f.addView(round, new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, gravity));
        f.setPadding(dp20, dp20, dp20, dp20);
        this.view = f;
        this.panel = round;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private SeekBar addDropDownSeekBar(Button wpmSelector) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View contentView = inflater.inflate(R.layout.dropdown_seek_bar, null);
        SeekBar seekBar = (SeekBar) contentView.findViewById(R.id.drop_down_seek_bar);
        seekBar.setMin(50);
        seekBar.setMax(700);
        seekBar.setProgress(wpm);
        progressTextView = contentView.findViewById(R.id.progress_text);
        progressTextView.setText(String.valueOf(wpm));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                progressTextView.setText(String.valueOf(progress));
                wpm = progress;
                int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
                int thumbPos = seekBar.getPaddingLeft() + width * seekBar.getProgress() / seekBar.getMax();

                progressTextView.measure(0, 0);
                int txtW = progressTextView.getMeasuredWidth();
                int delta = txtW / 2;
                progressTextView.setX(seekBar.getX() + thumbPos - delta);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final PopupWindow popupWindow = new PopupWindow(context, null,
                android.R.attr.actionDropDownStyle);
        popupWindow.setBackgroundDrawable(activity.getDrawable(R.drawable.rounded_background));
        popupWindow.setFocusable(true); // seems to take care of dismissing on click outside
        popupWindow.setContentView(contentView);
        setPopupSize(popupWindow);

        wpmSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // compensate for PopupWindow's internal padding
                popupWindow.showAsDropDown(v, 0, -120);
            }
        });

        return seekBar;
    }

    private FrameLayout createActionButton(Context context, String title) {
        FrameLayout frame = new FrameLayout(context, null, android.R.attr.actionButtonStyle);
        frame.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        TextView text = new TextView(context, null, android.R.attr.actionMenuTextAppearance);
        text.setGravity(Gravity.CENTER_VERTICAL);
        text.setText(title);
        frame.addView(text);
        return frame;
    }

    private void setPopupSize(PopupWindow popupWindow) {
        View contentView = popupWindow.getContentView();

        int unspecified = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        contentView.measure(unspecified, unspecified);

        int width = contentView.getMeasuredWidth();
        int height = contentView.getMeasuredHeight();

        Drawable background = popupWindow.getBackground();
        if (background != null) {
            Rect rect = new Rect();
            background.getPadding(rect);
            width += rect.left + rect.right;
            height += rect.top + rect.bottom;
        }

        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
    }


    public void togglePlay(ImageView fab) {
        playing = !playing;
        if ( playing ) {
            fab.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_outline_pause_24));
        } else {
            fab.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_outline_play_arrow_24));
        }

    }

    public Context getContext() {
        return context;
    }


    public String selectPrev() {
        marks.clear();
        if (fragment == null) {
            if (fb.widget instanceof ScrollWidget) {
                int first = ((ScrollWidget) fb.widget).findFirstPage();
                ScrollWidget.ScrollAdapter.PageCursor c = ((ScrollWidget) fb.widget).adapter.pages.get(first);
                Storage.Bookmark bm = expandWord(new Storage.Bookmark("", c.start, c.start));
                fragment = new Fragment(bm);
            }
            if (fb.widget instanceof PagerWidget) {
                ZLTextPosition position = fb.getPosition();
                Storage.Bookmark bm = expandWord(new Storage.Bookmark("", position, position));
                fragment = new Fragment(bm);
            }
        } else {
            Storage.Bookmark bm = selectPrev(fragment.fragment);
            fragment = new Fragment(bm);
        }
        marks.add(fragment.fragment);
        if (fb.widget instanceof ScrollWidget) {
            ScrollWidget.ScrollAdapter.PageCursor nc;
            int pos = ((ScrollWidget) fb.widget).adapter.findPage(fragment.fragment.start);
            if (pos == -1)
                return "";
            nc = ((ScrollWidget) fb.widget).adapter.pages.get(pos);
            int first = ((ScrollWidget) fb.widget).findFirstPage();
            ScrollWidget.ScrollAdapter.PageCursor cur = ((ScrollWidget) fb.widget).adapter.pages.get(first);
            if (!nc.equals(cur)) {
                onScrollFinished.add(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                fb.scrollPrevPage();
            } else {
                ensureVisible(fragment.fragment);
            }
            updateGravity();
        }
        if (fb.widget instanceof PagerWidget) {
            if (fb.pluginview == null) {
                ZLTextPosition start = fb.app.BookTextView.getStartCursor();
                if (start.compareTo(fragment.fragment.end) >= 0) {
                    onScrollFinished.add(new Runnable() {
                        @Override
                        public void run() {
                            updateGravity();
                        }
                    });
                    fb.scrollPrevPage();
                } else {
                    updateGravity();
                }
            } else {
                Plugin.View.Selection s = fb.pluginview.select(fragment.fragment.start, fragment.fragment.end);
                Rect dst = ((PagerWidget) fb.widget).getPageRect();
                ZLTextPosition px = fb.pluginview.getPosition();
                if (px.getParagraphIndex() > fragment.fragment.start.getParagraphIndex()) {
                    onScrollFinished.add(new Runnable() {
                        @Override
                        public void run() {
                            updateGravity();
                        }
                    });
                    fb.scrollPrevPage();
                } else {
                    Plugin.View.Selection.Page page = fb.pluginview.selectPage(px, ((PagerWidget) fb.widget).getInfo(), dst.width(), dst.height());
                    Plugin.View.Selection.Bounds bounds = s.getBounds(page);
                    if (fb.pluginview.reflow) {
                        bounds.rr = fb.pluginview.boundsUpdate(bounds.rr, ((PagerWidget) fb.widget).getInfo());
                        bounds.start = true;
                        bounds.end = true;
                    }
                    ArrayList<Rect> ii = new ArrayList<>(Arrays.asList(bounds.rr));
                    Collections.sort(ii, new SelectionView.LinesUL(ii));
                    s.close();
                    if (ii.get(ii.size() - 1).bottom < ((PagerWidget) fb.widget).getTop() + fb.pluginview.current.pageOffset / fb.pluginview.current.ratio) {
                        onScrollFinished.add(new Runnable() {
                            @Override
                            public void run() {
                                updateGravity();
                            }
                        });
                        fb.scrollPrevPage();
                    } else {
                        Rect r = SelectionView.union(Arrays.asList(bounds.rr));
                        if (((PagerWidget) fb.widget).getHeight() / 2 < r.centerY())
                            updateGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                        else
                            updateGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                    }
                }
            }
        }
        return fragment.fragmentText;
    }

    // Get the next chunk of text
    public String selectNext() {
        marks.clear();
        if (fragment == null) {
            if (fb.widget instanceof ScrollWidget) {
                int first = ((ScrollWidget) fb.widget).findFirstPage();
                ScrollWidget.ScrollAdapter.PageCursor c = ((ScrollWidget) fb.widget).adapter.pages.get(first);
                Storage.Bookmark bm = expandWord(new Storage.Bookmark("", c.start, c.start));
                fragment = new Fragment(bm);
            }
            if (fb.widget instanceof PagerWidget) {
                ZLTextPosition position = fb.getPosition();
                Storage.Bookmark bm = expandWord(new Storage.Bookmark("", position, position));
                fragment = new Fragment(bm);
            }
        } else {
            Storage.Bookmark bm = selectNext(fragment.fragment);
            fragment = new Fragment(bm);
        }
        marks.add(fragment.fragment);
        if (fb.widget instanceof ScrollWidget) {
            int pos = ((ScrollWidget) fb.widget).adapter.findPage(fragment.fragment.start);
            if (pos == -1)
                return "";
            ScrollWidget.ScrollAdapter.PageCursor nc = ((ScrollWidget) fb.widget).adapter.pages.get(pos);
            int first = ((ScrollWidget) fb.widget).findFirstPage();
            ScrollWidget.ScrollAdapter.PageCursor cur = ((ScrollWidget) fb.widget).adapter.pages.get(first);
            if (!nc.equals(cur)) {
                int page = ((ScrollWidget) fb.widget).adapter.findPage(nc);
                onScrollFinished.add(new Runnable() {
                    @Override
                    public void run() {
                        updateGravity();
                    }
                });
                ((ScrollWidget) fb.widget).smoothScrollToPosition(page);
            } else {
                ensureVisible(fragment.fragment);
                updateGravity();
            }
        }
        if (fb.widget instanceof PagerWidget) {
            if (fb.pluginview == null) {
                ZLTextPosition end;
                end = fb.app.BookTextView.getEndCursor();
                if (end.compareTo(fragment.fragment.start) <= 0) {
                    onScrollFinished.add(new Runnable() {
                        @Override
                        public void run() {
                            updateGravity();
                        }
                    });
                    fb.scrollNextPage();
                } else {
                    updateGravity();
                }
            } else {
                Plugin.View.Selection s = fb.pluginview.select(fragment.fragment.start, fragment.fragment.end);
                Rect dst = ((PagerWidget) fb.widget).getPageRect();
                ZLTextPosition px = fb.pluginview.getPosition();
                if (px.getParagraphIndex() < fragment.fragment.start.getParagraphIndex()) {
                    fb.scrollNextPage();
                } else {
                    Plugin.View.Selection.Page page = fb.pluginview.selectPage(px, ((PagerWidget) fb.widget).getInfo(), dst.width(), dst.height());
                    Plugin.View.Selection.Bounds bounds = s.getBounds(page);
                    if (fb.pluginview.reflow) {
                        bounds.rr = fb.pluginview.boundsUpdate(bounds.rr, ((PagerWidget) fb.widget).getInfo());
                        bounds.start = true;
                        bounds.end = true;
                    }
                    ArrayList<Rect> ii = new ArrayList<>(Arrays.asList(bounds.rr));
                    Collections.sort(ii, new SelectionView.LinesUL(ii));
                    s.close();
                    if (ii.get(0).bottom > ((PagerWidget) fb.widget).getBottom() + fb.pluginview.current.pageOffset / fb.pluginview.current.ratio) {
                        onScrollFinished.add(new Runnable() {
                            @Override
                            public void run() {
                                updateGravity();
                            }
                        });
                        fb.scrollNextPage();
                    } else {
                        Rect r = SelectionView.union(Arrays.asList(bounds.rr));
                        if (((PagerWidget) fb.widget).getHeight() / 2 < r.centerY())
                            updateGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                        else
                            updateGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                    }
                }
            }
        }
        return fragment.fragmentText;
    }

    public Storage.Bookmark selectNext(Storage.Bookmark bm) {
        if (fb.pluginview != null) {
            ZLTextPosition start = bm.end;
            PluginWordCursor k = new PluginWordCursor(start);
            if (k.nextWord()) {
                ZLTextPosition end = expandRight(k);
                bm = new Storage.Bookmark(k.getText(), start, end);
            }
            k.close();
            return bm;
        } else {
            ZLTextPosition start = bm.end;
            ZLTextParagraphCursor paragraphCursor = new ZLTextParagraphCursor(fb.app.Model.getTextModel(), start.getParagraphIndex());
            ZLTextWordCursor wordCursor = new ZLTextWordCursor(paragraphCursor);
            wordCursor.moveTo(start);
            if (wordCursor.isEndOfParagraph())
                wordCursor.nextParagraph();
            else
                wordCursor.nextWord();
            start = wordCursor;
            ZLTextPosition end = expandRight(start);
            return new Storage.Bookmark(bm.text, start, end);
        }
    }

    public Storage.Bookmark selectPrev(Storage.Bookmark bm) {
        if (fb.pluginview != null) {
            ZLTextPosition end = bm.start;
            PluginWordCursor k = new PluginWordCursor(end);
            if (k.prevWord()) {
                ZLTextPosition start = expandLeft(k);
                bm = new Storage.Bookmark(k.getText(), start, end);
            }
            k.close();
            return bm;
        } else {
            ZLTextPosition end = bm.start;
            ZLTextParagraphCursor paragraphCursor = new ZLTextParagraphCursor(fb.app.Model.getTextModel(), end.getParagraphIndex());
            ZLTextWordCursor wordCursor = new ZLTextWordCursor(paragraphCursor);
            wordCursor.moveTo(end);
            wordCursor.previousWord();
            if (wordCursor.getElementIndex() < 0) {
                if (!wordCursor.previousParagraph()) {
                    wordCursor.moveTo(0, 0);
                } else {
                    wordCursor.moveToParagraphEnd();
                    wordCursor.previousWord();
                }
            }
            end = wordCursor;
            ZLTextElement e = wordCursor.getElement();
            if (e instanceof ZLTextWord)
                wordCursor.setCharIndex(((ZLTextWord) e).Length - 1);
            ZLTextPosition start = expandLeft(end);
            return new Storage.Bookmark(bm.text, start, end);
        }
    }

    public void show() {
        fb.addView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setVisibility(View.VISIBLE);
    }


    public void ensureVisible(Storage.Bookmark bm) { // same page
        int pos = ((ScrollWidget) fb.widget).adapter.findPage(bm.start);
        ScrollWidget.ScrollAdapter.PageCursor c = ((ScrollWidget) fb.widget).adapter.pages.get(pos);
        ScrollWidget.ScrollAdapter.PageView v = ((ScrollWidget) fb.widget).findViewPage(c);
        int bottom = fb.getTop() + ((ScrollWidget) fb.widget).getMainAreaHeight();
        Rect rect;
        if (fb.pluginview != null) {
            rect = getRect(fb.pluginview, v, bm);
            if (rect == null)
                return;
        } else {
            if (v.text == null)
                return;
            rect = FBReaderView.findUnion(v.text.areas(), bm);
            if (rect == null)
                return;
        }
        rect.top += v.getTop();
        rect.bottom += v.getTop();
        rect.left += v.getLeft();
        rect.right += v.getLeft();
        int dy = 0;
        if (rect.bottom > bottom)
            dy = rect.bottom - bottom;
        if (rect.top < fb.getTop())
            dy = rect.top - fb.getTop();
        ((ScrollWidget) fb.widget).smoothScrollBy(0, dy);
    }


    public void updateGravity(int g) {
        gravity = g;
        handler.removeCallbacks(updateGravity);
        handler.postDelayed(updateGravity, 200);
    }

    public void updateGravity() {
        if (fragment == null || marks.isEmpty()) {
            updateGravity(gravity);
            return;
        }
        if (fb.pluginview == null) {
            View view = null;
            ZLTextElementAreaVector text = null;
            if (fb.widget instanceof ScrollWidget) {
                int pos = ((ScrollWidget) fb.widget).adapter.findPage(fragment.fragment.start);
                if (pos == -1)
                    return;
                ScrollWidget.ScrollAdapter.PageCursor c = ((ScrollWidget) fb.widget).adapter.pages.get(pos);
                ScrollWidget.ScrollAdapter.PageView v = ((ScrollWidget) fb.widget).findViewPage(c);
                view = v;
                if (v != null)
                    text = v.text;
            }
            if (fb.widget instanceof PagerWidget) {
                view = (View) fb.widget;
                text = fb.app.BookTextView.myCurrentPage.TextElementMap;
            }
            if (view == null || text == null) { // happens when page just invalidated
                updateGravity(gravity);
            } else {
                ArrayList<Rect> rr = new ArrayList<>();
                for (ZLTextElementArea a : text.areas()) {
                    if (a.compareTo(fragment.fragment.start) >= 0 && a.compareTo(fragment.fragment.end) <= 0)
                        rr.add(new Rect(a.XStart, a.YStart, a.XEnd, a.YEnd));
                }
                if (rr.size() == 0) {
                    updateGravity(gravity);
                } else {
                    Rect r = SelectionView.union(rr);
                    if (((View) fb.widget).getHeight() / 2 < view.getTop() + r.centerY())
                        updateGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                    else
                        updateGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                }
            }
        } else {
            View view = null;
            Reflow.Info info = null;
            if (fb.widget instanceof ScrollWidget) {
                int pos = ((ScrollWidget) fb.widget).adapter.findPage(fragment.fragment.start);
                if (pos == -1)
                    return;
                ScrollWidget.ScrollAdapter.PageCursor c = ((ScrollWidget) fb.widget).adapter.pages.get(pos);
                ScrollWidget.ScrollAdapter.PageView v = ((ScrollWidget) fb.widget).findViewPage(c);
                view = v;
                info = v.info;
            }
            if (fb.widget instanceof PagerWidget) {
                view = (View) fb.widget;
                info = ((PagerWidget) fb.widget).getInfo();
            }
            Plugin.View.Selection s = fb.pluginview.select(fragment.fragment.start, fragment.fragment.end);
            if (s != null) {
                Plugin.View.Selection.Page page = fb.pluginview.selectPage(fragment.fragment.start, info, view.getWidth(), view.getHeight());
                Plugin.View.Selection.Bounds bounds = s.getBounds(page);
                Rect r = SelectionView.union(Arrays.asList(bounds.rr));
                s.close();
                if (((View) fb.widget).getHeight() / 2 < view.getTop() + r.centerY())
                    updateGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                else
                    updateGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            }
        }
    }

    public static String getText(ZLTextPosition start, ZLTextPosition end) {
        if (fb.pluginview != null) {
            Plugin.View.Selection s = fb.pluginview.select(start, end);
            if (s != null) {
                String str = s.getText();
                s.close();
                return str;
            }
            return null;
        } else {
            TextBuildTraverser tt = new TextBuildTraverser(fb.app.BookTextView);
            tt.traverse(start, end);
            return tt.getText();
        }
    }



    public static ZLTextPosition expandLeft(ZLTextPosition start) {
        if (fb.pluginview != null) {
            ZLTextPosition last;
            PluginWordCursor k = new PluginWordCursor(start);
            int count = 0;
            do {
                last = new ZLTextFixedPosition(k);
                k.prevWord();
                count++;
            } while (!isStopSymbol(k.getText()) && count < MAX_COUNT);
            if (stopOnLeft(k.getText()))
                last = new ZLTextFixedPosition(k);
            k.close();
            return last;
        } else {
            ZLTextParagraphCursor paragraphCursor = new ZLTextParagraphCursor(fb.app.Model.getTextModel(), start.getParagraphIndex());
            ZLTextWordCursor wordCursor = new ZLTextWordCursor(paragraphCursor);
            wordCursor.moveTo(start);
            wordCursor.setCharIndex(0);
            ZLTextPosition last;
            ZLTextElement e = null;
            int count = 0;
            do {
                last = new ZLTextFixedPosition(wordCursor);
                wordCursor.previousWord();
                if (wordCursor.getElementIndex() < 0) {
                    if (!wordCursor.previousParagraph())
                        wordCursor.moveTo(0, 0);
                    break;
                }
                e = wordCursor.getElement();
                count++;
            } while (!isStopSymbol(e) && count < MAX_COUNT);
            if (stopOnLeft(e))
                last = wordCursor;
            return last;
        }
    }

    public static ZLTextPosition expandRight(ZLTextPosition end) {
        if (fb.pluginview != null) {
            PluginWordCursor k = new PluginWordCursor(end);
            int count = 0;
            do {
                k.nextWord();
                count++;
            } while (!(isStopSymbol(k.getText()) && stopOnRight(k.getText())) && count < MAX_COUNT);
            end = new ZLTextFixedPosition(k);
            k.close();
            return end;
        } else {
            ZLTextParagraphCursor paragraphCursor = new ZLTextParagraphCursor(fb.app.Model.getTextModel(), end.getParagraphIndex());
            ZLTextWordCursor wordCursor = new ZLTextWordCursor(paragraphCursor);
            wordCursor.moveTo(end);
            int count = 0;
            ZLTextElement e;
            for (e = wordCursor.getElement(); !(isStopSymbol(e) && stopOnRight(e)) && count < MAX_COUNT; e = wordCursor.getElement()) {
                wordCursor.nextWord();
                count++;
            }
            e = wordCursor.getElement();
            if (e instanceof ZLTextWord)
                wordCursor.setCharIndex(((ZLTextWord) e).Length - 1);
            return wordCursor;
        }
    }

    public static Storage.Bookmark expandWord(Storage.Bookmark bm) {
        if (isEmpty(bm))
            return bm;
        ZLTextPosition start = expandLeft(bm.start);
        ZLTextPosition end = expandRight(bm.end);
        return new Storage.Bookmark(getText(start, end), start, end);
    }
}