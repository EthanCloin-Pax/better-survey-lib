package com.example.bettersurveylib.api.survey.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("QuestionNo")
    String QuestionNo;

    @SerializedName("Content")
    String Content;

    @SerializedName("ProductID")
    String ProductID;

    @SerializedName("Options")
    List<QuestionOption> Options;
}
