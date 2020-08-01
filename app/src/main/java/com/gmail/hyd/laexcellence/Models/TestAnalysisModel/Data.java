package com.gmail.hyd.laexcellence.Models.TestAnalysisModel;

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
    @SerializedName("topicwise")
    @Expose
    private List<Topicwise> topicwise = null;
    @SerializedName("formwise")
    @Expose
    private List<Formwise> formwise = null;

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

    public List<Topicwise> getTopicwise() {
        return topicwise;
    }

    public void setTopicwise(List<Topicwise> topicwise) {
        this.topicwise = topicwise;
    }

    public List<Formwise> getFormwise() {
        return formwise;
    }

    public void setFormwise(List<Formwise> formwise) {
        this.formwise = formwise;
    }

}