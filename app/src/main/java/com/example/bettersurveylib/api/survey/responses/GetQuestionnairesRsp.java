package com.example.bettersurveylib.api.survey.responses;

import java.util.List;
import java.util.Map;

public class GetQuestionnairesRsp extends BaseRspModel {
  public String StoreID;
  public List<QuestionnaireModel> Questionnaires;
  public String SignatureData;
  public String TimeStamp;
  public Map ExtData;
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
