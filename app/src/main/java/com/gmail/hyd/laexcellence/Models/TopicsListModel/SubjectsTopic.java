package com.gmail.hyd.laexcellence.Models.TopicsListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectsTopic {

    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("topic_id")
    @Expose
    private String topicId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("count")
    @Expose
    private String count;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}