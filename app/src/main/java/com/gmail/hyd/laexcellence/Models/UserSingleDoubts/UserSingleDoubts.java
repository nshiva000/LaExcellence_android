package com.gmail.hyd.laexcellence.Models.UserSingleDoubts;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSingleDoubts {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("doubt")
    @Expose
    private String doubt;
    @SerializedName("discussion")
    @Expose
    private List<Discussion> discussion = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDoubt() {
        return doubt;
    }

    public void setDoubt(String doubt) {
        this.doubt = doubt;
    }

    public List<Discussion> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(List<Discussion> discussion) {
        this.discussion = discussion;
    }

}