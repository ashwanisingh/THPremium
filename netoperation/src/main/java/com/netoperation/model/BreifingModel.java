package com.netoperation.model;

import java.util.List;

public class BreifingModel {


    private List<MorningBean> morning;
    private List<MorningBean> noon;
    private List<MorningBean> evening;

    public List<MorningBean> getMorning() {
        return morning;
    }

    public void setMorning(List<MorningBean> morning) {
        this.morning = morning;
    }

    public List<MorningBean> getNoon() {
        return noon;
    }

    public void setNoon(List<MorningBean> noon) {
        this.noon = noon;
    }

    public List<MorningBean> getEvening() {
        return evening;
    }

    public void setEvening(List<MorningBean> evening) {
        this.evening = evening;
    }



}
