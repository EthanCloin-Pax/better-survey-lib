package com.example.bettersurveylib.api.survey.responses;

import com.example.bettersurveylib.api.survey.models.Question;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GetQuestionsRsp {
    @SerializedName("QuestionnaireID")
    public String questionnaireID;

    @SerializedName("Questions")
    public List<Question> questions;

    @SerializedName("SignatureData")
    public String signatureData;

    @SerializedName("TimeStamp")
    public String timestamp;

    @SerializedName("ExtData")
    public Map ExtData;

    @SerializedName("ExtDataList")
    public List ExtDataList;
}
