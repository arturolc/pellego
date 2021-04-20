package com.gitlab.capstone.pellego.fragments.profile;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amplifyframework.core.Amplify;
import com.gitlab.capstone.pellego.database.UsersRepo;
import com.gitlab.capstone.pellego.database.entities.Users;
import com.gitlab.capstone.pellego.network.models.TotalWordsReadResponse;

/**********************************************
 Eli Hebdon & Arturo Lara

 Profile Singleton Model
 **********************************************/

public class ProfileModel extends AndroidViewModel {
    private Users user;
    private static ProfileModel INSTANCE;
    private final UsersRepo usersRepo;
    private LiveData<TotalWordsReadResponse> totalWordsReadResponse;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ProfileModel(@NonNull Application application) {
        super(application);
        this.usersRepo = UsersRepo.getInstance(application);
        totalWordsReadResponse = new MutableLiveData<>();

        SharedPreferences sharedPref = application.getSharedPreferences("User", Context.MODE_PRIVATE);
        long uid = sharedPref.getLong("UserID", -1);
        String name = sharedPref.getString("UserName", "");
        String email = sharedPref.getString("UserEmail", "");
        this.user = new Users((int)uid, name, email);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static ProfileModel getInstance(@NonNull Application application) {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return new ProfileModel(application);
    }

    public LiveData<TotalWordsReadResponse> getTotalWordsReadResponse() {
        totalWordsReadResponse = usersRepo.getTotalWordsRead();

        return totalWordsReadResponse;
    }

    public Users getUser() {
        return user;
    }
}
