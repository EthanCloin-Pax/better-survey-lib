package com.example.bettersurveylib.api.survey.requests;

import com.google.gson.annotations.SerializedName;

public class GetQuestionsReq {

    // these may be common to all Survey requests
    @SerializedName("DeviceID")
    public String deviceID;

    @SerializedName("TimeStamp")
    public String timestamp;

    @SerializedName("SignatureData")
    public String signatureData;

    @SerializedName("Token")
    public String token;
    // these may be common to all Survey requests

    @SerializedName("QuestionnaireID")
    public String questionnaireID;
}
