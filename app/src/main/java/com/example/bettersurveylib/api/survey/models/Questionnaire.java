package com.example.bettersurveylib.api.survey.models;

import com.google.gson.annotations.SerializedName;

public class Questionnaire {

  @SerializedName("QuestionnaireID")
  public String QuestionnaireID;

  @SerializedName("Title")
  public String Title;

  @SerializedName("Description")
  public String Description;

  public Questionnaire() {};
  public Questionnaire(String questionnaireID, String title, String description) {
    QuestionnaireID = questionnaireID;
    Title = title;
    Description = description;
  }

  @Override
  public String toString() {
    return "QuestionnaireModel{" +
      "QuestionnaireID='" + QuestionnaireID + '\'' +
      ", Title='" + Title + '\'' +
      ", Description='" + Description + '\'' +
      '}';
  }
}
