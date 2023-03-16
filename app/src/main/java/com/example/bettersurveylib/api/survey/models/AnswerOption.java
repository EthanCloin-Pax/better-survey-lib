package com.example.bettersurveylib.api.survey.models;

import com.google.gson.annotations.SerializedName;

public class AnswerOption {
    @SerializedName("QuestionnaireID")
    public String questionnaireID;

    @SerializedName("QuestionNo")
    public String questionNo;

    @SerializedName("OptionNo")
    public String optionNo;

    @SerializedName("CustomerID")
    public String customerID;

    @SerializedName("FirstName")
    public String firstName;

    @SerializedName("LastName")
    public String lastName;

    @SerializedName("Phone")
    public String phone;

    @SerializedName("Review")
    public String review;
}
