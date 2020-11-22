package com.example.pellego.ui.profile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Eli hebdon
 */
public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Profile");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
