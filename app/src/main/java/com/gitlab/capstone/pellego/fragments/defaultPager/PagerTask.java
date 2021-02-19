package com.gitlab.capstone.pellego.fragments.defaultPager;


import android.os.AsyncTask;
import android.text.TextPaint;


/**
 * Eli Hebdon
 * Background task that reads text and loads it into the reader, updating page number and views as it goes
 *
 * based on AndroidReader here: https://github.com/koros/AndroidReader
 */
public class PagerTask extends AsyncTask<DefaultPagerFragment.ViewAndPaint, DefaultPagerFragment.ProgressTracker, DefaultPagerFragment.ProgressTracker> {

    private DefaultPagerFragment defaultPagerFragment;

    public PagerTask(DefaultPagerFragment defaultPagerFragment){
        this.defaultPagerFragment = defaultPagerFragment;
    }

    protected DefaultPagerFragment.ProgressTracker doInBackground(DefaultPagerFragment.ViewAndPaint... vps) {

        DefaultPagerFragment.ViewAndPaint vp = vps[0];
        DefaultPagerFragment.ProgressTracker progress = new DefaultPagerFragment.ProgressTracker();
        TextPaint paint = vp.paint;
        int numChars = 0;
        int lineCount = 0;
        int maxLineCount = vp.maxLineCount;
        int totalCharactersProcessedSoFar = 0;

        // contentString is the whole string of the book
        int totalPages = 0;
        while (vp.contentString != null && vp.contentString.length() != 0 )
        {
            while ((lineCount < maxLineCount) && (numChars < vp.contentString.length())) {
                numChars = numChars + paint.breakText(vp.contentString.substring(numChars), true, vp.screenWidth, null);
                lineCount ++;
            }

            // Retrieve the String to be displayed in the current textview
            String stringToBeDisplayed = vp.contentString.substring(0, numChars);
            int nextIndex = numChars;
            char nextChar = nextIndex < vp.contentString.length() ? vp.contentString.charAt(nextIndex) : ' ';
            if (!Character.isWhitespace(nextChar)) {
                stringToBeDisplayed = stringToBeDisplayed.substring(0, stringToBeDisplayed.lastIndexOf(" "));
            }
            numChars = stringToBeDisplayed.length();
            vp.contentString = vp.contentString.substring(numChars);

            // publish progress
            progress.totalPages = totalPages;
            progress.addPage(totalPages, totalCharactersProcessedSoFar, totalCharactersProcessedSoFar + numChars);
            publishProgress(progress);

            totalCharactersProcessedSoFar += numChars;

            // reset per page items
            numChars = 0;
            lineCount = 0;

            // increment  page counter
            totalPages ++;
        }

        return progress;
    }

    @Override
    protected void onProgressUpdate(DefaultPagerFragment.ProgressTracker... values) {
        defaultPagerFragment.onPageProcessedUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(DefaultPagerFragment.ProgressTracker result) {
        defaultPagerFragment.onAllPagesProcessed(result);
    }


}
