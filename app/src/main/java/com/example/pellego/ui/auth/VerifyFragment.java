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

import com.amplifyframework.core.Amplify;
import com.example.pellego.DatabaseHelper;
import com.example.pellego.R;
import com.example.pellego.UserModel;

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
        Button btn = view.findViewById(R.id.nextBtn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("REGISTRATION".equals(getArguments().get("type"))) {
                    nav.navigate(R.id.action_verifyFragment_to_registerFragment);
                }
                else {
                    nav.navigate(R.id.action_verifyFragment_to_passwordFragment);
                }
            }
        });


        EditText et0 = view.findViewById(R.id.editTextNumber0);
        EditText et1 = view.findViewById(R.id.editTextNumber1);
        EditText et2 = view.findViewById(R.id.editTextNumber2);
        EditText et3 = view.findViewById(R.id.editTextNumber3);
        EditText et4 = view.findViewById(R.id.editTextNumber4);
        EditText et5 = view.findViewById(R.id.editTextNumber5);

        et0.requestFocus();
        et0.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et1.requestFocus();
            }
        });

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et2.requestFocus();
            }
        });


        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et3.requestFocus();
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et4.requestFocus();
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                et5.requestFocus();
            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // verify code
            }
        });


    }


//    public void confirm(View view) {
//        EditText confirmationCode = findViewById(R.id.confirmCodeText);
//        DatabaseHelper dh = new DatabaseHelper(VerifyActivity.this, null, null, 1);
//        Amplify.Auth.confirmSignUp(
//                email,
//                confirmationCode.getText().toString(),
//                result ->
//                {
//                    dh.addUser(new UserModel(-1, firstName, lastName, email));
//                    Intent i = new Intent(VerifyActivity.this,
//                            AuthActivity.class);
//                    startActivity(i);
//                    finish();
//                    Log.i("AUTHENTICATION", result.toString());
//                },
//                error -> Log.e("AUTHENTICATION", error.toString())
//        );
//
//    }
}