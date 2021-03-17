package com.gitlab.capstone.pellego.fragments.profile;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.gitlab.capstone.pellego.app.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gitlab.capstone.pellego.activities.MainActivity.RESULT_ADD_CATALOG;
import static com.gitlab.capstone.pellego.activities.MainActivity.RESULT_FILE;
import static com.gitlab.capstone.pellego.activities.MainActivity.TAG;


/**********************************************
 Eli Hebdon and Joanna Lowry

 Profile Fragment
 **********************************************/


public class ProfileFragment extends BaseFragment {

    public String user_name;
    public static final int GET_FROM_GALLERY = 38;
    private ProfileViewModel profileViewModel;
    private static String profilePath = "/data/user/0/com.gitlab.capstone.pellego/app_imageDir/";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        super.setupHeader(root);
        RelativeLayout usr = root.findViewById(R.id.imgUser);
        usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                loadImageFromStorage((ImageView) getActivity().findViewById(R.id.profile_image_drawer), (ImageView) root.findViewById(R.id.profile_image));

            }
        });

        getApiData(inflater, container, new ResponseCallBack() {
            public void onResponse(Object response) {
                // Update UI only after response is received &
               //set the UI
            }
        });
//        if (root.findViewById(R.id.pro))
//        loadImageFromStorage((ImageView) root.findViewById(R.id.profile_image));

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_FROM_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        CircleImageView profilePic =  getActivity().findViewById(R.id.profile_image);
                        profilePic.setImageBitmap(bitmap);
                        // save profile photo
                        storeImage(bitmap);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * Interface to to update UI after response from server is received
     */
    public interface ResponseCallBack{
        void onResponse(Object response);
    }

    private String storeImage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");
        mypath.toString();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static void loadImageFromStorage(ImageView img1, ImageView img2)
    {
        try {
            File f=new File(profilePath, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img1.setImageBitmap(b);
            img2.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

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