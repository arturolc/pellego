package com.example.pellego.ui.profile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
 Eli Hebdon

 Profile View Model
 **********************************************/
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
