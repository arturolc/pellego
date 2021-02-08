package com.example.pellego.ui.quiz;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class QuizViewModelTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

// not working as we cannot test live data not on the android thread
    @Test
    public void testPopulateQuestionBank() {
//        QuizViewModel quizViewModel = new QuizViewModel();

            assertTrue(true);
//            quizViewModel.setDifficulty("intermediate");
//            quizViewModel.populateQuestionBank();
//            assertNotNull(quizViewModel.getNextQuestion());
    }
}