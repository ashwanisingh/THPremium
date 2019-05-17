package com.netoperation.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.netoperation.model.RecoBean;

import java.util.List;

@Entity(tableName ="DashboardTable")
public class DashboardTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name ="bean")
    private List<RecoBean> beans;

    public List<RecoBean> getBeans() {
        return beans;
    }

    public void setBeans(List<RecoBean> beans) {
        this.beans = beans;
    }
}
