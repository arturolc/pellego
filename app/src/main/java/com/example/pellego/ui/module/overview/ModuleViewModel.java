package com.example.pellego.ui.module;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**********************************************
    Chris Bordoy & Eli Hebdon

    The Learn Modules view model
 **********************************************/
public class ModuleViewModel extends ViewModel {

    public boolean showDialog;
    private MutableLiveData<String> moduleTitle;
    private String moduleDescription;
    private int intro_id;
    private int intro_header_id;
    private int intro_content_id;
    private int module_id;

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

    public ModuleViewModel() {
        showDialog = false;
        moduleTitle = new MutableLiveData<>();
        moduleDescription = "";
        intro_id = -1;
        module_id = -1;
    }

    public void clear() {
        showDialog = false;
        moduleTitle = new MutableLiveData<>();
        moduleDescription = "";
        intro_id = -1;
        module_id = -1;
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