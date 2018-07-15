package com.github.axet.bookreader.widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.github.axet.androidlibrary.widgets.ThemeUtils;
import com.github.axet.bookreader.app.MainApplication;
import com.github.axet.bookreader.app.Storage;
import com.github.axet.k2pdfopt.K2PdfOpt;

import org.geometerplus.zlibrary.core.view.ZLViewEnums;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Reflow {
    public K2PdfOpt k2;
    public int current = 0; // current view position
    public int page = 0; // document page
    int w;
    int h;
    int rw;
    Context context;
    public Bitmap bm; // source bm, in case or errors, recycled otherwise
    public Storage.RecentInfo info;
    FBReaderView.CustomView custom; // font size

    public static class Info {
        public Rect bm; // source bitmap size
        public Rect margin; // page margins
        public Map<Rect, Rect> src;
        public Map<Rect, Rect> dst;

        public Info(Reflow reflower, int page) {
            bm = new Rect(0, 0, reflower.bm.getWidth(), reflower.bm.getHeight());
            margin = new Rect(reflower.getLeftMargin(), 0, reflower.getRightMargin(), 0);
            src = reflower.k2.getRectMaps(page);
            dst = new HashMap<>();
            for (Rect k : src.keySet()) {
                Rect v = src.get(k);
                dst.put(v, k);
            }
        }

        public Point fromDst(Rect d, int x, int y) {
            Rect s = dst.get(d);
            double kx = s.width() / (double) d.width();
            double ky = s.height() / (double) d.height();
            return new Point(s.left + (int) ((x - d.left) * kx), s.top + (int) ((y - d.top) * ky));
        }

        public Rect fromSrc(Rect s, Rect r) {
            Rect d = src.get(s);
            double kx = d.width() / (double) s.width();
            double ky = d.height() / (double) s.height();
            return new Rect(d.left + (int) ((r.left - s.left) * kx), d.top + (int) ((r.top - s.top) * ky), d.right + (int) ((r.right - s.right) * kx), d.bottom + (int) ((r.bottom - s.bottom) * ky));
        }
    }

    public Reflow(Context context, int w, int h, int page, FBReaderView.CustomView custom, Storage.RecentInfo info) {
        this.context = context;
        this.page = page;
        this.info = info;
        this.custom = custom;
        reset(w, h);
    }

    public void reset() {
        w = 0;
        h = 0;
        if (k2 != null) {
            k2.close();
            k2 = null;
        }
    }

    public int getLeftMargin() {
        return custom.getLeftMargin();
    }

    public int getRightMargin() {
        return custom.getRightMargin();
    }

    public void reset(int w, int h) {
        if (this.w != w || this.h != h) {
            SharedPreferences shared = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
            Float old = shared.getFloat(MainApplication.PREFERENCE_FONTSIZE_REFLOW, MainApplication.PREFERENCE_FONTSIZE_REFLOW_DEFAULT);
            if (info.fontsize != null)
                old = info.fontsize / 100f;
            if (k2 != null) {
                old = k2.getFontSize();
                k2.close();
            }
            int rw = w - getLeftMargin() - getRightMargin();
            k2 = new K2PdfOpt();
            DisplayMetrics d = context.getResources().getDisplayMetrics();
            k2.create(rw, h, d.densityDpi);
            k2.setFontSize(old);
            this.w = w;
            this.h = h;
            this.rw = rw;
            this.current = 0; // size changed, reflow page can overflow total pages
        }
    }

    public void load(Bitmap bm) {
        if (this.bm != null)
            this.bm.recycle();
        this.bm = bm;
        current = 0;
        k2.load(bm);
    }

    public void load(Bitmap bm, int page, int current) {
        if (this.bm != null)
            this.bm.recycle();
        this.bm = bm;
        this.page = page;
        this.current = current;
        k2.load(bm);
    }

    public int count() {
        if (k2 == null)
            return -1;
        return k2.getCount();
    }

    public int emptyCount() {
        int c = count();
        if (c == 0)
            c = 1;
        return c;
    }

    public Bitmap render(int page) {
        return k2.renderPage(page);
    }

    public boolean canScroll(ZLViewEnums.PageIndex index) {
        switch (index) {
            case previous:
                return current > 0;
            case next:
                return current + 1 < count();
            default:
                return true; // current???
        }
    }

    public void onScrollingFinished(ZLViewEnums.PageIndex index) {
        switch (index) {
            case next:
                current++;
                break;
            case previous:
                current--;
                break;
        }
    }

    public void close() {
        if (k2 != null) {
            k2.close();
            k2 = null;
        }
        if (bm != null) {
            bm.recycle();
            bm = null;
        }
    }

    public Bitmap drawSrc(PluginView pluginview, Info info, Rect r) {
        Bitmap bm = drawSrc(pluginview, info);
        Canvas c = new Canvas(bm);
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        c.drawRect(r, paint);
        return bm;
    }

    public Bitmap drawSrc(PluginView pluginview, Info info, Point p) {
        Bitmap bm = drawSrc(pluginview, info);
        Canvas c = new Canvas(bm);
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        c.drawPoint(p.x, p.y, paint);
        return bm;
    }

    public Bitmap drawSrc(PluginView pluginview, Info info) {
        Bitmap b = pluginview.render(w, h, Reflow.this.page);
        Canvas canvas = new Canvas(b);
        draw(canvas, info.src.keySet());
        return b;
    }

    public Bitmap drawDst(Info info, Rect r) {
        Bitmap bm = drawDst(info);
        if (bm == null)
            return null;
        Canvas c = new Canvas(bm);
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        c.drawRect(r, paint);
        return bm;
    }

    public Bitmap drawDst(Info info, Point p) {
        Bitmap bm = drawDst(info);
        if (bm == null)
            return null;
        Canvas c = new Canvas(bm);
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        c.drawPoint(p.x, p.y, paint);
        return bm;
    }

    int findPage(Info info) {
        for (int i = 0; i < count(); i++) {
            if (info.src.equals(k2.getRectMaps(i)))
                return i;
        }
        return -1;
    }

    public Bitmap drawDst(Info info) {
        int page = findPage(info);
        if (page == -1)
            return null;
        Bitmap b = render(page);
        Canvas canvas = new Canvas(b);
        draw(canvas, info.dst.keySet());
        return b;
    }

    public void draw(Canvas canvas, Set<Rect> keys) {
        Rect[] kk = keys.toArray(new Rect[0]);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        Paint text = new Paint();
        for (int i = 0; i < kk.length; i++) {
            Rect k = kk[i];
            canvas.drawRect(k, paint);

            String t = "" + i;
            text.setColor(Color.RED);

            int size = 0;
            Rect bounds = new Rect();
            do {
                text.setTextSize(size);
                text.getTextBounds(t, 0, t.length(), bounds);
                size++;
            } while (bounds.height() < (k.height()));

            float m = text.
                    measureText(t);
            canvas.drawText(t, k.centerX() - m / 2, k.top + k.height(), text);
        }
    }

}