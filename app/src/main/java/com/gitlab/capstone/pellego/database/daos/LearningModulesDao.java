package com.gitlab.capstone.pellego.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.entities.Answers;
import com.gitlab.capstone.pellego.database.entities.LM_Intro;
import com.gitlab.capstone.pellego.database.entities.LM_Module;
import com.gitlab.capstone.pellego.database.entities.LM_Submodule;
import com.gitlab.capstone.pellego.database.entities.Questions;

import java.util.List;

@Dao
public interface LearningModulesDao {
    @Query("Select MID, Name, Subheader, Description, Icon from LM_Module")
    public List<LM_Module> getModules();

    @Query("Select count(*) from LM_Module")
    public int getModulesCount();

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

    @Query("Select Description from LM_Module where MID = :mID")
    public List<String> getModuleDescription(int mID);

    @Query("Select * from LM_Submodule where MID = :mID")
    public List<LM_Submodule> getSubmodules(int mID);

    @Query("Select count(*) from LM_Submodule where MID = :mID")
    public int getSubmodulesCount(int mID);

    public static class ModuleName {
        private int mID;
        private String name;

        public ModuleName(int mID, String name) {
            this.mID = mID;
            this.name = name;
        }
        public int getMID() { return mID; }
        public String getName() { return name; }
    }
}
