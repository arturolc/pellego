package com.github.axet.bookreader.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.axet.androidlibrary.app.Natives;
import com.github.axet.bookreader.widgets.FBReaderView;
import com.github.axet.djvulibre.Config;
import com.github.axet.djvulibre.DjvuLibre;

import org.geometerplus.fbreader.book.AbstractBook;
import org.geometerplus.fbreader.book.BookUtil;
import org.geometerplus.fbreader.bookmodel.BookModel;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.fbreader.formats.BookReadingException;
import org.geometerplus.fbreader.formats.BuiltinFormatPlugin;
import org.geometerplus.zlibrary.core.encodings.EncodingCollection;
import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.core.image.ZLImage;
import org.geometerplus.zlibrary.core.view.ZLView;
import org.geometerplus.zlibrary.core.view.ZLViewEnums;
import org.geometerplus.zlibrary.text.model.ZLTextMark;
import org.geometerplus.zlibrary.text.model.ZLTextModel;
import org.geometerplus.zlibrary.text.model.ZLTextParagraph;
import org.geometerplus.zlibrary.ui.android.image.ZLBitmapImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DjvuPlugin extends BuiltinFormatPlugin {

    public static String TAG = DjvuPlugin.class.getSimpleName();

    public static final String EXT = "djvu";

    static {
        if (Config.natives) {
            Natives.loadLibraries(Storage.zlib.getBaseContext(), "djvu", "djvulibrejni");
            Config.natives = false;
        }
    }

    public static class PluginPage extends FBReaderView.PluginPage {
        public DjvuLibre doc;

        public PluginPage(PluginPage r) {
            super(r);
            doc = r.doc;
        }

        public PluginPage(PluginPage r, ZLViewEnums.PageIndex index) {
            this(r);
            load(index);
        }

        public PluginPage(DjvuLibre d) {
            doc = d;
            load();
        }

        public void load() {
            DjvuLibre.Page p = doc.getPageInfo(pageNumber);
            pageBox = new FBReaderView.PluginRect(0, 0, p.width, p.height);
        }

        @Override
        public int getPagesCount() {
            return doc.getPagesCount();
        }
    }

    public static class DjvuView extends FBReaderView.PluginView {
        public DjvuLibre doc;
        Paint paint = new Paint();
        FileInputStream is;

        public DjvuView(ZLFile f) {
            try {
                is = new FileInputStream(new File(f.getPath()));
                doc = new DjvuLibre(is.getFD());
                current = new PluginPage(doc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void drawOnBitmap(Bitmap bitmap, int w, int h, ZLView.PageIndex index) {
            Canvas canvas = new Canvas(bitmap);
            drawWallpaper(canvas);

            PluginPage r = new PluginPage((PluginPage) current, index);
            r.renderRect(w, h);
            current.updatePage(r);

            r.scale(w, h);
            FBReaderView.RenderRect render = r.renderRect(w, h);

            Bitmap bm = Bitmap.createBitmap(r.pageBox.w, r.pageBox.h, Bitmap.Config.ARGB_8888);
            bm.eraseColor(FBReaderView.PAGE_PAPER_COLOR);
            doc.renderPage(bm, r.pageNumber, 0, 0, r.pageBox.w, r.pageBox.h, render.x, render.y, render.w, render.h);
            canvas.drawBitmap(bm, render.src, render.dst, paint);
            bm.recycle();
        }
    }

    public static class DjvuTextModel extends DjvuView implements ZLTextModel {
        public ArrayList<ZLTextParagraph> pars = new ArrayList<>();

        public DjvuTextModel(ZLFile f) {
            super(f);
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            doc.close();
            is.close();
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public String getLanguage() {
            return null;
        }

        @Override
        public int getParagraphsNumber() {
            return pars.size();
        }

        @Override
        public ZLTextParagraph getParagraph(int index) {
            return pars.get(index);
        }

        @Override
        public void removeAllMarks() {
        }

        @Override
        public ZLTextMark getFirstMark() {
            return null;
        }

        @Override
        public ZLTextMark getLastMark() {
            return null;
        }

        @Override
        public ZLTextMark getNextMark(ZLTextMark position) {
            return null;
        }

        @Override
        public ZLTextMark getPreviousMark(ZLTextMark position) {
            return null;
        }

        @Override
        public List<ZLTextMark> getMarks() {
            return new ArrayList<>();
        }

        @Override
        public int getTextLength(int index) {
            return 0;
        }

        @Override
        public int findParagraphByTextLength(int length) {
            return 0;
        }

        @Override
        public int search(String text, int startIndex, int endIndex, boolean ignoreCase) {
            return 0;
        }
    }

    public DjvuPlugin() {
        super(FBReaderApp.Instance().SystemInfo, EXT);
    }

    @Override
    public void readMetainfo(AbstractBook book) throws BookReadingException {
        ZLFile f = BookUtil.fileByBook(book);
        try {
            FileInputStream is = new FileInputStream(f.getPath());
            DjvuLibre doc = new DjvuLibre(is.getFD());
            book.setTitle(doc.getTitle());
            book.addAuthor(doc.getAuthor());
            doc.close();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readUids(AbstractBook book) throws BookReadingException {
    }

    @Override
    public void detectLanguageAndEncoding(AbstractBook book) throws BookReadingException {
    }

    @Override
    public ZLImage readCover(ZLFile file) {
        DjvuView view = new DjvuView(file);
        view.current.scale(Storage.COVER_SIZE, Storage.COVER_SIZE); // reduce render memory footprint
        Bitmap bm = Bitmap.createBitmap(view.current.pageBox.w, view.current.pageBox.h, Bitmap.Config.ARGB_8888);
        view.drawOnBitmap(bm, bm.getWidth(), bm.getHeight(), ZLViewEnums.PageIndex.current);
        view.close();
        return new ZLBitmapImage(bm);
    }

    @Override
    public String readAnnotation(ZLFile file) {
        return null;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public EncodingCollection supportedEncodings() {
        return null;
    }

    @Override
    public void readModel(BookModel model) throws BookReadingException {
        model.setBookTextModel(new DjvuTextModel(BookUtil.fileByBook(model.Book)));
    }
}