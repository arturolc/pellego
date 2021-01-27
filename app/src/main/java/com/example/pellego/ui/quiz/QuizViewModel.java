package com.example.pellego.ui.quiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;

/**********************************************
 Eli Hebdon

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
        return (score + "/" + (questions.size() - 1));
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Integer getWPM() {
        return wpm;
    }

    public String getFinalMessage() {
        if (score == questions.size() - 1) {
            return "Perfection!";
        } else if (score / (questions.size() - 1) > 0.6) {
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
        question_no++;
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
                this.questions.add(new QuizQuestion("What city did they go to for their summer vacation?", new ArrayList<String>(
                        Arrays.asList("Louvre",
                                "Latin",
                                "Paris",
                                "Lyon")), 2));
                this.questions.add(new QuizQuestion("How long was the summer vacation?", new ArrayList<String>(
                        Arrays.asList("One week",
                                "Eight days",
                                "Two weeks",
                                "Eight weeks")), 1));
                this.questions.add(new QuizQuestion("What did their hotel room have?", new ArrayList<String>(
                        Arrays.asList("A balcony",
                                "A bottle of wine",
                                "A view of the metro",
                                "A refrigerator")), 0));
                this.questions.add(new QuizQuestion("Who got tired walking in the Louvre museum?", new ArrayList<String>(
                        Arrays.asList("Henry",
                                "Seine",
                                "Harry",
                                "Steve")), 0));
                this.questions.add(new QuizQuestion("What did Steve enjoy the most?", new ArrayList<String>(
                        Arrays.asList("The hotel breakfast and the croissants",
                                "The cafes along the river Seine",
                                "The Latin Quarter and the balcony",
                                "The wine and the food")), 0));
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