package com.gitlab.capstone.pellego.fragments.progress;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.database.UsersRepo;
import com.gitlab.capstone.pellego.network.models.ProgressValuesResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**********************************************
    Chris Bordoy

    The Progress View Model
 **********************************************/

public class ProgressViewModel extends AndroidViewModel {

    private UsersRepo usersRepo;
    private MutableLiveData<String> mText;
    private LiveData<List<ProgressValuesResponse>> progressValueResponse;
    private final int NUMBER_OF_DAYS_IN_WEEK = 7;
    private final int NUMBER_OF_MONTHS_IN_YEAR = 12;

    public ProgressViewModel(@NonNull Application application) {
        super(application);
        usersRepo = UsersRepo.getInstance(application);
        this.progressValueResponse = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("Progress");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<ProgressValuesResponse>> getProgressValues() {
        progressValueResponse = usersRepo.getProgressValues();

        return progressValueResponse;
    }

    public int[] getLastWeekValues(List<ProgressValuesResponse> response) {
        int[] lastWeekValues = new int[2];
        int wordsRead = 0;
        int wpm = 0;

        for (int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
            wordsRead += response.get(i).getWordsRead();
            wpm += response.get(i).getWpm();
        }

        if(wordsRead != 0 && wpm != 0) {
            wordsRead = wordsRead/NUMBER_OF_DAYS_IN_WEEK;
            wpm = wpm/NUMBER_OF_DAYS_IN_WEEK;
            lastWeekValues[0] = wordsRead;
            lastWeekValues[1] = wpm;
        }

        return lastWeekValues;
    }

    public int[] getLastYearValues(List<ProgressValuesResponse> response) {
        int[] lastYearValues = new int[12];
        int wordsRead = 0;
        int wpm = 0;

        for (int i = 7; i < response.size(); i++) {
            wordsRead += response.get(i).getWordsRead();
            wpm += response.get(i).getWpm();
        }

        if(wordsRead != 0 && wpm != 0) {
            wordsRead = wordsRead/NUMBER_OF_MONTHS_IN_YEAR;
            wpm = wpm/NUMBER_OF_MONTHS_IN_YEAR;
            lastYearValues[0] = wordsRead;
            lastYearValues[1] = wpm;
        }

        return lastYearValues;
    }

    public ArrayList<String> getDaysList() {
        ArrayList<String> listOfDays = new ArrayList<>();
        Collections.addAll(listOfDays, App.getAppResources().getStringArray(R.array.days_abbr));

        return listOfDays;
    }

    public ArrayList<String> getMonthsList() {
        ArrayList<String> listOfMonths = new ArrayList<>();
        Collections.addAll(listOfMonths, App.getAppResources().getStringArray(R.array.months_abbr));

        return listOfMonths;
    }
    
    public ArrayList<BarEntry> getLastWeekWordsReadData(List<ProgressValuesResponse> response) {
        ArrayList<BarEntry> wordsReadEntries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
            wordsReadEntries.add(new BarEntry( i + 1, response.get(i).getWordsRead()));
        }

        return wordsReadEntries;
    }

    public ArrayList<BarEntry> getLastWeekWpmData(List<ProgressValuesResponse> response) {
        ArrayList<BarEntry> wpmEntries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
            wpmEntries.add(new BarEntry(i + 1, response.get(i).getWpm()));
        }

        return wpmEntries;
    }

    public ArrayList<Entry> getLastYearWordsReadData(List<ProgressValuesResponse> response) {
        ArrayList<Entry> wordsReadEntries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_MONTHS_IN_YEAR; i++) {
            wordsReadEntries.add(new BarEntry(i + 1, response.get(i).getWordsRead()));
        }

        return wordsReadEntries;
    }

    public ArrayList<Entry> getLastYearWpmData(List<ProgressValuesResponse> response) {
        ArrayList<Entry> wpmEntries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_MONTHS_IN_YEAR; i++) {
            wpmEntries.add(new BarEntry(i + 1, response.get(i).getWpm()));
        }

        return wpmEntries;
    }
}