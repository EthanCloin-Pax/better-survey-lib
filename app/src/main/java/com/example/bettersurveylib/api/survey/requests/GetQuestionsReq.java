package com.example.bettersurveylib.api.survey.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Request to retrieve questions from provided QuestionnaireID from Survey API
 */
public class GetQuestionsReq extends BaseSurveyRequest {

    @SerializedName("QuestionnaireID")
    public String questionnaireID;

    public String getQuestionnaireID() {
        return questionnaireID;
    }

    public GetQuestionsReq(String deviceID, String token, String questionnaireID) {
        super(deviceID, token);
        this.questionnaireID = questionnaireID;
    }

    public GetQuestionsReq(String deviceID, String token, String timestamp, String signatureData, String questionnaireID) {
        super(deviceID, token, timestamp, signatureData);
        this.questionnaireID = questionnaireID;
    }
}
