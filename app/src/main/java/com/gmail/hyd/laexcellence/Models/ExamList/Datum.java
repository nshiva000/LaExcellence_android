
package com.gmail.hyd.laexcellence.Models.ExamList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_title")
    @Expose
    private String testTitle;
    @SerializedName("t_type")
    @Expose
    private String tType;
    @SerializedName("total_q")
    @Expose
    private Object totalQ;
    @SerializedName("test_date")
    @Expose
    private String testDate;
    @SerializedName("batches")
    @Expose
    private String batches;
    @SerializedName("marks_right")
    @Expose
    private String marksRight;
    @SerializedName("marks_wrong")
    @Expose
    private String marksWrong;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("explanation_video")
    @Expose
    private Object explanationVideo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("test_attempted")
    @Expose
    private Boolean testAttempted;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("rank")
    @Expose
    private Object rank;
    @SerializedName("myanswers")
    @Expose
    private Object myanswers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public String getTType() {
        return tType;
    }

    public void setTType(String tType) {
        this.tType = tType;
    }

    public Object getTotalQ() {
        return totalQ;
    }

    public void setTotalQ(Object totalQ) {
        this.totalQ = totalQ;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getBatches() {
        return batches;
    }

    public void setBatches(String batches) {
        this.batches = batches;
    }

    public String getMarksRight() {
        return marksRight;
    }

    public void setMarksRight(String marksRight) {
        this.marksRight = marksRight;
    }

    public String getMarksWrong() {
        return marksWrong;
    }

    public void setMarksWrong(String marksWrong) {
        this.marksWrong = marksWrong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getExplanationVideo() {
        return explanationVideo;
    }

    public void setExplanationVideo(Object explanationVideo) {
        this.explanationVideo = explanationVideo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getTestAttempted() {
        return testAttempted;
    }

    public void setTestAttempted(Boolean testAttempted) {
        this.testAttempted = testAttempted;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public Object getRank() {
        return rank;
    }

    public void setRank(Object rank) {
        this.rank = rank;
    }

    public Object getMyanswers() {
        return myanswers;
    }

    public void setMyanswers(Object myanswers) {
        this.myanswers = myanswers;
    }

}
