package com.example.quizzapp.objects.Responses.questions;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answers {

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

}
