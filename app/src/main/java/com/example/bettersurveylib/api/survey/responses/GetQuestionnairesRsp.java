package com.example.bettersurveylib.api.survey.responses;

import com.example.bettersurveylib.api.survey.models.Questionnaire;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GetQuestionnairesRsp extends BaseSurveyResponse {
  @SerializedName("StoreID")
  public String storeID;

  @SerializedName("Questionnaires")
  public List<Questionnaire> questionnaires;

  public GetQuestionnairesRsp(String signatureData, String timestamp, String resultCode, String resultMessage, Map extData, List extDataList, String storeID, List<Questionnaire> questionnaires) {
    super(signatureData, timestamp, resultCode, resultMessage, extData, extDataList);
    this.storeID = storeID;
    this.questionnaires = questionnaires;
  }

  public GetQuestionnairesRsp(String responseCode, String responseMsg) {
    super(responseCode, responseMsg);
  }
}
