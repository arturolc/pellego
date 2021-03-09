package com.gitlab.capstone.pellego.fragments.quiz;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;

import java.util.ArrayList;
import java.util.Arrays;

/**********************************************
 Eli Hebdon and Chris Bordoy

 Quiz view model
 **********************************************/
public class QuizViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String difficulty;
    public static Integer question_no;
    private static ArrayList<QuizQuestion> questions;
    public static int score;
    private static String module;
    private static Integer wpm;

    public QuizViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(getResourceString(R.string.quiz_name));
        question_no = 0;
        score = 0;
        module = "";
        wpm = 0;
        questions = new ArrayList<>();
    }

    public QuizViewModel(String quiz_name) {
        mText = new MutableLiveData<>();
        mText.setValue(quiz_name);
        question_no = 0;
        score = 0;
        module = "";
        wpm = 0;
        questions = new ArrayList<>();
    }

    public void setDifficulty(String diff) {
        this.difficulty = diff;
    }
    public void setWPM(Integer wpm) {this.wpm = wpm;}
    public void setModule(String m) {this.module = m;}
    public String getModule() {return this.module;}

    public boolean isLastQuestion() {
        return question_no == questions.size() - 1;
    }

    public String getFinalScore() {
        return (score + "/" + (questions.size()));
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Integer getWPM() {
        return wpm;
    }

    public String getFinalMessage() {
        if (score == questions.size()) {
            return getResourceString(R.string.quiz_perfect_score);
        } else if ((float)score / questions.size() >= 0.75) {
            return getResourceString(R.string.quiz_mediocre_score);
        } else {
            return getResourceString(R.string.quiz_bad_score);
        }
    }

    public boolean quizPassed() {
        if ((float)score / questions.size() >= 0.75) {
            return true;
        } else {
            return false;
        }
    }



    public ArrayList<QuizQuestionModel> getNextAnswers() {
        ArrayList<QuizQuestionModel> mNavItems = new ArrayList<>();
        mNavItems.add(new QuizQuestionModel(this.questions.get(question_no).answers.get(0), "A)"));
        mNavItems.add(new QuizQuestionModel(this.questions.get(question_no).answers.get(1), "B)"));
        mNavItems.add(new QuizQuestionModel(this.questions.get(question_no).answers.get(2), "C)"));
        mNavItems.add(new QuizQuestionModel(this.questions.get(question_no).answers.get(3), "D)"));
        return mNavItems;
    }

    public String getNextQuestion() {
        return this.questions.get(question_no).question;
    }

    public int getCorrectIndex() {
        return this.questions.get(question_no).correct_answer;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void incrementQuestionCount() {
        question_no++;
    }

    public void clear() {
        mText = new MutableLiveData<>();
        mText.setValue(getResourceString(R.string.quiz_name));
        question_no = 0;
        score = 0;
        module = "";
        wpm = 0;
        questions = new ArrayList<>();
    }



    public void populateQuestionBank() {
        this.questions = new ArrayList<>();
        // TODO: query DB for quiz questions based on learning module and difficulty
        String question = "question_" + getModule() +"_" + this.difficulty + "_";
        String answers = "answers_" + getModule() + "_" + this.difficulty + "_";

        for (int i = 0; i < 4; i ++) {
            int q = App.getStringIdentifier(question + i);
            int a = App.getArrayIdentifier(answers + i);
            String[] answersArray = App.getAppResources().getStringArray(a);
            this.questions.add(new QuizQuestion(getResourceString(q), new ArrayList<String>(
                    Arrays.asList(answersArray[0],
                            answersArray[1],
                            answersArray[2],
                            answersArray[3])), Integer.parseInt(answersArray[4])));
        }

    }

    public String generateSubmoduleCompleteKey() {
        return getModule() +"_" + getDifficulty() + "_complete";
    }

    private String getResourceString(int resString) {
        return App.getAppResources().getString(resString);
    }


    private class QuizQuestion {
        public String question;
        public ArrayList<String> answers;
        public Integer correct_answer;
        public QuizQuestion(String question, ArrayList<String> answers, Integer correct_answer) {
            this.question = question;
            this.answers = answers;
            this.correct_answer = correct_answer;

        }
    }
}