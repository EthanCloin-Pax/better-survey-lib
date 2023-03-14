package com.example.bettersurveylib.api.survey.models;

public class QuestionnaireModel {
  private String QuestionnaireID;
  private String Title;
  private String Description;

  public QuestionnaireModel(String questionnaireID, String title, String description) {
    QuestionnaireID = questionnaireID;
    Title = title;
    Description = description;
  }

  public String getQuestionnaireID() {
    return QuestionnaireID;
  }

  public void setQuestionnaireID(String questionnaireID) {
    QuestionnaireID = questionnaireID;
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
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
