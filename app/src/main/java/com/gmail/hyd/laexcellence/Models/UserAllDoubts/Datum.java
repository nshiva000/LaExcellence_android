package com.gmail.hyd.laexcellence.Models.UserAllDoubts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("q_id")
    @Expose
    private String qId;
    @SerializedName("s_id")
    @Expose
    private String sId;
    @SerializedName("prob_type")
    @Expose
    private String probType;
    @SerializedName("problem")
    @Expose
    private String problem;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("q_no")
    @Expose
    private String qNo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("close_date")
    @Expose
    private String closeDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQId() {
        return qId;
    }

    public void setQId(String qId) {
        this.qId = qId;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getProbType() {
        return probType;
    }

    public void setProbType(String probType) {
        this.probType = probType;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQNo() {
        return qNo;
    }

    public void setQNo(String qNo) {
        this.qNo = qNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

}