package com.example.bettersurveylib.api.survey.requests;

import com.google.gson.annotations.SerializedName;

public class GetQuestionsReq extends BaseSurveyRequest {

    @SerializedName("QuestionnaireID")
    public String questionnaireID;

    public String getQuestionnaireID() {
        return questionnaireID;
    }

    public GetQuestionsReq(String token, String deviceID, String timestamp, String signatureData, String questionnaireID) {
        super(token, deviceID, timestamp, signatureData);
        this.questionnaireID = questionnaireID;
    }
}
