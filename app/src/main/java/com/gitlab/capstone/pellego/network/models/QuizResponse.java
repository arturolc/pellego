package com.gitlab.capstone.pellego.network.models; ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*****************************************************
 * Chris Bordoy
 *
 * Model that represents a learning sub module quiz
 *****************************************************/

public class QuizResponse {

    @SerializedName("QUID")
    @Expose
    private Integer qUID;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("Answers")
    @Expose
    private List<AnswerAll> answers = null;

    public Integer getQUID() {
        return qUID;
    }

    public void setQUID(Integer qUID) {
        this.qUID = qUID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerAll> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerAll> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuizResponse{" +
                "qUID=" + qUID +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}