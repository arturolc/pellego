package com.example.pellego.ui.module;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
    Chris Bordoy

    The Learn Modules view model
 **********************************************/
public class ModuleViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public boolean showDialog;
    private String moduleTitle;
    private String moduleDescription;
    private int intro_id;
    private int module_id;

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
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

    public ModuleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Rapid Serial Visualization");
        showDialog = false;
        moduleTitle = "";
        moduleDescription = "";
        intro_id = -1;
        module_id = -1;
    }

    public void clear() {
        mText = new MutableLiveData<>();
        mText.setValue("Rapid Serial Visualization");
        showDialog = false;
        moduleTitle = "";
        moduleDescription = "";
        intro_id = -1;
        module_id = -1;
    }

    public void setViewModelVars(String title, String descr, int intro_id, int module_id) {
        this.moduleTitle = title;
        this.moduleDescription = descr;
        this.intro_id = intro_id;
        this.module_id = module_id;
    }

    public LiveData<String> getText() {
        return mText;
    }
}