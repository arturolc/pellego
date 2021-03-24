package com.gitlab.capstone.pellego.fragments.module.overview;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.database.LearningModulesRepo;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;

import java.util.List;

/**********************************************
    Chris Bordoy, Eli Hebdon, Arturo Lara

    The Modules view model
 **********************************************/

public class ModuleViewModel extends AndroidViewModel {
    private final String moduleID;
    private final LearningModulesRepo repo;
    private LiveData<List<LMDescResponse>> lmDescResponse = new MutableLiveData<>();
    private LiveData<List<SMResponse>> submoduleResponse = new MutableLiveData<>();
    private String techniqueLabel;
    private MutableLiveData<String> moduleTitle;
    public String technique;
    private Drawable gradient;
    private boolean showSubmodulePopupDialog;
    private boolean showPopupDialog;

    public ModuleViewModel(@NonNull Application application, String moduleID) {
        super(application);
        this.repo = LearningModulesRepo.getInstance(application);
        this.moduleID = moduleID;
        showSubmodulePopupDialog = false;
        showPopupDialog = false;
    }

    public LiveData<List<LMDescResponse>> getLMDescResponse(String mid) {
        if (lmDescResponse.getValue() == null) {
            lmDescResponse = repo.getModuleDesc(mid);
        }

        return lmDescResponse;
    }

    public LiveData<List<SMResponse>> getSubmodulesResponse(String MID) {
        if (submoduleResponse.getValue() == null) {
            submoduleResponse = repo.getSubmodules(MID);
        }

        return submoduleResponse;
    }

    public String getModuleID(){
        return moduleID;
    }

    public void setShowPopupDialog(boolean showSubmodulePopupDialog) {
        this.showPopupDialog = showSubmodulePopupDialog;
    }

    public boolean isShowSubmodulePopupDialog() {
        return showSubmodulePopupDialog;
    }

    public void setShowSubmodulePopupDialog(boolean showSubmodulePopupDialog) {
        this.showSubmodulePopupDialog = showSubmodulePopupDialog;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechniqueLabel(String MID) {
        switch(MID) {
            case "1":
                techniqueLabel = "rsvp";
                break;
            case "2":
                techniqueLabel = "metaguiding";
                break;
            default:
                break;
        }
    }

    public String getTechniqueLabel() {
        return techniqueLabel;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public Drawable getGradient() {
        return gradient;
    }

    public void setGradient(Drawable gradient) {
        this.gradient = gradient;
    }

    public void clear() {
        showSubmodulePopupDialog = false;
        showPopupDialog = false;
        moduleTitle = new MutableLiveData<>();
        gradient = null;
    }

    public LiveData<String> getText() {

        return  moduleTitle;
    }
}