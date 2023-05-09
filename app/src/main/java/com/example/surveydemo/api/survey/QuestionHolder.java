package com.example.surveydemo.api.survey;

import com.paxus.pay.host.ui.init.surveydemo.api.survey.models.Question;

import java.util.List;

/**
 * singleton class to avoid using broadcast. just put the question response in
 * here!
 */
public class QuestionHolder {
    private static QuestionHolder INSTANCE = null;
    private static List<Question> questionsList = null;

    public static QuestionHolder getInstance() {
        if (INSTANCE == null) {
            return new QuestionHolder();
        }
        return INSTANCE;
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }
}