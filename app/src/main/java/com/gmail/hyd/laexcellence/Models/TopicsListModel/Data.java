package com.gmail.hyd.laexcellence.Models.TopicsListModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("subjects_topics")
    @Expose
    private List<SubjectsTopic> subjectsTopics = null;
    @SerializedName("subjects")
    @Expose
    private List<Subject> subjects = null;

    public List<SubjectsTopic> getSubjectsTopics() {
        return subjectsTopics;
    }

    public void setSubjectsTopics(List<SubjectsTopic> subjectsTopics) {
        this.subjectsTopics = subjectsTopics;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}