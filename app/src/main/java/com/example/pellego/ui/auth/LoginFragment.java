package com.example.pellego.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pellego.R;


public class LoginFragment extends Fragment {

    private AuthViewModel model;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        NavController nav = Navigation.findNavController(view);

        TextView login = view.findViewById(R.id.textView12);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
            }
        });
    }

    public void signIn(View view) {
        EditText email = view.findViewById(R.id.editTextTextEmailAddress);
////        EditText password = view.findViewById(R.id.editTextTextPassword);
//
//        Amplify.Auth.signIn(email.getText().toString(),
//                password.getText().toString(),
//                success -> {
//                    Log.i("AUTHENTICATION", success.toString());
////                    Intent i = new Intent(AuthActivity.this, HomeActivity.class);
////                    startActivity(i);
////                    finish();
//                },
//                error -> Log.e("AUTHENTICATION", error.toString()));
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