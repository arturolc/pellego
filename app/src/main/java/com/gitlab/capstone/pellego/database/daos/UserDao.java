package com.gitlab.capstone.pellego.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.entities.ProgressCompleted;
import com.gitlab.capstone.pellego.database.entities.UserWordValues;
import com.gitlab.capstone.pellego.database.entities.Users;
import com.gitlab.capstone.pellego.network.models.ProgressValuesResponse;

import java.util.Date;
import java.util.List;

/****************************************
 * Arturo Lara
 *
 * Represents a User
 * data access object for caching
 ***************************************/

@Dao
public interface UserDao {

    @Insert
    long addUser(Users user);

    @Insert
    void setUserWordValues(UserWordValues... values);

    @Insert
    void setProgressCompleted(ProgressCompleted... pc);

    @Query("select UID from Users where Email = :email")
    int getUserID(String email);

    @Query("select * from Users where Email= :email")
    LiveData<Users> getUser(String email);

    @Query("select count(*) from Users where Email = :email")
    LiveData<Boolean> userExists(String email);

    @Query("select Recorded from User_Word_Values where uID = :uID order by Recorded desc limit 1")
    LiveData<Date> getLastRecordedDate(int uID);

    @Query("select * from User_Word_Values where Recorded > :date and uID = :uID order by Recorded asc")
    LiveData<List<UserWordValues>> getUpdatedRecords(Date date, int uID);

    @Query("select * from ProgressCompleted where UID = :userID")
    LiveData<List<ProgressCompleted>> getProgressCompleted(int userID);

    @Query("select uwID, UID, sum(WordsRead) as WordsRead, round(cast(avg(WPM) as UNSIGNED), 0) as " +
            "WPM, Recorded from User_Word_Values where UID = :userID and Recorded = :date")
    UserWordValues getProgressLast7Days(int userID, Date date);

    @Query("select uwID, UID, sum(WordsRead) as WordsRead, " +
            "round(cast(avg(WPM) as UNSIGNED), 0) as WPM, Recorded from User_Word_Values " +
            "where UID = :userID and CAST(strftime('%m', datetime(Recorded/1000, 'unixepoch')) AS int) = " +
            "CAST(strftime('%m', datetime(:date/1000, 'unixepoch')) AS int)")
    UserWordValues getProgressLast12Months(int userID, long date);

    @Query("select SUM(WordsRead) as TotalWordsRead from User_Word_Values where UID = :userID")
    LiveData<Integer> getTotalWordsRead(int userID);
}
//    select uwID, UID, round(cast(avg(WordsRead) as UNSIGNED), 0) as WordsRead, round(cast(avg(WPM) as UNSIGNED), 0) as WPM, Recorded from User_Word_Values where UID = :userID and CAST(strftime('%m', datetime(Recorded/1000, 'unixepoch')) AS int) = CAST(strftime('%m', datetime(:date/1000, 'unixepoch')) AS int)