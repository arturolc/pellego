package com.gitlab.capstone.pellego.fragments.rsvp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.app.Plugin;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.fragments.module.overview.ModuleViewModel;
import com.gitlab.capstone.pellego.widgets.FBReaderView;
import com.gitlab.capstone.pellego.widgets.PagerWidget;
import com.gitlab.capstone.pellego.widgets.ScrollWidget;
import com.gitlab.capstone.pellego.widgets.SelectionView;
import com.gitlab.capstone.pellego.widgets.TTSPopup;

import org.geometerplus.zlibrary.text.view.ZLTextElement;
import org.geometerplus.zlibrary.text.view.ZLTextFixedPosition;
import org.geometerplus.zlibrary.text.view.ZLTextParagraphCursor;
import org.geometerplus.zlibrary.text.view.ZLTextPosition;
import org.geometerplus.zlibrary.text.view.ZLTextWord;
import org.geometerplus.zlibrary.text.view.ZLTextWordCursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**********************************************
 Eli Hebdon and Chris Bordoy

 RSVP module fragment that displays words, one at a time, at 'wpm'
 **********************************************/
public class RsvpModuleFragment extends BaseFragment {

    private View root;
    private Integer wpm;
    public String difficulty;
    private static AsyncUpdateText asyncUpdateText;
    private String content;
    private ModuleViewModel moduleViewModel;
    private FragmentActivity currentActivity;

    public static FBReaderView fb;
    static TTSPopup.Fragment fragment;
    public static Storage.Bookmarks marks = new Storage.Bookmarks();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        try {
            wpm = Integer.parseInt(getArguments().getString("wpm"));
            difficulty = getArguments().getString("difficulty");
            // Set the displayed text to the appropriate level
            switch(difficulty) {
                case "Beginner Submodule":
                    content = getString(R.string.content_rsvp_beginner);
                    break;
                case "Intermediate Submodule":
                    content = getString(R.string.content_rsvp_intermediate);
                    break;
                case "Advanced Submodule":
                    content = getString(R.string.content_rsvp_advanced);
                    break;
            }
        } catch (Exception e) {

            content = selectNextChunk();
            Log.d("chunk" , content);

        }

        currentActivity = getActivity();
        moduleViewModel =
                new ViewModelProvider(requireActivity()).get(ModuleViewModel.class);


        root = inflater.inflate(R.layout.fragment_rsvp_module, container, false);
        final TextView textView = root.findViewById(R.id.title_rsvp);



        // Only show popup if user navigated to the Rsvp module
       if (moduleViewModel.showSubmodulePopupDialog) showSubmodulePopupDialog();
        return root;
    }

    // Get the next chunk of text
    public static String selectNextChunk() {
        marks.clear();
        if (fragment == null) {
            if (fb.widget instanceof ScrollWidget) {
                int first = ((ScrollWidget) fb.widget).findFirstPage();
                ScrollWidget.ScrollAdapter.PageCursor c = ((ScrollWidget) fb.widget).adapter.pages.get(first);
                Storage.Bookmark bm = TTSPopup.expandWord(new Storage.Bookmark("", c.start, c.start));
                fragment = new TTSPopup.Fragment(bm);
            }
            if (fb.widget instanceof PagerWidget) {
                ZLTextPosition position = fb.getPosition();
                Storage.Bookmark bm = TTSPopup.expandWord(new Storage.Bookmark("", position, position));
                fragment = new TTSPopup.Fragment(bm);
            }
        } else {
            Storage.Bookmark bm = selectNextChunk(fragment.fragment);
            fragment = new TTSPopup.Fragment(bm);
        }
        marks.add(fragment.fragment);

        return fragment.fragmentText;
    }

    public static Storage.Bookmark selectNextChunk(Storage.Bookmark bm) {
        if (fb.pluginview != null) {
            ZLTextPosition start = bm.end;
            TTSPopup.PluginWordCursor k = new TTSPopup.PluginWordCursor(start);
            if (k.nextWord()) {
                ZLTextPosition end = TTSPopup.expandRight(k);
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
            ZLTextPosition end = TTSPopup.expandRight(start);
            return new Storage.Bookmark(bm.text, start, end);
        }
    }


//    public Fragment(Storage.Bookmark bm) {
//        String str = "";
//        ArrayList<TTSPopup.Fragment.Bookmark> list = new ArrayList<>();
//        if (fb.pluginview != null) {
//            ZLTextPosition start = bm.start;
//            ZLTextPosition end = bm.end;
//            TTSPopup.PluginWordCursor k = new TTSPopup.PluginWordCursor(start);
//            if (k.nextWord()) {
//                while (k.compareTo(end) <= 0) {
//                    TTSPopup.Fragment.Bookmark b = new TTSPopup.Fragment.Bookmark(k.getText(), new ZLTextFixedPosition(start), new ZLTextFixedPosition(k));
//                    b.strStart = str.length();
//                    str += k.getText();
//                    b.strEnd = str.length();
//                    str += " ";
//                    list.add(b);
//                    start = new ZLTextFixedPosition(k);
//                    k.nextWord();
//                }
//            }
//            k.close();
//        } else {
//            ZLTextParagraphCursor paragraphCursor = new ZLTextParagraphCursor(fb.app.Model.getTextModel(), bm.start.getParagraphIndex());
//            ZLTextWordCursor wordCursor = new ZLTextWordCursor(paragraphCursor);
//            wordCursor.moveTo(bm.start);
//            for (ZLTextElement e = wordCursor.getElement(); wordCursor.compareTo(bm.end) < 0; e = wordCursor.getElement()) {
//                if (e instanceof ZLTextWord) {
//                    String z = ((ZLTextWord) e).getString();
//                    TTSPopup.Fragment.Bookmark b = new TTSPopup.Fragment.Bookmark(z, new ZLTextFixedPosition(wordCursor), new ZLTextFixedPosition(wordCursor.getParagraphIndex(), wordCursor.getElementIndex(), wordCursor.getCharIndex() + ((ZLTextWord) e).Length));
//                    b.strStart = str.length();
//                    str += z;
//                    b.strEnd = str.length();
//                    str += " ";
//                    list.add(b);
//                }
//                wordCursor.nextWord();
//            }
//        }
//        fragmentText = str;
//        fragmentWords = list;
//        fragment = new Storage.Bookmark(bm);
//        fragment.color = TTS_BG_COLOR;
//        word = null;
//    }
//
//    public Storage.Bookmark findWord(int start, int end) {
//        for (TTSPopup.Fragment.Bookmark bm : fragmentWords) {
//            if (bm.strStart == start)
//                return bm;
//        }
//        return null;
//    }
//
//    public boolean isEmpty() {
//        if (fragment == null || fragmentText == null)
//            return true;
//        return fragmentText.trim().length() == 0;
//    }
//}



    private void showSubmodulePopupDialog() {
        // Setup the custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ok_dialog);
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(R.string.submodule_popup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                asyncUpdateText = new AsyncUpdateText(); // start thread on ok
                asyncUpdateText.execute(wpm);
            }
        });
        dialog.show();
        moduleViewModel.showSubmodulePopupDialog = false;
    }

    private void showQuizPopupDialog() {
        // Setup the custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ok_dialog);
        ((TextView) dialog.findViewById(R.id.text_dialog)).setText(R.string.quiz_popup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                NavController navController = Navigation.findNavController(currentActivity, R.id.nav_host_fragment);
                Bundle args = new Bundle();
                args.putString("difficulty", difficulty);
                args.putString("wpm", String.valueOf(wpm));
                args.putString("module", "rsvp");
                navController.navigate(R.id.nav_quiz, args);
            }
        });
        dialog.show();
        moduleViewModel.showPopupDialog = false;
    }

    /**
     * Asynchronously updates the text in the RSVP fragment at the provided WPM rate
     */
    private class AsyncUpdateText extends AsyncTask<Integer, String, Integer> {

        TextView rsvp_text;
        String[] words = content.split ("\\W+"); // split on non-word characters

        @Override
        protected void onPreExecute() {
            rsvp_text = root.findViewById(R.id.text_rsvp);
        }

        @Override
        protected Integer doInBackground(Integer... ints) {
            long delay = (long) ((60.0 / (float) ints[0]) * 1000);
            for (String word : words) {
                // Verify that user has not navigated away from the RSVP fragment
                NavHostFragment navHostFragment = (NavHostFragment) currentActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                if (!navHostFragment.getChildFragmentManager().getFragments().get(0).toString().contains("RsvpModuleFragment")) {
                    cancel(true);
                    return 0;
                } else {
                    rsvp_text.setText(word);
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            try {
                showQuizPopupDialog();
            } catch (Exception e) {
                Log.d("error" , e.getMessage());
            }

        }
    }
}

