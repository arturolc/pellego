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


    @Mock
    QuizViewModel quizViewModel = new QuizViewModel();

    @Before
    public void initialize() {
        quizViewModel.setDifficulty("intermediate");
        quizViewModel.setWPM(120);
        quizViewModel.setModule("metaguiding");
    }

    @Test
    public void testInitialization() {

        assertEquals("intermediate", quizViewModel.getDifficulty());
        assertTrue(120 == quizViewModel.getWPM());
        assertEquals("metaguiding", quizViewModel.getModule());
    }


    @Test
    public void testQuestions() {
        quizViewModel.populateQuestionBank();
        String q = quizViewModel.getNextQuestion();
        assertNotNull(q);
        assertFalse(quizViewModel.isLastQuestion());
        assertNotNull(quizViewModel.getNextAnswers());
        assertTrue(quizViewModel.getCorrectIndex() > -1 && quizViewModel.getCorrectIndex() < 4);
        quizViewModel.incrementQuestionCount();
        assertNotSame(quizViewModel.getNextQuestion(), q);
 }
}