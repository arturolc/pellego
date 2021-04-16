package com.gitlab.capstone.pellego.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.entities.UserWordValues;
import com.gitlab.capstone.pellego.database.entities.Users;

import java.util.Date;
import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void addUser(Users user);

    @Insert
    void setUserWordValues(UserWordValues... values);

    @Query("select * from Users where Email= :email")
    LiveData<Users> getUser(String email);

    @Query("select Recorded from User_Word_Values where uID = :uID order by Recorded desc limit 1")
    LiveData<Date> getLastRecordedDate(int uID);

    @Query("select * from User_Word_Values where Recorded > :date and uID = :uID order by Recorded asc")
    LiveData<List<UserWordValues>> getUpdatedRecords(Date date, int uID);

}
