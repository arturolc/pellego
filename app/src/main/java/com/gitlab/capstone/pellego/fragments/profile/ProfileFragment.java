package com.gitlab.capstone.pellego.fragments.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gitlab.capstone.pellego.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**********************************************
 Eli Hebdon and Joanna Lowry

 Profile Fragment
 **********************************************/


public class ProfileFragment extends Fragment {

    public String user_name;

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);

        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        getApiData(inflater, container, new ResponseCallBack() {
            public void onResponse(Object response) {
                // Update UI only after response is received &
               //set the UI
                final TextView view = root.findViewById(R.id.user_name);
                view.setText(user_name);
            }
        });
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        setHasOptionsMenu(false);
        toolbar.setVisibility(View.INVISIBLE);

        return root;
    }

    /**
     * Interface to to update UI after response from server is received
     */
    public interface ResponseCallBack{
        void onResponse(Object response);
    }


    private void getApiData(@NonNull LayoutInflater inflater, ViewGroup container, ResponseCallBack responseCallBack)
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://54.176.198.201:5001/user";

        // Request a json response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject item = response.getJSONObject(i);
                                user_name = item.get("Name").toString();

                             } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        responseCallBack.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("error", error.toString());

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

}