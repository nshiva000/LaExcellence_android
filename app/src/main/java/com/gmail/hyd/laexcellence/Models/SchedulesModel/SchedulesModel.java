package com.gmail.hyd.laexcellence.Models.SchedulesModel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchedulesModel {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("upcoming")
    @Expose
    private List<Upcoming> upcoming = null;
    @SerializedName("completed")
    @Expose
    private List<Completed> completed = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Upcoming> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<Upcoming> upcoming) {
        this.upcoming = upcoming;
    }

    public List<Completed> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Completed> completed) {
        this.completed = completed;
    }

}