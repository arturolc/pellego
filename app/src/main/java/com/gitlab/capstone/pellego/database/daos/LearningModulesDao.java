package com.gitlab.capstone.pellego.database.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.entities.LM_Submodule;

import java.util.List;

@Dao
public interface LearningModulesDao {
    @Query("Select MID as mID, Name as name from LM_Module")
    public List<ModuleName> getModuleNames();

    @Query("Select count(*) as countProgress from ProgressCompleted where UID = :uID and SMID = :smID")
    public int getModuleProgress(int uID, int smID);

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
