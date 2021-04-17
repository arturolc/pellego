package com.gitlab.capstone.pellego.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.gitlab.capstone.pellego.database.daos.LearningModulesDao;
import com.gitlab.capstone.pellego.database.daos.LibraryDao;
import com.gitlab.capstone.pellego.database.daos.UserDao;
import com.gitlab.capstone.pellego.database.entities.Answers;
import com.gitlab.capstone.pellego.database.entities.Average_WPM_History;
import com.gitlab.capstone.pellego.database.entities.Books;
import com.gitlab.capstone.pellego.database.entities.Intro_Status;
import com.gitlab.capstone.pellego.database.entities.LM_Intro;
import com.gitlab.capstone.pellego.database.entities.LM_Module;
import com.gitlab.capstone.pellego.database.entities.LM_Quiz;
import com.gitlab.capstone.pellego.database.entities.LM_Submodule;
import com.gitlab.capstone.pellego.database.entities.Library;
import com.gitlab.capstone.pellego.database.entities.ProgressCompleted;
import com.gitlab.capstone.pellego.database.entities.Questions;
import com.gitlab.capstone.pellego.database.entities.Scores;
import com.gitlab.capstone.pellego.database.entities.User_Analytics;
import com.gitlab.capstone.pellego.database.entities.Users;

/****************************************
 * Arturo Lara
 *
 * Represents the Pellego Database
 ***************************************/

@Database(entities = {Answers.class,
        Average_WPM_History.class,
        Books.class,
        Intro_Status.class,
        Library.class,
        LM_Intro.class,
        LM_Module.class,
        LM_Quiz.class,
        LM_Submodule.class,
        Questions.class,
        Scores.class,
        ProgressCompleted.class,
        User_Analytics.class,
        Users.class},
        version = 1, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class PellegoDatabase extends RoomDatabase {
    public abstract LearningModulesDao learningModulesDao();
    public abstract LibraryDao libraryDao();
    public abstract UserDao userDao();

    private static PellegoDatabase INSTANCE;

    public static PellegoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PellegoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PellegoDatabase.class, "pellego_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

