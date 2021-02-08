package com.example.pellego.ui.quiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        mText.setValue("Quiz");
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
            return "Perfection!";
        } else if ((float)score / questions.size() > 0.6) {
            return "Not bad. You passed";
        } else {
            return "Oof. You'll have to try again..";
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
        mText.setValue("Quiz");
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
            case "Beginner Submodule":
                this.questions.add(new QuizQuestion("What city was traveled to for summer vacation?", new ArrayList<String>(
                        Arrays.asList("Chicago",
                                "London",
                                "Paris",
                                "Bangkok")), 2));
                this.questions.add(new QuizQuestion("How long was the summer vacation?", new ArrayList<String>(
                        Arrays.asList("One week",
                                "Eight days",
                                "Two weeks",
                                "Eight weeks")), 1));
                this.questions.add(new QuizQuestion("Who were the best friends in this reading?", new ArrayList<String>(
                        Arrays.asList("Henry and Steve",
                                "Tim and Mable",
                                "Amy and Sheila",
                                "Eli and Joanna")), 0));
                this.questions.add(new QuizQuestion("What was the name of the famous museum in the reading?", new ArrayList<String>(
                        Arrays.asList("The Metropolitan",
                                "The Museum of Modern Art",
                                "The Acropolis Museum",
                                "The Louvre")), 3));
                break;
            case "Intermediate Submodule":
                break;
            case "Advanced Submodule":
                break;
            default:
                break;
        }
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