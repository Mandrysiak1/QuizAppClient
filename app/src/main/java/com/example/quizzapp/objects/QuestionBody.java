package com.example.quizzapp.objects;


public class QuestionBody {
    private String socID;
    private Questionb question;

    public String getSocID() {
        return socID;
    }

    public void setSocID(String socID) {
        this.socID = socID;
    }

    public Questionb getQuestion() {
        return question;
    }

    public void setQuestion(Questionb question) {
        this.question = question;
    }

    public QuestionBody(String socID, Questionb question) {
        this.socID = socID;
        this.question = question;
    }
}