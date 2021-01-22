package com.example.pellego.ui.rsvp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
 Eli Hebdon

 Rapid Serial Visualization ViewModel
 **********************************************/
public class RsvpViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public boolean showDialog;

    public RsvpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Rapid Serial Visualization");
        showDialog = false;
    }

    public void clear() {
        mText = new MutableLiveData<>();
        mText.setValue("Rapid Serial Visualization");
        showDialog = false;
    }

    public LiveData<String> getText() {
        return mText;
    }
}