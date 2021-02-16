package com.example.pellego.ui.quiz;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pellego.HomeActivity;
import com.example.pellego.ui.pellegolibrary.PellegoLibrary;
import com.example.pellego.ui.pellegolibrary.PellegoLibraryViewModel;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(AndroidJUnit4.class)
public class PellegoLibraryViewModelTest extends TestCase {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private Context context = ApplicationProvider.getApplicationContext();

    @Mock
    PellegoLibraryViewModel pellegoLibraryViewModel = new PellegoLibraryViewModel(Mockito.mock(Application.class));
    @Test
    public void testPopulateQuestionBank() {
        assertNotNull(pellegoLibraryViewModel.getBooks());
    }
}
