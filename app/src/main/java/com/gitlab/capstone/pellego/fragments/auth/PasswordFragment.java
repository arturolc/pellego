package com.gitlab.capstone.pellego.fragments.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**********************************************
 Arturo Lara

 prompts the user for their password
 **********************************************/

public class PasswordFragment extends Fragment {

    private AuthViewModel model;

    public PasswordFragment() {
    }

    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        NavController nav = Navigation.findNavController(view);
        Button btn = view.findViewById(R.id.button8);

        btn.setEnabled(false);

        TextView et = view.findViewById(R.id.editTextPassword);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    btn.setEnabled(true);
                }
                else {
                    btn.setEnabled(false);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verify with regex if it's a valid password
                String password = et.getText().toString();

                if (password.length() < 8) {
                    Snackbar.make(view, "Password must contain at least 8 characters.",
                            BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else {
                    model.setPassword(password);
                    Log.d("PasswordFragment", getArguments().get("type").toString());
                    if("REGISTRATION".equals(getArguments().get("type"))) {
                        nav.navigate(R.id.action_passwordFragment_to_verifyFragment);
                    }
                    else {
                        Amplify.Auth.confirmResetPassword(model.getPassword(),
                                model.getConfirmationCode(),
                                () -> nav.navigate(R.id.action_passwordFragment_to_registerFragment),
                                error -> Snackbar.make(view,
                                        error.getRecoverySuggestion(),
                                        BaseTransientBottomBar.LENGTH_SHORT).show());
                    }
                }
            }
        });
    }
}