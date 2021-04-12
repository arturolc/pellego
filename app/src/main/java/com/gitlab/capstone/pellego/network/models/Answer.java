package com.gitlab.capstone.pellego.network.models; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*****************************************************
 * Chris Bordoy
 *
 * Model that represents a Quiz AnswerAll
 *****************************************************/

public class Answer {

    @SerializedName("QUID")
    @Expose
    private Integer qUID;
    @SerializedName("AnswerAll")
    @Expose
    private String answer;
    @SerializedName("Correct")
    @Expose
    private Integer correct;

    public Integer getQUID() {
        return qUID;
    }

    public void setQUID(Integer qUID) {
        this.qUID = qUID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "AnswerAll{" +
                "qUID=" + qUID +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                '}';
    }
}