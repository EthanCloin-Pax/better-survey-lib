package com.example.bettersurveylib.api.survey.responses;

import com.example.bettersurveylib.api.survey.models.QuestionnaireModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GetQuestionnairesRsp {
  @SerializedName("StoreID")
  public String StoreID;

  @SerializedName("Questionnaires")
  public List<QuestionnaireModel> Questionnaires;

  @SerializedName("SignatureData")
  public String SignatureData;

  @SerializedName("TimeStamp")
  public String TimeStamp;

  @SerializedName("ExtData")
  public Map ExtData;

  @SerializedName("ExtDataList")
  public List ExtDataList;

  public GetQuestionnairesRsp(String storeID, List<QuestionnaireModel> questionnaires, String signatureData, String timeStamp, Map extData, List extDataList) {
    super();
    StoreID = storeID;
    Questionnaires = questionnaires;
    SignatureData = signatureData;
    TimeStamp = timeStamp;
    ExtData = extData;
    ExtDataList = extDataList;
  }

}
