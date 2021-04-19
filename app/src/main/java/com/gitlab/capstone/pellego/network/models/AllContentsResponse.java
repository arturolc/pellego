package com.gitlab.capstone.pellego.network.models;

import com.gitlab.capstone.pellego.database.entities.Answers;
import com.gitlab.capstone.pellego.database.entities.LM_Intro;
import com.gitlab.capstone.pellego.database.entities.LM_Module;
import com.gitlab.capstone.pellego.database.entities.LM_Submodule;
import com.gitlab.capstone.pellego.database.entities.Questions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/****************************************
 * Arturo Lara
 *
 * Represents an all contents object
 * for use with data caching
 ***************************************/

public class AllContentsResponse {

    @SerializedName("modules")
    @Expose
    private List<LM_Module> modules;
    @SerializedName("intros")
    @Expose
    private List<LM_Intro> intros;
    @SerializedName("submodule")
    @Expose
    private List<LM_Submodule> submodule;
    @SerializedName("questions")
    @Expose
    private List<Questions> questions;
    @SerializedName("answers")
    @Expose
    private List<Answers> answers;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AllContentsResponse() {
    }

    public AllContentsResponse(List<LM_Module> modules, List<LM_Intro> intros, List<LM_Submodule> submodule, List<Questions> questions, List<Answers> answers) {
        this.modules = modules;
        this.intros = intros;
        this.submodule = submodule;
        this.questions = questions;
        this.answers = answers;
    }

    public List<LM_Module> getModules() {
        return modules;
    }

    public void setModules(List<LM_Module> modules) {
        this.modules = modules;
    }

    public List<LM_Intro> getIntros() {
        return intros;
    }

    public void setIntros(List<LM_Intro> intros) {
        this.intros = intros;
    }

    public List<LM_Submodule> getSubmodule() {
        return submodule;
    }

    public void setSubmodule(List<LM_Submodule> submodule) {
        this.submodule = submodule;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public List<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }
}
