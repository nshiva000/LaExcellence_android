package com.gmail.hyd.laexcellence.Models.TestResultsModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("right")
    @Expose
    private Integer right;
    @SerializedName("wrong")
    @Expose
    private Integer wrong;
    @SerializedName("omitted")
    @Expose
    private Integer omitted;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("correct_By")
    @Expose
    private List<CorrectBy> correctBy = null;

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getWrong() {
        return wrong;
    }

    public void setWrong(Integer wrong) {
        this.wrong = wrong;
    }

    public Integer getOmitted() {
        return omitted;
    }

    public void setOmitted(Integer omitted) {
        this.omitted = omitted;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public List<CorrectBy> getCorrectBy() {
        return correctBy;
    }

    public void setCorrectBy(List<CorrectBy> correctBy) {
        this.correctBy = correctBy;
    }

}