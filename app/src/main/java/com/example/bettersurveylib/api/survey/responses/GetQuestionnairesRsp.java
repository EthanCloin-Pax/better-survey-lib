package com.example.bettersurveylib.api.survey.responses;

import com.example.bettersurveylib.api.survey.models.Questionnaire;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GetQuestionnairesRsp extends BaseSurveyResponse {
  @SerializedName("StoreID")
  private String storeID;

  @SerializedName("Questionnaires")
  private List<Questionnaire> questionnaires;

  public String getStoreID() {
    return storeID;
  }

  public List<Questionnaire> getQuestionnaires() {
    return questionnaires;
  }

  public GetQuestionnairesRsp(String storeID, List<Questionnaire> questionnaires, String signatureData, String timestamp, Map extData, List extDataList) {
    super(signatureData, timestamp, extData, extDataList);
    this.storeID = storeID;
    this.questionnaires = questionnaires;
  }
}
