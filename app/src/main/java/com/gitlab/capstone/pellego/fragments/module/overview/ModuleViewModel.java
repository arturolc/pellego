package com.gitlab.capstone.pellego.fragments.module.overview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gitlab.capstone.pellego.database.LearningModulesRepo;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;

import java.util.List;

/**********************************************
    Chris Bordoy & Eli Hebdon

    The Learn Modules view model
 **********************************************/
public class ModuleViewModel extends AndroidViewModel {
    private int moduleID;
    private List<LMDescResponse> resp;
    private LearningModulesRepo repo;
    private LiveData<List<LMDescResponse>> lmDescResponse;

    public ModuleViewModel(@NonNull Application application, List<LMDescResponse> resp, int moduleID) {
        super(application);
        this.resp = resp;
        this.repo = LearningModulesRepo.getInstance(application);
        this.moduleID = moduleID;
    }

    public LiveData<List<LMDescResponse>> getLMDescResponse() {
        return lmDescResponse;
    }


    public boolean showSubmodulePopupDialog;
    public boolean showPopupDialog;
    private MutableLiveData<String> moduleTitle;
    private String moduleDescription;
    private int intro_id;
    private int intro_header_id;
    private int intro_content_id;
    private int module_id;
    public String technique;
    private int[] gradient;

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public int getIntro_header_id() {
        return intro_header_id;
    }

    public void setIntro_header_id(int intro_header_id) {
        this.intro_header_id = intro_header_id;
    }

    public int getIntro_content_id() {
        return intro_content_id;
    }

    public void setIntro_content_id(int intro_content_id) {
        this.intro_content_id = intro_content_id;
    }

    public MutableLiveData<String> getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(MutableLiveData<String> moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public void setIntroMessages() {

    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public int getIntro_id() {
        return intro_id;
    }

    public void setIntro_id(int intro_id) {
        this.intro_id = intro_id;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int[] getGradient() {
        return gradient;
    }

    public void setGradient(int[] gradient) {
        this.gradient = gradient;
    }

//    public ModuleViewModel() {
//        showSubmodulePopupDialog = false;
//        showPopupDialog = false;
//        moduleTitle = new MutableLiveData<>();
//        moduleDescription = "";
//        intro_id = -1;
//        module_id = -1;
//        gradient = new int[] {};
//    }

    public void clear() {
        showSubmodulePopupDialog = false;
        showPopupDialog = false;
        moduleTitle = new MutableLiveData<>();
        moduleDescription = "";
        intro_id = -1;
        module_id = -1;
        gradient = new int[] {};
    }

    public void setViewModelVars(String title, String descr, int intro_id, int intro_content_id, int intro_header_id, int module_id) {
        this.moduleTitle = new MutableLiveData<>(title);
        this.moduleDescription = descr;
        this.intro_id = intro_id;
        this.intro_content_id = intro_content_id;
        this.intro_header_id = intro_header_id;
        this.module_id = module_id;
    }

    public LiveData<String> getText() {

        return  moduleTitle;
    }
}