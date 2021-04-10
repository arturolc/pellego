package com.gitlab.capstone.pellego.fragments.module.overview;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.database.LearningModulesRepo;
import com.gitlab.capstone.pellego.database.UsersRepo;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**********************************************
    Chris Bordoy, Eli Hebdon, Arturo Lara

    The Modules view model
 **********************************************/

public class ModuleViewModel extends AndroidViewModel {
    private String moduleID;
    private String submoduleID;
    private final LearningModulesRepo learningModulesRepo;
    private final UsersRepo usersRepo;
    private LiveData<List<LMDescResponse>> lmDescResponse;
    private LiveData<List<SMResponse>> submoduleResponse;
    private String techniqueLabel;
    private MutableLiveData<String> moduleTitle;
    public String technique;
    private Drawable gradient;
    private boolean showSubmodulePopupDialog;
    private boolean showPopupDialog;

    public ModuleViewModel(@NonNull Application application, String moduleID) {
        super(application);
        this.learningModulesRepo = LearningModulesRepo.getInstance(application);
        this.usersRepo = UsersRepo.getInstance(application);
        this.moduleID = moduleID;
        showSubmodulePopupDialog = false;
        showPopupDialog = false;
        moduleTitle = new MutableLiveData<>();
        lmDescResponse = new MutableLiveData<>();
        submoduleResponse = new MutableLiveData<>();
    }

    public LiveData<List<LMDescResponse>> getLMDescResponse(String mid) {
        lmDescResponse = learningModulesRepo.getModuleDesc(mid);

        return lmDescResponse;
    }

    public LiveData<List<SMResponse>> getSubmodulesResponse(String MID) {
        submoduleResponse = learningModulesRepo.getSubmodules(MID);

        return submoduleResponse;
    }

    public void setSubModuleCompletion(String MID, String SMID) {
        usersRepo.setSubmoduleCompletion(MID, SMID);
    }

    public void setUserWordValues(int wordsRead, int wpm) {
        usersRepo.setUserWordValues(wordsRead, wpm);
    }

    public int getQuizTextCount(String quizText) {
        if (quizText.isEmpty() || quizText == null) {
            return 0;
        }

        String[] words = quizText.split("\\s+");

        return words.length;
    }

    public String getModuleID(){
        return moduleID;
    }

    public void setModuleID(String MID) {
        moduleID = MID;
    }

    public String getSubModuleID() { return submoduleID; }

    public void setSubModuleID(String SMID) { submoduleID = SMID; }

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

    public int[] getModuleGradientColors(String MID){
        int[] colors = new int[2];

        switch(MID) {
            case "1":
                colors[0] = 0xFFF9D976;
                colors[1] = 0xFFF39f86;
                break;
            case "2":
                colors[0] = 0xFF20BF55;
                colors[1] = 0xFF01BAEF;
                break;
            case "3":
                colors[0] = 0xFFF53844;
                colors[1] = 0xFF42378F;
                break;
            case "4":
                colors[0] = 0xFF37D5D6;
                colors[1] = 0xFF9B6DFF;
                break;
            default:
                break;
        }

        return colors;
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