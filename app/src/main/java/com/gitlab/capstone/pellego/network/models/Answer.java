package com.gitlab.capstone.pellego.network.models; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*****************************************************
 * Chris Bordoy
 *
 * Model that represents a Quiz AnswerAll
 *****************************************************/

public class Answer {
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("Correct")
    @Expose
    private Integer correct;

    public Answer(String answer, Integer correct) {
        this.answer = answer;
        this.correct = correct;
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
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", correct=" + correct +
                '}';
    }
}