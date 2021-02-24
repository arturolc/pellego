package com.example.pellego.ui.auth;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.pellego.DatabaseHelper;
import com.example.pellego.R;
import com.example.pellego.UserModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
/**********************************************
 Arturo Lara
 email verification
 **********************************************/
public class VerifyFragment extends Fragment {

    AuthViewModel model;

    public VerifyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VerifyFragment newInstance() {
        VerifyFragment fragment = new VerifyFragment();
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
        return inflater.inflate(R.layout.fragment_verify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        NavController nav = Navigation.findNavController(view);

        // send verification code
        List<AuthUserAttribute> list = new ArrayList<AuthUserAttribute>();
        list.add(new AuthUserAttribute(AuthUserAttributeKey.name(), model.getName()));
        list.add(new AuthUserAttribute(AuthUserAttributeKey.email(), model.getEmail()));

        if ("REGISTRATION".equals(getArguments().get("type"))) {
            Amplify.Auth.signUp(
                    model.getEmail(),
                    model.getPassword(),
                    AuthSignUpOptions.builder().userAttributes(list).build(),
                    result -> {
                        Log.i("AUTHENTICATION", "Result: " + result.toString());


                    },
                    error -> Log.e("AUTHENTICATION", "Sign up failed" + error.toString())
            );
        }

        Button btn = view.findViewById(R.id.nextBtn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("REGISTRATION".equals(getArguments().get("type"))) {
                    nav.navigate(R.id.action_verifyFragment_to_registerFragment);
                } else {
                    nav.navigate(R.id.action_verifyFragment_to_passwordFragment);
                }
            }
        });
        btn.setEnabled(false);

        TextView tv = view.findViewById(R.id.textViewSendNewCode);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send a new code
                Amplify.Auth.resendUserAttributeConfirmationCode(AuthUserAttributeKey.email(),
                        result -> Log.i("AuthDemo", "Code was sent again: " + result.toString()),
                        error -> Log.e("AuthDemo", "Failed to resend code.", error)
                );

                Amplify.Auth.resendSignUpCode(model.getEmail(),
                        success -> Snackbar.make(view, "Verification code resent.",
                                BaseTransientBottomBar.LENGTH_SHORT).show(),
                        error -> Snackbar.make(view, "Failed to send verification code",
                                BaseTransientBottomBar.LENGTH_SHORT).show());
            }
        });

        EditText et0 = view.findViewById(R.id.editTextNumber0);
        EditText et1 = view.findViewById(R.id.editTextNumber1);
        EditText et2 = view.findViewById(R.id.editTextNumber2);
        EditText et3 = view.findViewById(R.id.editTextNumber3);
        EditText et4 = view.findViewById(R.id.editTextNumber4);
        EditText et5 = view.findViewById(R.id.editTextNumber5);

        et0.setText("");
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");

        et0.requestFocus();
        et0.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et1.requestFocus();
            }
        });

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et2.requestFocus();
            }
        });


        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et3.requestFocus();
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et4.requestFocus();
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et5.requestFocus();
            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                StringBuilder sb = new StringBuilder();
                sb.append(et0.getText().toString());
                sb.append(et1.getText().toString());
                sb.append(et2.getText().toString());
                sb.append(et3.getText().toString());
                sb.append(et4.getText().toString());
                sb.append(et5.getText().toString());

                String confirmationCode = sb.toString();

                if ("REGISTRATION".equals(getArguments().get("type"))) {

                    Amplify.Auth.confirmSignUp(
                            model.getEmail(),
                            confirmationCode,
                            result ->
                            {
                                nav.navigate(R.id.action_verifyFragment_to_registerFragment);
                            },
                            error -> Log.e("AUTHENTICATION", error.toString())
                    );
                } else {
                    model.setConfirmationCode(confirmationCode);
                    nav.navigate(R.id.action_verifyFragment_to_passwordFragment);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }
}