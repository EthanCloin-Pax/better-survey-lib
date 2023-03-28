package com.example.bettersurveylib.api.survey.models;

import com.google.gson.annotations.SerializedName;

public class QuestionOption {

    @SerializedName("OptionNo")
    public String OptionNo;

    @SerializedName("Content")
    public String Content;

    @SerializedName("IsDefault")
    private Boolean IsDefault;

    public QuestionOption(String optionNo, String content, Boolean isDefault) {
        OptionNo = optionNo;
        Content = content;
        IsDefault = isDefault;
    }
}
