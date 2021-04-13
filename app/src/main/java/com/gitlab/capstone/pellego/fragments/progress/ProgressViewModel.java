package com.gitlab.capstone.pellego.fragments.progress;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.App;
import com.gitlab.capstone.pellego.database.UsersRepo;
import com.gitlab.capstone.pellego.network.models.TodayProgressValueResponse;

import java.util.ArrayList;
import java.util.Collections;

/**********************************************
    Chris Bordoy

    The Progress View Model
 **********************************************/

public class ProgressViewModel extends AndroidViewModel {

    private UsersRepo usersRepo;
    private MutableLiveData<String> mText;
    private LiveData<TodayProgressValueResponse> todayProgressValueResponse;

    public ProgressViewModel(@NonNull Application application) {
        super(application);
        usersRepo = UsersRepo.getInstance(application);
        this.todayProgressValueResponse = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("Progress");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<TodayProgressValueResponse> getTodayValues() {
        todayProgressValueResponse = usersRepo.getTodayProgressValues();

        return todayProgressValueResponse;
    }

    public String[] getLastWeekValues() {
        String[] lastWeekValues = {"588", "255"};

        return lastWeekValues;
    }

    public String[] getLastYearValues() {
        String[] lastYearValues = {"1529", "420"};

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
    
    public ArrayList<BarEntry> getMockLastWeekWordsReadData() {
        ArrayList<BarEntry> wordsReadEntries = new ArrayList<>();
        wordsReadEntries.add(new BarEntry(1,890));
        wordsReadEntries.add(new BarEntry(2,600));
        wordsReadEntries.add(new BarEntry(3,550));
        wordsReadEntries.add(new BarEntry(4,533));
        wordsReadEntries.add(new BarEntry(5,620));
        wordsReadEntries.add(new BarEntry(6,420));
        wordsReadEntries.add(new BarEntry(7,666));

        return wordsReadEntries;
    }

    public ArrayList<BarEntry> getMockLastWeekWpmData() {
        ArrayList<BarEntry> wpmEntries = new ArrayList<>();
        wpmEntries.add(new BarEntry(1,540));
        wpmEntries.add(new BarEntry(2,230));
        wpmEntries.add(new BarEntry(3,297));
        wpmEntries.add(new BarEntry(4,89));
        wpmEntries.add(new BarEntry(5,444));
        wpmEntries.add(new BarEntry(6,33));
        wpmEntries.add(new BarEntry(7,111));

        return wpmEntries;
    }

    public ArrayList<Entry> getMockLastYearWordsReadData() {
        ArrayList<Entry> wordsReadEntries = new ArrayList<>();
        wordsReadEntries.add(new Entry(1, 233));
        wordsReadEntries.add(new Entry(2, 120));
        wordsReadEntries.add(new Entry(3, 450));
        wordsReadEntries.add(new Entry(4, 500));
        wordsReadEntries.add(new Entry(5, 80));
        wordsReadEntries.add(new Entry(6, 145));
        wordsReadEntries.add(new Entry(7, 278));
        wordsReadEntries.add(new Entry(8, 333));
        wordsReadEntries.add(new Entry(9, 444));
        wordsReadEntries.add(new Entry(10, 580));
        wordsReadEntries.add(new Entry(11, 700));
        wordsReadEntries.add(new Entry(12, 490));

        return wordsReadEntries;
    }

    public ArrayList<Entry> getMockLastYearWpmData() {
        ArrayList<Entry> wpmEntries = new ArrayList<>();
        wpmEntries.add(new Entry(1, 100));
        wpmEntries.add(new Entry(2, 120));
        wpmEntries.add(new Entry(3, 140));
        wpmEntries.add(new Entry(4, 150));
        wpmEntries.add(new Entry(5, 180));
        wpmEntries.add(new Entry(6, 200));
        wpmEntries.add(new Entry(7, 190));
        wpmEntries.add(new Entry(8, 125));
        wpmEntries.add(new Entry(9, 175));
        wpmEntries.add(new Entry(10, 195));
        wpmEntries.add(new Entry(11, 205));
        wpmEntries.add(new Entry(12, 211));

        return wpmEntries;
    }
}