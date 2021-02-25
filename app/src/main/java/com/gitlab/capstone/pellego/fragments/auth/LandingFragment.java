package com.gitlab.capstone.pellego.fragments.auth;

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
import android.widget.TextView;

import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.R;
/**********************************************
 Arturo Lara
 Main landing screen when user opens the app for the first time
 **********************************************/
public class LandingFragment extends Fragment {

    private AuthViewModel model;

    public LandingFragment() {
        // Required empty public constructor
    }


    public static LandingFragment newInstance() {
        LandingFragment fragment = new LandingFragment();
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
        return inflater.inflate(R.layout.fragment_landing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        NavController nav = Navigation.findNavController(view);
        Button btn = view.findViewById(R.id.signupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.action_landingFragment_to_nameFragment);
            }
        });

        TextView login = view.findViewById(R.id.textView16);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.navigate(R.id.action_landingFragment_to_loginFragment);
            }
        });

        Button btnGoogle = view.findViewById(R.id.googleBtn);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signInWithSocialWebUI(
                        AuthProvider.google(),
                        getActivity(),
                        result -> Log.i("AUTHENTICATION", result.toString()),
                        error -> Log.e("AUTHENTICATION", error.toString())
                );
            }
        });

        Button btnFacebook = view.findViewById(R.id.facebookBtn);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signInWithSocialWebUI(
                        AuthProvider.facebook(),
                        getActivity(),
                        result -> Log.i("AuthQuickstart", result.toString()),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
            }
        });


    }
}