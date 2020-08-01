package com.gmail.hyd.laexcellence.Models.TestAnalysisModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topicwise {

    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("c")
    @Expose
    private Integer c;
    @SerializedName("w")
    @Expose
    private Integer w;
    @SerializedName("u")
    @Expose
    private Integer u;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getU() {
        return u;
    }

    public void setU(Integer u) {
        this.u = u;
    }

}