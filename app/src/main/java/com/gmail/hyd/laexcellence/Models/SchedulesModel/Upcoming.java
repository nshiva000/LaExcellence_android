package com.gmail.hyd.laexcellence.Models.SchedulesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Upcoming {

    @SerializedName("schedule_date")
    @Expose
    private String scheduleDate;
    @SerializedName("slots")
    @Expose
    private String slots;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("faculty")
    @Expose
    private String faculty;
    @SerializedName("building")
    @Expose
    private String building;
    @SerializedName("hall")
    @Expose
    private String hall;

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

}