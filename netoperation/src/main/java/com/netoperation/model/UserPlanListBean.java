package com.netoperation.model;

public class UserPlanListBean {
    /**
     * planId : 10
     * planName : 30 days free trial
     * amount : 0.0
     * status : success
     * nextRenewal : 2019-07-12
     * validity : -
     * sDate : 2019-06-12
     * eDate : 2019-07-11
     * isActive : 1
     */

    private int planId;
    private String planName;
    private double amount;
    private String status;
    private String nextRenewal;
    private String validity;
    private String sDate;
    private String eDate;
    private int isActive;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextRenewal() {
        return nextRenewal;
    }

    public void setNextRenewal(String nextRenewal) {
        this.nextRenewal = nextRenewal;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getSDate() {
        return sDate;
    }

    public void setSDate(String sDate) {
        this.sDate = sDate;
    }

    public String getEDate() {
        return eDate;
    }

    public void setEDate(String eDate) {
        this.eDate = eDate;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

}
