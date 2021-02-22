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
    }


    public void confirm(View view) {
        EditText confirmationCode = findViewById(R.id.confirmCodeText);
        DatabaseHelper dh = new DatabaseHelper(VerifyActivity.this, null, null, 1);
        Amplify.Auth.confirmSignUp(
                email,
                confirmationCode.getText().toString(),
                result ->
                {
                    dh.addUser(new UserModel(-1, firstName, lastName, email));
                    Intent i = new Intent(VerifyActivity.this,
                            AuthActivity.class);
                    startActivity(i);
                    finish();
                    Log.i("AUTHENTICATION", result.toString());
                },
                error -> Log.e("AUTHENTICATION", error.toString())
        );

    }
}