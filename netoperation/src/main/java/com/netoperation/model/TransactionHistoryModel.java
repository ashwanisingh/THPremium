package com.netoperation.model;

import java.util.List;

public class TransactionHistoryModel {


    /**
     * txnData : [{"userId":"80","planId":"8","planName":"Value pack","transactionid":"20190123173729gIM","trxnstatus":"add to cart","isactive":0,"amount":499,"netamount":0,"currency":"INR","billingmode":"paytm","validity":"1-year","igst":0,"igstamt":0,"cgst":0,"cgstamt":0,"sgst":0,"sgstamt":0,"utgst":0,"utgstamt":0,"txnDate":"2019-01-23 17:37:29","payMode":"","planType":"Value pack"},{"userId":"80","planId":"8","planName":"Value pack","transactionid":"20190123150243F1a","trxnstatus":"add to cart","isactive":0,"amount":499,"netamount":0,"currency":"INR","billingmode":"paytm","validity":"1-year","igst":0,"igstamt":0,"cgst":0,"cgstamt":0,"sgst":0,"sgstamt":0,"utgst":0,"utgstamt":0,"txnDate":"2019-01-23 15:02:43","payMode":"","planType":"Value pack"},{"userId":"80","planId":"8","planName":"Value pack","transactionid":"20190123145822Hqi","trxnstatus":"add to cart","isactive":0,"amount":499,"netamount":0,"currency":"INR","billingmode":"paytm","validity":"1-year","igst":0,"igstamt":0,"cgst":0,"cgstamt":0,"sgst":0,"sgstamt":0,"utgst":0,"utgstamt":0,"txnDate":"2019-01-23 14:58:21","payMode":"","planType":"Value pack"},{"userId":"80","planId":"7","planName":"Starter Pack","transactionid":"20190123145540R79","trxnstatus":"add to cart","isactive":0,"amount":99,"netamount":0,"currency":"INR","billingmode":"paytm","validity":"1-month","igst":0,"igstamt":0,"cgst":0,"cgstamt":0,"sgst":0,"sgstamt":0,"utgst":0,"utgstamt":0,"txnDate":"2019-01-23 14:55:40","payMode":"","planType":"Starter Plan"},{"userId":"80","planId":"9","planName":"Reader's pack","transactionid":"201901222038371mb","trxnstatus":"add to cart","isactive":0,"amount":899,"netamount":0,"currency":"INR","billingmode":"paytm","validity":"2-year","igst":0,"igstamt":0,"cgst":0,"cgstamt":0,"sgst":0,"sgstamt":0,"utgst":0,"utgstamt":0,"txnDate":"2019-01-22 20:38:37","payMode":"","planType":"Readers pack"},{"userId":"80","planId":"8","planName":"Value pack","transactionid":"201901222035110eB","trxnstatus":"add to cart","isactive":0,"amount":499,"netamount":0,"currency":"INR","billingmode":"paytm","validity":"1-year","igst":0,"igstamt":0,"cgst":0,"cgstamt":0,"sgst":0,"sgstamt":0,"utgst":0,"utgstamt":0,"txnDate":"2019-01-22 20:35:11","payMode":"","planType":"Value pack"}]
     * txnCount : 6
     * respCode : {"msg":"NONE","status":"Success","code":200}
     */

    private String txnCount;
    private RespCodeBean respCode;
    private List<TxnDataBean> txnData;

    public String getTxnCount() {
        return txnCount;
    }

    public void setTxnCount(String txnCount) {
        this.txnCount = txnCount;
    }

    public RespCodeBean getRespCode() {
        return respCode;
    }

    public void setRespCode(RespCodeBean respCode) {
        this.respCode = respCode;
    }

    public List<TxnDataBean> getTxnData() {
        return txnData;
    }

    public void setTxnData(List<TxnDataBean> txnData) {
        this.txnData = txnData;
    }

    public static class RespCodeBean {
        /**
         * msg : NONE
         * status : Success
         * code : 200
         */

        private String msg;
        private String status;
        private int code;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static class TxnDataBean {
        /**
         * userId : 80
         * planId : 8
         * planName : Value pack
         * transactionid : 20190123173729gIM
         * trxnstatus : add to cart
         * isactive : 0
         * amount : 499.0
         * netamount : 0.0
         * currency : INR
         * billingmode : paytm
         * validity : 1-year
         * igst : 0.0
         * igstamt : 0.0
         * cgst : 0.0
         * cgstamt : 0.0
         * sgst : 0.0
         * sgstamt : 0.0
         * utgst : 0.0
         * utgstamt : 0.0
         * txnDate : 2019-01-23 17:37:29
         * payMode :
         * planType : Value pack
         */

        private String userId;
        private String planId;
        private String planName;
        private String transactionid;
        private String trxnstatus;
        private int isactive;
        private double amount;
        private double netamount;
        private String currency;
        private String billingmode;
        private String validity;
        private double igst;
        private double igstamt;
        private double cgst;
        private double cgstamt;
        private double sgst;
        private double sgstamt;
        private double utgst;
        private double utgstamt;
        private String txnDate;
        private String payMode;
        private String planType;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(String transactionid) {
            this.transactionid = transactionid;
        }

        public String getTrxnstatus() {
            return trxnstatus;
        }

        public void setTrxnstatus(String trxnstatus) {
            this.trxnstatus = trxnstatus;
        }

        public int getIsactive() {
            return isactive;
        }

        public void setIsactive(int isactive) {
            this.isactive = isactive;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getNetamount() {
            return netamount;
        }

        public void setNetamount(double netamount) {
            this.netamount = netamount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getBillingmode() {
            return billingmode;
        }

        public void setBillingmode(String billingmode) {
            this.billingmode = billingmode;
        }

        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
        }

        public double getIgst() {
            return igst;
        }

        public void setIgst(double igst) {
            this.igst = igst;
        }

        public double getIgstamt() {
            return igstamt;
        }

        public void setIgstamt(double igstamt) {
            this.igstamt = igstamt;
        }

        public double getCgst() {
            return cgst;
        }

        public void setCgst(double cgst) {
            this.cgst = cgst;
        }

        public double getCgstamt() {
            return cgstamt;
        }

        public void setCgstamt(double cgstamt) {
            this.cgstamt = cgstamt;
        }

        public double getSgst() {
            return sgst;
        }

        public void setSgst(double sgst) {
            this.sgst = sgst;
        }

        public double getSgstamt() {
            return sgstamt;
        }

        public void setSgstamt(double sgstamt) {
            this.sgstamt = sgstamt;
        }

        public double getUtgst() {
            return utgst;
        }

        public void setUtgst(double utgst) {
            this.utgst = utgst;
        }

        public double getUtgstamt() {
            return utgstamt;
        }

        public void setUtgstamt(double utgstamt) {
            this.utgstamt = utgstamt;
        }

        public String getTxnDate() {
            return txnDate;
        }

        public void setTxnDate(String txnDate) {
            this.txnDate = txnDate;
        }

        public String getPayMode() {
            return payMode;
        }

        public void setPayMode(String payMode) {
            this.payMode = payMode;
        }

        public String getPlanType() {
            return planType;
        }

        public void setPlanType(String planType) {
            this.planType = planType;
        }
    }
}
