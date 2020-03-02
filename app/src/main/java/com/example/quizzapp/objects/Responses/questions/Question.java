package com.example.quizzapp.objects.Responses.questions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class Question {

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("answers")
        @Expose
        private Answers answers;
        @SerializedName("properAnswer")
        @Expose
        private String properAnswer;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Answers getAnswers() {
            return answers;
        }

        public void setAnswers(Answers answers) {
            this.answers = answers;
        }

        public String getProperAnswer() {
            return properAnswer;
        }

        public void setProperAnswer(String properAnswer) {
            this.properAnswer = properAnswer;
        }

    }


