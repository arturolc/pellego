package com.gitlab.capstone.pellego.fragments.auth;

import android.nfc.FormatException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**********************************************
 Arturo Lara
 Email Fragment prompting the user for their email address
 **********************************************/
public class EmailFragment extends Fragment {

    AuthViewModel model;
    public EmailFragment() {
        // Required empty public constructor
    }

    public static EmailFragment newInstance(String param1, String param2) {
        EmailFragment fragment = new EmailFragment();

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
        return inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        NavController nav = Navigation.findNavController(view);
        Button btn = view.findViewById(R.id.button7);

        btn.setEnabled(false);

        TextView et = view.findViewById(R.id.editTextEmail);
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
                // verify email before adding it to model
                String email = et.getText().toString();
                Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                Matcher m = p.matcher(email);

                if (m.matches()) {
                    model.setEmail(email);
                    Amplify.Auth.signIn(model.getEmail(), "123",
                            success -> Log.d("EmailFragment",
                                    "logged in, this should not happen"),
                            error -> {
                                try {
                                    throw error;
                                } catch (AuthException.UserNotFoundException e) {
                                    nav.navigate(R.id.action_emailFragment_to_passwordFragment);
                                } catch (AuthException e) {
                                    Snackbar sb = Snackbar.make(view, "User already exists.",
                                            BaseTransientBottomBar.LENGTH_SHORT);
                                    sb.show();
                                }
                            });
                } else {
                    Snackbar.make(view, "Please enter a valid email address.",
                            BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean verifyUserExists(String email) {
        AtomicBoolean res = new AtomicBoolean(false);
        Amplify.Auth.signIn(email, "123",
                success -> Log.d("EmailFragment", "logged in, this should not happen"),
                error -> {
                    try {
                        throw error;
                    } catch (AuthException.UserNotFoundException e) {
                        res.set(false);
                    } catch (AuthException e) {
                        res.set(true);
                        e.printStackTrace();
                    }
                });

        return res.get();
    }







}