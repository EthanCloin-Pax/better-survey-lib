package com.example.bettersurveylib.api.survey.responses;

import com.example.bettersurveylib.api.survey.models.Question;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GetQuestionsRsp extends BaseSurveyResponse {
    @SerializedName("QuestionnaireID")
    private String questionnaireID;

    @SerializedName("Questions")
    private List<Question> questions;


    public GetQuestionsRsp(String questionnaireID, List<Question> questions, String signatureData, String timestamp, Map extData, List extDataList) {
        super(signatureData, timestamp, extData, extDataList);
        this.questionnaireID = questionnaireID;
        this.questions = questions;
    }

    public String getQuestionnaireID() {
        return questionnaireID;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
