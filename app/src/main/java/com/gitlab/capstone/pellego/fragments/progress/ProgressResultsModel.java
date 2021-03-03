package com.gitlab.capstone.pellego.fragments.progress;

public class ProgressResultsModel {
    public int wordsRead = 0;
    public int wpm = 0;
    public String date = null;

    public ProgressResultsModel(int wordsRead, int wpm, String date) {
        this.wordsRead = wordsRead;
        this.wpm = wpm;
        this.date = date;
    }

    public void setWordsRead(int wordsRead) {
        this.wordsRead = wordsRead;
    }

    public int getWpm() {
        return wpm;
    }

    public void setWpm(int wpm) {
        this.wpm = wpm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWordsRead(){
        return wordsRead;
    }
}
