package com.gitlab.capstone.pellego.fragments.profile;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.activities.MainActivity;
import com.gitlab.capstone.pellego.app.BaseFragment;
import com.gitlab.capstone.pellego.database.entities.Users;
import com.gitlab.capstone.pellego.network.models.TotalWordsReadResponse;
import com.gitlab.capstone.pellego.fragments.library.LibraryFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gitlab.capstone.pellego.activities.MainActivity.bitmap;

/**********************************************
 Eli Hebdon, Joanna Lowry, Arturo Lara

 Profile Fragment
 **********************************************/

public class ProfileFragment extends BaseFragment {

    public static final int GET_FROM_GALLERY = 38;
    private ProfileModel profileViewModel;
    public static String profilePath = "/data/user/0/com.gitlab.capstone.pellego/app_imageDir/";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ProfileModel.getInstance(getActivity().getApplication());
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ProgressBar pgsBar = (ProgressBar)getActivity().findViewById(R.id.progress_loader);
        pgsBar.setVisibility(View.VISIBLE);
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

        Users user = profileViewModel.getUser();
        pgsBar.setVisibility(View.INVISIBLE);
        ((TextView)root.findViewById(R.id.tv_name)).setText(user.getName());
        ((TextView)root.findViewById(R.id.userName)).setText(user.getName());

        ((TextView)root.findViewById(R.id.userEmail)).setText(user.getEmail());

        profileViewModel.getTotalWordsReadResponse().observe(getViewLifecycleOwner(), new Observer<TotalWordsReadResponse>() {
           @Override
           public void onChanged(TotalWordsReadResponse response) {
               ((TextView)root.findViewById(R.id.tv_address)).setText(String.valueOf(response.getTotalWordsRead()) + " Words Read");
           }
        });

        ((TextView)root.findViewById(R.id.total_books)).setText(String.valueOf(LibraryFragment.numBooks));
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
                        storeImage(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
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