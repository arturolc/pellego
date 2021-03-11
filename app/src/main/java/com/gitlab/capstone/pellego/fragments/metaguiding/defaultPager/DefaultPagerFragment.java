package com.gitlab.capstone.pellego.fragments.metaguiding.defaultPager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Eli Hebdon
 * The default pager that displays pages of text before learning
 * techniques are applied. Can adjust based on text size.
 *
 * based on AndroidReader here: https://github.com/koros/AndroidReader
 */
public class DefaultPagerFragment extends BaseFragment {

    //pager
    protected ViewPager mPager;
    protected FragmentStatePagerAdapter mPagerAdapter;
    protected static Map<String, String> mPages = new HashMap<String, String>();
    protected LinearLayout mPageIndicator;
    protected ProgressBar mProgressBar;
    protected static String mContentString = "";
    protected Display mDisplay;
    protected View root;
    private String uri;
    protected static String textSize;
    private static int padding; // temporary variable while i'm still figuring out text sizing


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // pager
        root = inflater.inflate(R.layout.fragment_default_pager, container, false);
        mPager = root.findViewById(R.id.pager);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mPageIndicator = root.findViewById(R.id.pageIndicator);
        ViewGroup textviewPage = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_pager_container, (ViewGroup) getActivity().getWindow().getDecorView().findViewById(android.R.id.content) , false);
        TextView contentTextView = (TextView) textviewPage.findViewById(R.id.mText);

        // Instantiate a ViewPager and a PagerAdapter.
        parseArgs();
        // obtaining screen dimensions
        mDisplay = getActivity().getWindowManager().getDefaultDisplay();

        // get text size setting
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        textSize = sharedPreferences.getString("text_size", "18dp");

        ViewAndPaint  vp = new ViewAndPaint((TextPaint)contentTextView.getPaint(), textviewPage, getScreenWidth(), getMaxLineCount(contentTextView), mContentString);

        PagerTask pt = new PagerTask(this);
        pt.execute(vp);
        return root;
    }

    private void parseArgs() {
        // get string by uri
        // TODO: read the file by URI
        try {
            mContentString = getArguments().getString("uri");
        } catch (Exception e) {
            Log.d("error: ", e.getMessage());
        }
        if (mContentString == null)  mContentString = getString(getArguments().getInt("string_id"));

    }

    protected static float getTextSize() {
        switch(textSize) {
            case "small":
                padding = 6;
                return 14;
            case "large":
                padding = 12;
                return 24;
            default:
                padding = 11;
                return 18;
        }
    }


    protected int getScreenWidth(){
        float horizontalMargin = getResources().getDimension(R.dimen.activity_horizontal_margin) * 2;
        int screenWidth = (int) (mDisplay.getWidth() - horizontalMargin);
        return screenWidth;
    }

    protected int getMaxLineCount(TextView view){
        float verticalMargin = convertDpToPx(getActivity(), getResources().getDimension(R.dimen.activity_vertical_margin) );
        float bottomMargin = convertDpToPx(getActivity(),  getResources().getDimension(R.dimen.activity_actionbar_margin)) ;
        int screenHeight = mDisplay.getHeight();
        int screenWidth = mDisplay.getWidth();
        TextPaint paint = view.getPaint();

        //Working Out How Many Lines Can Be Entered In The Screen
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textHeight = fm.top - fm.bottom;
        float textHeight1 = convertDpToPx(this.getActivity(), getTextSize());

        textHeight = Math.abs(textHeight);

        int maxLineCount = (int) ((screenHeight - verticalMargin - bottomMargin) / (textHeight1));

        maxLineCount -= padding;

        return maxLineCount;
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    private void initViewPager(){
        mPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), 1);
        mPager.setAdapter(mPagerAdapter);
        // Do this twice to clear the cache, just in case
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                showPageIndicator(position);
            }
        });
    }
    public void onPageProcessedUpdate(ProgressTracker progress){
        mPages = progress.pages;
        // init the pager if necessary
        if (mPagerAdapter == null){
            initViewPager();
            hideProgress();
        }else {
            ((PagerAdapter)mPagerAdapter).incrementPageCount();
        }
//        addPageIndicator(progress.totalPages);
    }

    public void onAllPagesProcessed(ProgressTracker progress){
        mPages = progress.pages;
        for (int i = 0; i < mPages.size(); i++) {
            addPageIndicator(i);
        }
    }

    private void hideProgress(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void addPageIndicator(int pageNumber) {
        View view = new View(getActivity());
        ViewGroup.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        view.setLayoutParams(params );
        view.setBackground(getResources().getDrawable(pageNumber == 0 ? R.drawable.current_page_indicator : R.drawable.indicator_background));
        view.setTag(pageNumber);
        mPageIndicator.addView(view);
    }

    protected void showPageIndicator(int position) {
        try {
            View selectedIndexIndicator = mPageIndicator.getChildAt(position);
            selectedIndexIndicator.setBackground(getResources().getDrawable(R.drawable.current_page_indicator));
            // dicolorize the neighbours
            if (position > 0){
                View leftView = mPageIndicator.getChildAt(position -1);
                leftView.setBackground(getResources().getDrawable(R.drawable.indicator_background));
            }
            if (position < mPages.size()){
                View rightView = mPageIndicator.getChildAt(position +1);
                rightView.setBackground(getResources().getDrawable(R.drawable.indicator_background));
            }

        } catch (Exception e) {
            // This usually isn't an error. Just means we reached the last page
            Log.e("Show Page Indicator", e.toString());
        }
    }

    public static String getContents(int pageNumber){
        String page = String.valueOf(pageNumber);
        String textBoundaries = mPages.get(page);
        if (textBoundaries != null) {
            String[] bounds = textBoundaries.split(",");
            int startIndex = Integer.valueOf(bounds[0]);
            int endIndex = Integer.valueOf(bounds[1]);
            return mContentString.substring(startIndex, endIndex).trim();
        }
        return "";
    }

    public static class ViewAndPaint {

        public ViewGroup textviewPage;
        public TextPaint paint;
        public int screenWidth;
        public int maxLineCount;
        public String contentString;

        public ViewAndPaint(TextPaint paint, ViewGroup textviewPage, int screenWidth, int maxLineCount, String contentString){
            this.paint = paint;
            this.textviewPage = textviewPage;
            this.maxLineCount = maxLineCount;
            this.contentString = contentString;
            this.screenWidth = screenWidth;
        }
    }

    public static class ProgressTracker {

        public int totalPages;
        public Map<String, String> pages = new HashMap<String, String>();

        public void addPage(int page, int startIndex, int endIndex) {
            String thePage = String.valueOf(page);
            String indexMarker = String.valueOf(startIndex) + "," + String.valueOf(endIndex);
            pages.put(thePage, indexMarker);
        }
    }



}