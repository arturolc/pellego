package com.example.pellego.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.core.Amplify;
import com.example.pellego.HomeActivity;
import com.example.pellego.R;
import com.example.pellego.RegisterActivity;


public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

//    // TODO: Rename and change types and number of parameters
//    public static LoginFragment newInstance(String param1, String param2) {
//        LoginFragment fragment = new LoginFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this °
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void signIn(View view) {
        EditText email = view.findViewById(R.id.editTextTextEmailAddress);
//        EditText password = view.findViewById(R.id.editTextTextPassword);

        Amplify.Auth.signIn(email.getText().toString(),
                password.getText().toString(),
                success -> {
                    Log.i("AUTHENTICATION", success.toString());
//                    Intent i = new Intent(AuthActivity.this, HomeActivity.class);
//                    startActivity(i);
//                    finish();
                },
                error -> Log.e("AUTHENTICATION", error.toString()));
    }

    public void moveToRegister(View view) {
//        Intent i = new Intent(AuthActivity.this,
//                RegisterActivity.class);
//        startActivity(i);
//        finish();
    }

    public void googleSignIn(View view) {
//        Amplify.Auth.signInWithSocialWebUI(
//                AuthProvider.google(),
//                this,
//                result -> Log.i("AUTHENTICATION", result.toString()),
//                error -> Log.e("AUTHENTICATION", error.toString())
//        );
    }
}