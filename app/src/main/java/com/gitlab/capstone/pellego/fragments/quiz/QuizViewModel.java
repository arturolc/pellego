package com.gitlab.capstone.pellego.fragments.quiz;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.database.LearningModulesRepo;
import com.gitlab.capstone.pellego.network.models.Answer;
import com.gitlab.capstone.pellego.network.models.QuizResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**********************************************
 Eli Hebdon and Chris Bordoy

 Quiz view model
 **********************************************/

public class QuizViewModel extends AndroidViewModel {

    public LearningModulesRepo repo;
    private LiveData<List<QuizResponse>> quizResponse = new MutableLiveData<>();
    private MutableLiveData<String> mText;
    private String difficulty;
    public static Integer question_no;
    private static ArrayList<QuizQuestion> questions;
    public static int score;
    private static String module;
    private static Integer wpm;

    public QuizViewModel(@NonNull Application application){
        super(application);
        this.repo = LearningModulesRepo.getInstance(application);
    }

    public LiveData<List<QuizResponse>> getQuizResponse(String MID, String SMID) {
        if (quizResponse.getValue() == null) {
            quizResponse = repo.getQuizzes(MID, SMID);
        }

        return quizResponse;
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
        mNavItems.add(new QuizQuestionModel(this.questions.get(question_no).answers.get(0).getAnswer(), "A"));
        mNavItems.add(new QuizQuestionModel(this.questions.get(question_no).answers.get(1).getAnswer(), "B"));
        mNavItems.add(new QuizQuestionModel(this.questions.get(question_no).answers.get(2).getAnswer(), "C"));
        mNavItems.add(new QuizQuestionModel(this.questions.get(question_no).answers.get(3).getAnswer(), "D"));
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

    public void populateQuestionBank(List<String> questions, List<List<Answer>> answers) {
        this.questions = new ArrayList<>();
        for (int i = 0; i < 4; i ++) {
            Answer[] answersArray = answers.get(i).toArray(new Answer[0]);
            this.questions.add(new QuizQuestion(questions.get(i), new ArrayList<>(
                    Arrays.asList(answersArray[0],
                            answersArray[1],
                            answersArray[2],
                            answersArray[3])), getCorrectAnswerIndex(answersArray)));
        }
    }

    public String generateSubmoduleCompleteKey() {
        return getModule() +"_" + getDifficulty() + "_complete";
    }

    private String getResourceString(int resString) {
        return App.getAppResources().getString(resString);
    }

    public List<String> getQuestions(List<QuizResponse> quizResponses) {
        List<String> questions = new ArrayList<>();
        for (int i = 0; i < quizResponses.size(); i++) {
            questions.add(quizResponses.get(i).getQuestion());
        }

        return questions;
    }

    public List<List<Answer>> getAnswers(List<QuizResponse> quizResponses) {
        List<List<Answer>> answers = new ArrayList<>();

        for (int i = 0; i < quizResponses.size(); i++) {
            answers.add(quizResponses.get(i).getAnswers());
        }

        return answers;
    }

    public int getCorrectAnswerIndex(Answer[] answers) {
        int correctIdx = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].getCorrect() == 1) {
                correctIdx = i;
            }
        }

        return correctIdx;
    }

    private static class QuizQuestion {
        public String question;
        public ArrayList<Answer> answers;
        public Integer correct_answer;
        public QuizQuestion(String question, ArrayList<Answer> answers, Integer correct_answer) {
            this.question = question;
            this.answers = answers;
            this.correct_answer = correct_answer;
        }
    }
}