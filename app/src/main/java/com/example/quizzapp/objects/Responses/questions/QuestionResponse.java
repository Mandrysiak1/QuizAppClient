package com.example.quizzapp.objects.Responses.questions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("socID")
    @Expose
    private String socID;
    @SerializedName("question")
    @Expose
    private Question question;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocID() {
        return socID;
    }

    public void setSocID(String socID) {
        this.socID = socID;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
