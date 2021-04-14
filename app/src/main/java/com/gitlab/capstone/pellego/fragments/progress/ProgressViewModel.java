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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
        int[] lastYearValues = new int[NUMBER_OF_MONTHS_IN_YEAR];
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
    
    public ArrayList<BarEntry> getLastWeekWordsReadData(Integer[] response) {
        ArrayList<BarEntry> wordsReadEntries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
            wordsReadEntries.add(new BarEntry( i + 1, response[i]));
        }

        return wordsReadEntries;
    }

    public ArrayList<BarEntry> getLastWeekWpmData(Integer[] response) {
        ArrayList<BarEntry> wpmEntries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
            wpmEntries.add(new BarEntry(i + 1, response[i]));
        }

        return wpmEntries;
    }

    public ArrayList<Entry> getLastYearWordsReadData(Integer[] response) {
        ArrayList<Entry> wordsReadEntries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_MONTHS_IN_YEAR; i++) {
            wordsReadEntries.add(new BarEntry(i + 1, response[i]));
        }

        return wordsReadEntries;
    }

    public ArrayList<Entry> getLastYearWpmData(Integer[] response) {
        ArrayList<Entry> wpmEntries = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_MONTHS_IN_YEAR; i++) {
            wpmEntries.add(new BarEntry(i + 1, response[i]));
        }

        return wpmEntries;
    }

    public Integer[] getLastWeekWordsReadMapping(List<ProgressValuesResponse> response) {
        Integer[] dayIndices = new Integer[NUMBER_OF_DAYS_IN_WEEK];
        for (int i = 0; i < dayIndices.length; i++) {
            dayIndices[getDayNumberOld(response.get(i).getRecorded()) - 1] = response.get(i).getWordsRead();
        }

        return dayIndices;
    }

    public Integer[] getLastWeekWpmMapping(List<ProgressValuesResponse> response) {
        Integer[] dayIndices = new Integer[NUMBER_OF_DAYS_IN_WEEK];
        for (int i = 0; i < dayIndices.length; i++) {
            dayIndices[getDayNumberOld(response.get(i).getRecorded()) - 1] = response.get(i).getWpm();
        }

        return dayIndices;
    }

    public Integer[] getLastYearWordsReadMapping(List<ProgressValuesResponse> response) {
        Integer[] monthIndices = new Integer[NUMBER_OF_MONTHS_IN_YEAR];
        for (int i = NUMBER_OF_DAYS_IN_WEEK; i < monthIndices.length + NUMBER_OF_DAYS_IN_WEEK; i++) {
            monthIndices[getMonthNumberOld(response.get(i).getRecorded())] = response.get(i).getWordsRead();
        }

        return monthIndices;
    }

    public Integer[] getLastYearWpmMapping(List<ProgressValuesResponse> response) {
        Integer[] monthIndices = new Integer[NUMBER_OF_MONTHS_IN_YEAR];
        for (int i = NUMBER_OF_DAYS_IN_WEEK; i < monthIndices.length + NUMBER_OF_DAYS_IN_WEEK; i++) {
            monthIndices[getMonthNumberOld(response.get(i).getRecorded())] = response.get(i).getWpm();
        }

        return monthIndices;
    }

    private static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    private static int getMonthNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }
}