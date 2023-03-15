package com.example.bettersurveylib.api.survey.requests;


import com.google.gson.annotations.SerializedName;

public class GetQuestionnairesReq {

  @SerializedName("Token")
  public String token;

  @SerializedName("DeviceID")
  public String deviceID;

  @SerializedName("TimeStamp")
  public String timestamp;

  @SerializedName("SignatureData")
  public String signatureData;


  public GetQuestionnairesReq() { };



}
