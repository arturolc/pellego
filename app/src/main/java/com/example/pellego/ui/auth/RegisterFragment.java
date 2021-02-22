package com.example.pellego.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.pellego.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {

    private AuthViewModel model;
    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        NavController nav = Navigation.findNavController(view);
        Button btn = view.findViewById(R.id.button9);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.loginFragment);
            }
        });
    }

//    public void register(View view) {
//        String firstName = ((EditText)findViewById(R.id.firstNameText)).getText().toString();
//        String lastName = ((EditText)findViewById(R.id.lastNameText)).getText().toString();
//        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
//        EditText password = findViewById(R.id.passwordText);
//
//        List<AuthUserAttribute> list = new ArrayList<AuthUserAttribute>();
//        list.add(new AuthUserAttribute(AuthUserAttributeKey.name(), firstName + " " + lastName));
//        list.add(new AuthUserAttribute(AuthUserAttributeKey.email(), email));
//
//        Amplify.Auth.signUp(
//                email,
//                password.getText().toString(),
//                AuthSignUpOptions.builder().userAttributes(list).build(),
//                result -> {
//                    Log.i("AUTHENTICATION", "Result: " + result.toString());
//                    Intent i  = new Intent(RegisterActivity.this, VerifyActivity.class);
//                    i.putExtra("fName", firstName);
//                    i.putExtra("lName", lastName);
//                    i.putExtra("email", email);
//                    startActivity(i);
//                },
//                error -> Log.e("AUTHENTICATION", "Sign up failed" + error.toString())
//        );
//    }
}