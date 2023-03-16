package com.example.bettersurveylib.api.survey.models;

import com.google.gson.annotations.SerializedName;

public class QuestionOption {

    @SerializedName("OptionNo")
    private String OptionNo;

    @SerializedName("Content")
    private String Content;

    @SerializedName("IsDefault")
    private Boolean IsDefault;
}
