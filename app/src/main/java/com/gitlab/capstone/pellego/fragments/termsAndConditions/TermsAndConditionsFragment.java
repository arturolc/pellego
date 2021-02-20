package com.gitlab.capstone.pellego.fragments.termsAndConditions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gitlab.capstone.pellego.R;

/**********************************************
 Eli Hebdon and Chris Bordoy

This fragment displays the applications EULA
 **********************************************/
public class TermsAndConditionsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
        final WebView w = root.findViewById(R.id.text_terms_and_conditions);
        w.loadUrl("file:///android_asset/termsAndConditions.html");
        return root;
    }
}
