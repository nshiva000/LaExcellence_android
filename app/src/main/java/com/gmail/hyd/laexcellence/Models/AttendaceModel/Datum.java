package com.gmail.hyd.laexcellence.Models.AttendaceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}