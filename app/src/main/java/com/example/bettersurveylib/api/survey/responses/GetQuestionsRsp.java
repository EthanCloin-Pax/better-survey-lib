package com.example.bettersurveylib.api.survey.responses;

import com.example.bettersurveylib.api.survey.models.Question;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GetQuestionsRsp extends BaseSurveyResponse {
    @SerializedName("QuestionnaireID")
    public String questionnaireID;

    @SerializedName("Questions")
    public List<Question> questions;

    public GetQuestionsRsp(String signatureData, String timestamp, String resultCode, String resultMessage, Map extData, List extDataList, String questionnaireID, List<Question> questions) {
        super(signatureData, timestamp, resultCode, resultMessage, extData, extDataList);
        this.questionnaireID = questionnaireID;
        this.questions = questions;
    }
}
