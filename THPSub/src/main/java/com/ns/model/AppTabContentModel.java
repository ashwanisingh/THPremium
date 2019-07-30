package com.ns.model;

import com.netoperation.model.RecoBean;

public class AppTabContentModel {

    private int viewType;
    private RecoBean bean;

    public AppTabContentModel(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public RecoBean getBean() {
        return bean;
    }

    public void setBean(RecoBean bean) {
        this.bean = bean;
    }


}
