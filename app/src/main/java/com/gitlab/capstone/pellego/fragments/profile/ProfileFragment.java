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

import com.amplifyframework.core.Amplify;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.activities.MainActivity;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.app.Storage;
import com.gitlab.capstone.pellego.fragments.library.LibraryFragment;
import com.google.android.material.navigation.NavigationView;

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
import static com.gitlab.capstone.pellego.activities.MainActivity.bitmap;


/**********************************************
 Eli Hebdon and Joanna Lowry

 Profile Fragment
 **********************************************/


public class ProfileFragment extends BaseFragment {

    public String user_name;
    public static final int GET_FROM_GALLERY = 38;
    private ProfileViewModel profileViewModel;
    public static String profilePath = "/data/user/0/com.gitlab.capstone.pellego/app_imageDir/";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        super.setupHeader(root);
        ImageView im1 = (ImageView) root.findViewById(R.id.profile_image);
        RelativeLayout usr = root.findViewById(R.id.imgUser);
        usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                MainActivity.loadImageFromStorage(getActivity());
            }
        });


        im1.setImageBitmap(bitmap);
        profileViewModel.getUserName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ((TextView)root.findViewById(R.id.tv_name)).setText(s);
                ((TextView)root.findViewById(R.id.userName)).setText(s);
            }
        });

        profileViewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ((TextView)root.findViewById(R.id.userEmail)).setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_FROM_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        CircleImageView profilePic =  getActivity().findViewById(R.id.profile_image);
                        profilePic.setImageBitmap(bitmap);
                        NavigationView navView = (NavigationView) getActivity().findViewById(R.id.side_nav_view);
                        View headerView = navView.getHeaderView(0);
                        ImageView im2 = (ImageView) headerView.findViewById(R.id.profile_image_drawer);
                        im2.setImageBitmap(bitmap);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.loadImageFromStorage(getActivity());
    }

}