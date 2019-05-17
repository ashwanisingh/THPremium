package com.netoperation.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DashboardDao {

    @Insert
    void insertDashboard(DashboardTable HomeTable);

    @Query("SELECT * FROM DashboardTable")
    List<DashboardTable> getAllDashboardBean();

    @Query("DELETE FROM DashboardTable")
    void deleteAll();

}
