package com.gitlab.capstone.pellego.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gitlab.capstone.pellego.database.entities.Users;

/****************************************
 * Arturo Lara
 *
 * Represents a User
 * data access object for caching
 ***************************************/

@Dao
public interface UserDao {

    @Insert
    void addUser(Users user);

    @Query("select * from Users where Email= :email")
    LiveData<Users> getUser(String email);
}
