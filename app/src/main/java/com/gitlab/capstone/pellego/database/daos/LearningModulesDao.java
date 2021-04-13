package com.gitlab.capstone.pellego.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.entities.Answers;
import com.gitlab.capstone.pellego.database.entities.LM_Intro;
import com.gitlab.capstone.pellego.database.entities.LM_Module;
import com.gitlab.capstone.pellego.database.entities.LM_Submodule;
import com.gitlab.capstone.pellego.database.entities.Questions;
import com.gitlab.capstone.pellego.network.models.Answer;

import java.util.List;

@Dao
public interface LearningModulesDao {
    // Module
    @Query("Select * from LM_Module")
    public List<LM_Module> getModules();

    @Query("Select * from LM_Module where MID = :mID")
    public LM_Module getModule(int mID);

    @Query("Select count(*) from LM_Module")
    public int getModulesCount();

    // Submodule
    @Query("Select * from LM_Submodule where MID = :mID")
    public List<LM_Submodule> getSubmodules(int mID);

    @Query("Select count(*) from LM_Submodule where MID = :mID")
    public int getSubmodulesCount(int mID);

    // Intro
    @Query("Select * from LM_Intro where MID = :mID")
    public List<LM_Intro> getIntro(int mID);

    // Questions & Answers
    @Query("Select * from Questions where SMID = :sMID")
    public List<Questions> getQuestions(int sMID);

    @Query("Select QUID as qUID, Answer as answer, Correct as correct from Answers where SMID = :sMID and QUID = :qUID")
    public List<Answer> getAnswers(int sMID, int qUID);

    // Insert queries
    @Insert
    public void insertModules(LM_Module... module);

    @Insert
    public void insertIntros(LM_Intro... intro);

    @Insert
    public void insertSubmodules(LM_Submodule... submodule);

    @Insert
    public void insertQuestions(Questions... question);

    @Insert
    public void insertAnswers(Answers... answer);

    @Query("select count(*) from ProgressCompleted where UID = :uID and SMID in (select SMID from LM_Submodule where MID = :mID)")
    public int getModuleProgress(int uID, int mID);


}
