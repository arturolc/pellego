package com.gitlab.capstone.pellego.fragments.privacypolicy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gitlab.capstone.pellego.R;


/*********************************************************
 Eli Hebdon and Chris Bordoy

 This fragment displays the application's privacy policy.
 *********************************************************/
public class PrivacyPolicyFragment extends Fragment {

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
            final WebView w = root.findViewById(R.id.text_privacy_policy);
            w.loadUrl("file:///android_asset/privacyPolicy.html");
            return root;
    }
}
