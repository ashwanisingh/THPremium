package com.netoperation.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.netoperation.model.RecoBean;

import java.util.List;

@Entity(tableName = "BreifingTable")
public class BreifingTable {

    @PrimaryKey(autoGenerate = true)
    private int id;


    private List<RecoBean> morning;
    private List<RecoBean> noon;
    private List<RecoBean> evening;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RecoBean> getMorning() {
        return morning;
    }

    public void setMorning(List<RecoBean> morning) {
        this.morning = morning;
    }

    public List<RecoBean> getNoon() {
        return noon;
    }

    public void setNoon(List<RecoBean> noon) {
        this.noon = noon;
    }

    public List<RecoBean> getEvening() {
        return evening;
    }

    public void setEvening(List<RecoBean> evening) {
        this.evening = evening;
    }
}
