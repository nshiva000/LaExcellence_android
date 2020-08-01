package com.gmail.hyd.laexcellence.Models.TestResultsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CorrectBy {

    @SerializedName("qid")
    @Expose
    private String qid;
    @SerializedName("correctBy")
    @Expose
    private Integer correctBy;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public Integer getCorrectBy() {
        return correctBy;
    }

    public void setCorrectBy(Integer correctBy) {
        this.correctBy = correctBy;
    }

}