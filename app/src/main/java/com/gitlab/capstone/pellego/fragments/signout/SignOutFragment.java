package com.gitlab.capstone.pellego.fragments.signout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gitlab.capstone.pellego.R;

/**********************************************
 Eli Hebdon

Sign Out Fragment
 **********************************************/
public class SignOutFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_out, container, false);
        final TextView textView = root.findViewById(R.id.text_sign_out);
        textView.setText("Sign Out");
        // sign out logic


        return root;
    }
}
