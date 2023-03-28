package com.example.bettersurveylib.api.survey.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("QuestionNo")
    public String QuestionNo;

    @SerializedName("Content")
    public String Content;

    @SerializedName("ProductID")
    String ProductID;

    @SerializedName("Options")
    public List<QuestionOption> Options;

    public Question(String questionNo, String content, String productID, List<QuestionOption> options) {
        QuestionNo = questionNo;
        Content = content;
        ProductID = productID;
        Options = options;
    }
}
