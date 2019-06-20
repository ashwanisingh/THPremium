package com.netoperation.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserProfileDao {

    @Insert
    void insertUserProfile(UserProfileTable profileTable);

    @Query("SELECT * FROM UserProfileTable")
    UserProfileTable getUserProfileTable();

    @Query("DELETE FROM UserProfileTable")
    void deleteAll();
}
