package com.example.bettersurveylib.api.survey.models;

import com.google.gson.annotations.SerializedName;

public class QuestionOptions {

    @SerializedName("OptionNo")
    private String OptionNo;

    @SerializedName("Content")
    private String Content;

    @SerializedName("IsDefault")
    private Boolean IsDefault;
}
