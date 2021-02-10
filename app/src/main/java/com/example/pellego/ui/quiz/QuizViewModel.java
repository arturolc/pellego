package com.example.pellego.ui.quiz;

import android.content.res.Resources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pellego.App;
import com.example.pellego.R;

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
        } else if ((float)score / questions.size() > 0.6) {
            return getResourceString(R.string.quiz_mediocre_score);
        } else {
            return getResourceString(R.string.quiz_bad_score);
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
        mText.setValue(getResourceString(R.string.quiz_perfect_score));
        question_no = 0;
        score = 0;
        module = "";
        wpm = 0;
        questions = new ArrayList<>();
    }

    public void populateQuestionBank() {
        this.questions = new ArrayList<>();
        // TODO: query DB for quiz questions based on learning module and difficulty
        switch(this.difficulty) {
//            case "Beginner Submodule":
            default:
                this.questions.add(new QuizQuestion(getResourceString(R.string.rsvp_beginner_quiz_question_one), new ArrayList<String>(
                        Arrays.asList(getResourceString(R.string.rsvp_beginner_quiz_answer_one_a),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_one_b),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_one_c),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_one_d))), 2));
                this.questions.add(new QuizQuestion(getResourceString(R.string.rsvp_beginner_quiz_question_two), new ArrayList<String>(
                        Arrays.asList(getResourceString(R.string.rsvp_beginner_quiz_answer_two_a),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_two_b),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_two_c),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_two_d))), 1));
                this.questions.add(new QuizQuestion(getResourceString(R.string.rsvp_beginner_quiz_question_three), new ArrayList<String>(
                        Arrays.asList(getResourceString(R.string.rsvp_beginner_quiz_answer_three_a),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_three_b),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_three_c),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_three_d))), 0));
                this.questions.add(new QuizQuestion(getResourceString(R.string.rsvp_beginner_quiz_question_four), new ArrayList<String>(
                        Arrays.asList(getResourceString(R.string.rsvp_beginner_quiz_answer_four_a),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_four_b),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_four_c),
                                getResourceString(R.string.rsvp_beginner_quiz_answer_four_d))), 3));
//                break;
//            case "Intermediate Submodule":
//
//                break;
//            case "Advanced Submodule":
//                break;
//            default:
//                break;
        }
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