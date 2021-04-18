package com.gitlab.capstone.pellego.fragments.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.activities.MainActivity;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.database.PellegoDatabase;
import com.gitlab.capstone.pellego.database.daos.UserDao;
import com.gitlab.capstone.pellego.database.entities.Users;

/**********************************************
 Arturo Lara
 Login screen
 **********************************************/
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

        TextView forgotPW = view.findViewById(R.id.textView12);
        forgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
            }
        });

        Button loginBtn = view.findViewById(R.id.button2);
        EditText email = view.findViewById(R.id.editTextEmailLogin);
        EditText password = view.findViewById(R.id.editTextPasswordLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signIn(email.getText().toString(),
                        password.getText().toString(),
                        success -> {
                            Log.i("AUTHENTICATION", success.toString());
                            AWSMobileClient mobileClient = (AWSMobileClient) Amplify.Auth.getPlugin("awsCognitoAuthPlugin").getEscapeHatch();
                            try {
                                Log.i("AUTHENTICATION", mobileClient.getTokens().getIdToken().getTokenString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        },
                        error -> Log.e("AUTHENTICATION", error.toString()));
            }
        });
    }
}