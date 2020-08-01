package com.gmail.hyd.laexcellence.Models.TestListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("s_id")
    @Expose
    private String sId;
    @SerializedName("tutor_mode")
    @Expose
    private String tutorMode;
    @SerializedName("timed_mode")
    @Expose
    private String timedMode;
    @SerializedName("q_mode")
    @Expose
    private String qMode;
    @SerializedName("q_forms")
    @Expose
    private String qForms;
    @SerializedName("topics")
    @Expose
    private String topics;
    @SerializedName("no_q")
    @Expose
    private String noQ;
    @SerializedName("questions")
    @Expose
    private String questions;
    @SerializedName("test_status")
    @Expose
    private String testStatus;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("right")
    @Expose
    private String right;
    @SerializedName("wrong")
    @Expose
    private String wrong;
    @SerializedName("omitted")
    @Expose
    private String omitted;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("created_from")
    @Expose
    private String createdFrom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getTutorMode() {
        return tutorMode;
    }

    public void setTutorMode(String tutorMode) {
        this.tutorMode = tutorMode;
    }

    public String getTimedMode() {
        return timedMode;
    }

    public void setTimedMode(String timedMode) {
        this.timedMode = timedMode;
    }

    public String getQMode() {
        return qMode;
    }

    public void setQMode(String qMode) {
        this.qMode = qMode;
    }

    public String getQForms() {
        return qForms;
    }

    public void setQForms(String qForms) {
        this.qForms = qForms;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getNoQ() {
        return noQ;
    }

    public void setNoQ(String noQ) {
        this.noQ = noQ;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getOmitted() {
        return omitted;
    }

    public void setOmitted(String omitted) {
        this.omitted = omitted;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

}