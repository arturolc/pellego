package com.example.pellego.ui.termsAndConditions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Eli hebdon
 */
public class TermsAndConditionsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TermsAndConditionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Terms and Conditions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
