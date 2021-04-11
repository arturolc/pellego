package com.gitlab.capstone.pellego.database.models;

import androidx.room.Embedded;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.daos.LearningModulesDao;
import com.gitlab.capstone.pellego.database.entities.LM_Module;

public class ModuleWithProgress {

    @Embedded
    private LM_Module module;



}
