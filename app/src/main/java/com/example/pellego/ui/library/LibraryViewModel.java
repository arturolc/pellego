package com.example.pellego.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
    Chris Bordoy

    The Progress Component
 **********************************************/
public class LibraryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LibraryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Library fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}