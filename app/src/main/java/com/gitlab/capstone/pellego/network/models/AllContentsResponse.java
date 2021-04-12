package com.gitlab.capstone.pellego.network.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllContentsResponse {

    @SerializedName("modules")
    @Expose
    private List<Module> modules = null;
    @SerializedName("intros")
    @Expose
    private List<Intro> intros = null;
    @SerializedName("submodule")
    @Expose
    private List<Submodule> submodule = null;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("answers")
    @Expose
    private List<AnswerAll> answers = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AllContentsResponse() {
    }

    public AllContentsResponse(List<Module> modules, List<Intro> intros, List<Submodule> submodule, List<Question> questions, List<AnswerAll> answers) {
        super();
        this.modules = modules;
        this.intros = intros;
        this.submodule = submodule;
        this.questions = questions;
        this.answers = answers;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Intro> getIntros() {
        return intros;
    }

    public void setIntros(List<Intro> intros) {
        this.intros = intros;
    }

    public List<Submodule> getSubmodule() {
        return submodule;
    }

    public void setSubmodule(List<Submodule> submodule) {
        this.submodule = submodule;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<AnswerAll> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerAll> answers) {
        this.answers = answers;
    }

}
