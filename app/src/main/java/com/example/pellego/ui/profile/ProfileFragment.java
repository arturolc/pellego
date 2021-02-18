package com.example.pellego.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pellego.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.util.Log;
import android.os.Build;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**********************************************
 Eli Hebdon and Joanna Lowry

 Profile Fragment
 **********************************************/


public class ProfileFragment extends Fragment {

    private TextView textViewResult;
    private String user_name;

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        textViewResult = root.findViewById(R.id.text_view_result);

        //http://54.176.198.201:5001/
        //http://jsonplaceholder.typicode.com/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        Call<List<PostObject>> call = jsonPlaceHolderAPI.getPosts();
        call.enqueue(new Callback<List<PostObject>>() {

            @Override
            public void onResponse(Call<List<PostObject>> call, retrofit2.Response<List<PostObject>> response) {
                //May get a response back from server but may be a 404 request so check for it
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                //Else if its successful, then we populate data for PostObject
                List<PostObject> posts = response.body();
                for (PostObject post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserid() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText();

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<PostObject>> call, Throwable t) {
                //This is a bad example, only meant for debugging purposes
                textViewResult.setText(t.getMessage());
            }
        });

        /*        Call<UserObject> call = jsonPlaceHolderAPI.getUserName();
        call.enqueue(new Callback<UserObject>() {

            @Override
            public void onResponse(Call<UserObject> call, retrofit2.Response<UserObject> response) {
                //May get a response back from server but may be a 404 request so check for it
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                //Else if its successful, then we populate data for PostObject
                UserObject user = response.body();
                textViewResult.append(user.getUserName());
            }

            @Override
            public void onFailure(Call<UserObject> call, Throwable t) {
                //This is a bad example, only meant for debugging purposes
                textViewResult.setText(t.getMessage());
            }
        });*/
        return root;
    }

    /**
     * Interface to to update UI after response from server is received
     */
    public interface ResponseCallBack{
        void onResponse(Object response);
    }


/*    private void getApiData(@NonNull LayoutInflater inflater, ViewGroup container, ResponseCallBack responseCallBack)
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
    }*/

}