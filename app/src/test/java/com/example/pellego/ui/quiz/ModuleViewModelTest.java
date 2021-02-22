package com.example.pellego.ui.quiz;
import androidx.test.core.app.ApplicationProvider;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pellego.ui.module.overview.ModuleViewModel;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(AndroidJUnit4.class)
public class ModuleViewModelTest extends TestCase {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private Context context = ApplicationProvider.getApplicationContext();


    @Mock
    ModuleViewModel moduleViewModel = new ModuleViewModel();

    @Before
    public void initialize() {
        assertEquals(false, moduleViewModel.showSubmodulePopupDialog);
        assertEquals(false, moduleViewModel.showPopupDialog);
        assertEquals("", moduleViewModel.getModuleDescription());
        assertEquals(-1, moduleViewModel.getIntro_id());
        assertEquals(-1, moduleViewModel.getModule_id());
    }

    @Test
    public void setViewModelVarsTest() {
        moduleViewModel.setViewModelVars("title", "descr", 1, 1, 1, 1);
        assertEquals("descr", moduleViewModel.getModuleDescription());
        assertEquals(1, moduleViewModel.getIntro_id());
        assertEquals(1, moduleViewModel.getModule_id());
        assertSame("title", moduleViewModel.getText().getValue());
    }


    @Test
    public void clearTest() {
        moduleViewModel.clear();
        assertEquals(false, moduleViewModel.showSubmodulePopupDialog);
        assertEquals(false, moduleViewModel.showPopupDialog);
        assertEquals("", moduleViewModel.getModuleDescription());
        assertEquals(-1, moduleViewModel.getIntro_id());
        assertEquals(-1, moduleViewModel.getModule_id());
    }
}