package com.gitlab.capstone.unit.fragments.progress;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.fragments.progress.ProgressViewModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static android.os.Looper.getMainLooper;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**********************************************
 Chris Bordoy - 3/12/21

 Unit tests for the Progress View Model
 **********************************************/
@RunWith(AndroidJUnit4.class)
@Config(sdk = 28)
@LooperMode(LooperMode.Mode.PAUSED)
public class ProgressViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private Context context = ApplicationProvider.getApplicationContext();

    @Mock
    ProgressViewModel progressViewModel = new ProgressViewModel();

    @Test
    public void progressViewModelReturnsCorrectTextValue() {
        LiveData<String> mText = progressViewModel.getText();

        shadowOf(getMainLooper()).idle();

        assertEquals("Progress", mText.getValue());
    }

    @Test
    public void progressViewModelSetsCorrectTextValueOnMainThread() {
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.setValue("Progress Reports");

        shadowOf(getMainLooper()).idle();

        assertEquals("Progress Reports", mText.getValue());
    }

    @Test
    public void progressViewModelSetsCorrectTextValueOnBackgroundThread() {
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.postValue("Progress Reports");

        shadowOf(getMainLooper()).idle();

        assertEquals("Progress Reports", mText.getValue());
    }

    @Test
    public void progressViewModelReturnsCorrectTodayValues() {
        String[] expectedTodayValues = {"118", "236"};
        String[] actualTodayValues = progressViewModel.getTodayValues();

        assertEquals(expectedTodayValues[0], actualTodayValues[0]);
        assertEquals(expectedTodayValues[1], actualTodayValues[1]);
    }

    @Test
    public void progressViewModelReturnsCorrectLastWeekValues() {
        String[] expectedLastWeekValues = {"588", "255"};
        String[] actualLastWeekValues = progressViewModel.getLastWeekValues();

        assertEquals(expectedLastWeekValues[0], actualLastWeekValues[0]);
        assertEquals(expectedLastWeekValues[1], actualLastWeekValues[1]);
    }

    @Test
    public void progressViewModelReturnsCorrectLastYearValues() {
        String[] expectedLastYearValues = {"1529", "420"};
        String[] actualLastYearValues = progressViewModel.getLastYearValues();

        assertEquals(expectedLastYearValues[0], actualLastYearValues[0]);
        assertEquals(expectedLastYearValues[1], actualLastYearValues[1]);
    }

    @Test
    public void progressViewModelReturnsCorrectDaysListValues() {
        String[] expectedListOfDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String[] actualListOfDays = context.getResources().getStringArray(R.array.days_abbr);

        for (int i = 0; i < actualListOfDays.length; i++){
            assertEquals(expectedListOfDays[i], actualListOfDays[i]);
        }
    }

    @Test
    public void progressViewModelReturnsCorrectMonthsListValues() {
        String[] expectedListOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                                        "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] actualListOfMonths = context.getResources().getStringArray(R.array.months_abbr);

        for (int i = 0; i < actualListOfMonths.length; i++){
            assertEquals(expectedListOfMonths[i], actualListOfMonths[i]);
        }
    }

    @Test
    public void progressViewModelReturnsCorrectLastWeekWordsReadDataValues() {
        Float[] expectedYValues = {890f, 600f, 550f, 533f, 620f, 420f, 666f};
        ArrayList<BarEntry> expectedLastWeekValuesList = new ArrayList<>();
        for(int i = 0; i < expectedYValues.length; i++) {
            expectedLastWeekValuesList.add(new BarEntry((float)i + 1, expectedYValues[i]));
        }

        List<String> expectedLastWeekValues =
                expectedLastWeekValuesList
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

        List<String> actualLastWeekValues =
                progressViewModel
                        .getMockLastWeekWordsReadData()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

        assertEquals(expectedLastWeekValues, actualLastWeekValues);
    }

    @Test
    public void progressViewModelReturnsCorrectLastWeekWpmDataValues() {
        Float[] expectedYValues = {540f, 230f, 297f, 89f, 444f, 33f, 111f};
        ArrayList<BarEntry> expectedLastWeekValuesList = new ArrayList<>();
        for(int i = 0; i < expectedYValues.length; i++) {
            expectedLastWeekValuesList.add(new BarEntry((float)i + 1, expectedYValues[i]));
        }

        List<String> expectedLastWeekValues =
                expectedLastWeekValuesList
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

        List<String> actualLastWeekValues =
                progressViewModel
                        .getMockLastWeekWpmData()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

        assertEquals(expectedLastWeekValues, actualLastWeekValues);
    }

    @Test
    public void progressViewModelReturnsCorrectLastYearWordsReadDataValues() {
        Float[] expectedYValues = {233f, 120f, 450f, 500f, 80f, 145f, 278f,
                333f, 444f, 580f, 700f, 490f};
        ArrayList<Entry> expectedLastYearValuesList = new ArrayList<>();
        for(int i = 0; i < expectedYValues.length; i++) {
            expectedLastYearValuesList.add(new Entry((float)i + 1, expectedYValues[i]));
        }

        List<String> expectedLastYearValues =
                expectedLastYearValuesList
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

        List<String> actualLastYearValues =
                progressViewModel
                        .getMockLastYearWordsReadData()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

        assertEquals(expectedLastYearValues, actualLastYearValues);
    }

    @Test
    public void getMockLastYearWpmData() {
        Float[] expectedYValues = {100f, 120f, 140f, 150f, 180f, 200f, 190f,
                125f, 175f, 195f, 205f, 211f};
        ArrayList<Entry> expectedLastYearValuesList = new ArrayList<>();
        for(int i = 0; i < expectedYValues.length; i++) {
            expectedLastYearValuesList.add(new Entry((float)i + 1, expectedYValues[i]));
        }

        List<String> expectedLastYearValues =
                expectedLastYearValuesList
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

        List<String> actualLastYearValues =
                progressViewModel
                        .getMockLastYearWpmData()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

        assertEquals(expectedLastYearValues, actualLastYearValues);
    }
}