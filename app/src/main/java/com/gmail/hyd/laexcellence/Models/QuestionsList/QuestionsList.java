package com.gmail.hyd.laexcellence.Models.QuestionsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionsList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("qb_id")
    @Expose
    private Object qbId;
    @SerializedName("test_id")
    @Expose
    private String testId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("a")
    @Expose
    private String a;
    @SerializedName("b")
    @Expose
    private String b;
    @SerializedName("c")
    @Expose
    private String c;
    @SerializedName("d")
    @Expose
    private String d;
    @SerializedName("e")
    @Expose
    private Object e;
    @SerializedName("f")
    @Expose
    private Object f;
    @SerializedName("ans")
    @Expose
    private String ans;
    @SerializedName("s_code")
    @Expose
    private Object sCode;
    @SerializedName("t_code")
    @Expose
    private Object tCode;
    @SerializedName("st_code")
    @Expose
    private String stCode;
    @SerializedName("explanation")
    @Expose
    private String explanation;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("difficulty_level")
    @Expose
    private String difficultyLevel;
    @SerializedName("del")
    @Expose
    private String del;
    @SerializedName("sub_title")
    @Expose
    private Object subTitle;
    @SerializedName("topic_title")
    @Expose
    private Object topicTitle;
    @SerializedName("subtopic_title")
    @Expose
    private String subtopicTitle;

    private ArrayList my_ans;

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    private String answers;

    public ArrayList getMy_ans() {
        return my_ans;
    }

    public void setMy_ans(ArrayList my_ans) {
        this.my_ans = my_ans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getQbId() {
        return qbId;
    }

    public void setQbId(Object qbId) {
        this.qbId = qbId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Object getE() {
        return e;
    }

    public void setE(Object e) {
        this.e = e;
    }

    public Object getF() {
        return f;
    }

    public void setF(Object f) {
        this.f = f;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Object getSCode() {
        return sCode;
    }

    public void setSCode(Object sCode) {
        this.sCode = sCode;
    }

    public Object getTCode() {
        return tCode;
    }

    public void setTCode(Object tCode) {
        this.tCode = tCode;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String toString() {
        return "QuestionsList{" +
                "id='" + id + '\'' +
                ", qbId=" + qbId +
                ", testId='" + testId + '\'' +
                ", question='" + question + '\'' +
                ", a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", e=" + e +
                ", f=" + f +
                ", ans='" + ans + '\'' +
                ", sCode=" + sCode +
                ", tCode=" + tCode +
                ", stCode='" + stCode + '\'' +
                ", explanation='" + explanation + '\'' +
                ", source='" + source + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", del='" + del + '\'' +
                ", subTitle=" + subTitle +
                ", topicTitle=" + topicTitle +
                ", subtopicTitle='" + subtopicTitle + '\'' +
                ", my_ans=" + my_ans +
                ", answers='" + answers + '\'' +
                '}';
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public Object getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(Object subTitle) {
        this.subTitle = subTitle;
    }

    public Object getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(Object topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getSubtopicTitle() {
        return subtopicTitle;
    }

    public void setSubtopicTitle(String subtopicTitle) {
        this.subtopicTitle = subtopicTitle;
    }

}