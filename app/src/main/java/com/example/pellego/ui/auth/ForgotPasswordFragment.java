package com.example.pellego.ui.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.core.Amplify;
import com.example.pellego.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**********************************************
 Arturo Lara
 Prompts the user for email address to recover password
 **********************************************/
public class ForgotPasswordFragment extends Fragment {

    AuthViewModel model;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();

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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        NavController nav = Navigation.findNavController(view);
        Button btn = view.findViewById(R.id.button4);
        EditText et = view.findViewById(R.id.editTextTextEmailAddress3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et.getText().toString();
                Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                Matcher m = p.matcher(email);

                if(!m.matches()) {
                    Snackbar.make(view, "Please provide a valid email.",
                            BaseTransientBottomBar.LENGTH_SHORT).show();
                } else {
                    model.setEmail(email);
                    // check if this is a pellego account
                    Amplify.Auth.signIn(model.getEmail(), "123",
                            success -> Log.d("ForgotPasswordFragment",
                                    "logged in, this should not happen"),
                            error -> {
                                try {
                                    throw error;
                                } catch (AuthException.UserNotFoundException e) {
                                    Snackbar sb = Snackbar.make(view,
                                            "The email is not a Pellego account",
                                            BaseTransientBottomBar.LENGTH_SHORT);
                                    sb.show();
                                } catch (AuthException e) {
                                    Amplify.Auth.resetPassword(
                                            model.getEmail(),
                                            success ->  nav.navigate(R.id.action_forgotPasswordFragment_to_verifyFragment),
                                            error2 -> Snackbar.make(view,
                                                    "There was an error updating your password.",
                                                    BaseTransientBottomBar.LENGTH_SHORT));

                                }
                            });
                }
            }
        });
    }
}