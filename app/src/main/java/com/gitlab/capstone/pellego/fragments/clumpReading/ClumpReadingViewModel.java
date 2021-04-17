package com.gitlab.capstone.pellego.fragments.clumpReading;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
 Eli Hebdon

 Clump Reading View Model
 **********************************************/
public class ClumpReadingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClumpReadingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Clump Reading");
    }

    public LiveData<String> getText() {
        return mText;
    }
}