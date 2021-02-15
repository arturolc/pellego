package com.example.pellego.ui.quiz;
import androidx.test.core.app.ApplicationProvider;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(AndroidJUnit4.class)
public class QuizViewModelTest extends TestCase {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private Context context = ApplicationProvider.getApplicationContext();

    public void setUp() throws Exception {
        super.setUp();
    }

    @Mock
    QuizViewModel quizViewModel = new QuizViewModel();

// not working as we cannot test live data not on the android thread
    @Test
    public void testPopulateQuestionBank() {
            quizViewModel.setDifficulty("intermediate");
            quizViewModel.populateQuestionBank();
            assertNotNull(quizViewModel.getNextQuestion());
//        assertTrue(true);
    }

//    @Test
//    public void testGetDifficulty() {
//        quizViewModel.setDifficulty("intermediate");
//        assertEquals("intermediate", quizViewModel.getDifficulty());
//    }
}