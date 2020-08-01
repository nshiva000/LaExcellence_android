package com.gmail.hyd.laexcellence.Models.TopicsListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("subject_title")
    @Expose
    private String subjectTitle;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

}