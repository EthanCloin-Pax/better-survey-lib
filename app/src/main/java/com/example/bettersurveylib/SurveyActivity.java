package com.example.bettersurveylib;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bettersurveylib.api.SurveyGateway;
import com.example.bettersurveylib.api.survey.models.AnswerOption;
import com.example.bettersurveylib.api.survey.models.Question;
import com.example.bettersurveylib.api.survey.models.QuestionOption;
import com.example.bettersurveylib.api.survey.models.Questionnaire;
import com.example.bettersurveylib.api.survey.requests.GetQuestionnairesReq;
import com.example.bettersurveylib.api.survey.requests.GetQuestionsReq;
import com.example.bettersurveylib.api.survey.responses.GetQuestionnairesRsp;
import com.example.bettersurveylib.api.survey.responses.GetQuestionsRsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity {
    private static final String TAG = "EMC: ";
    // data
    Questionnaire surveyQuestionnaire;
    List<Question> surveyQuestions;
    Map<String, AnswerOption> selectedAnswers;

    // layout
    LinearLayout questionsLayout;

    // api
    SurveyGateway gateway = new SurveyGateway();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedAnswers = new HashMap<>();


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Layout stuff
        setContentView(R.layout.activity_multiple_choice_layout);
        questionsLayout = findViewById(R.id.questionsLinearLayout);

        initMockPlaceholderQuestionnaireInfo();
        for (Question q : surveyQuestions) {
            addQuestionView(q);
        }
    }

    private void requestQuestionnairesData() {
        GetQuestionnairesReq req = new GetQuestionnairesReq("token", "deviceId", "timestamp", "signaturedata");
        Callback<GetQuestionnairesRsp> getQuestionnairesCallback = new Callback<GetQuestionnairesRsp>() {
            @Override
            public void onResponse(Call<GetQuestionnairesRsp> call, Response<GetQuestionnairesRsp> response) {
                // just getting the first questionnaire for now, not sure how we will do it in prod
                assert response.body() != null;
                surveyQuestionnaire = response.body().questionnaires.get(0);
                requestQuestionsData();
            }

            @Override
            public void onFailure(Call<GetQuestionnairesRsp> call, Throwable t) {
                Log.w(TAG, "FAILED: " + t.getLocalizedMessage());
            }
        };

        gateway.async_requestQuestionnaires(req, getQuestionnairesCallback);
    }

    private void requestQuestionsData() {
        GetQuestionsReq req = new GetQuestionsReq("token", "deviceId", "timestamp", "signaturedata", "QNR_ID");
        Callback<GetQuestionsRsp> getQuestionsCallback = new Callback<GetQuestionsRsp>() {
            @Override
            public void onResponse(Call<GetQuestionsRsp> call, Response<GetQuestionsRsp> response) {
                // just getting the first questionnaire for now, not sure how we will do it in prod
                assert response.body() != null;
                surveyQuestions = response.body().questions;
            }

            @Override
            public void onFailure(Call<GetQuestionsRsp> call, Throwable t) {
                Log.w(TAG, "FAILED: " + t.getLocalizedMessage());
            }
        };

        gateway.async_requestQuestions(req, getQuestionsCallback);
    }

    /**
     * Fill survey with dummy data
     */
    private void initMockPlaceholderQuestionnaireInfo() {
        surveyQuestionnaire = new Questionnaire("FAKE_ID", "Survey Title", "Description of survey is rather extensive containing a long sentence which, while not a run-on, is rather pushing the boundary of reasonable sencence length.");
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<QuestionOption> options = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                String optionNumber = "" + i + j;
                QuestionOption o = new QuestionOption(optionNumber, " This is option" + optionNumber, false);
                options.add(o);
            }
            Question q = new Question(""+i, "This is question " + i, "product id", options);
            questions.add(q);
        }
        surveyQuestions = questions;
    }

    /**
     * Create a View for the provided Question data and add to the
     * @param question
     */
    private void addQuestionView(Question question) {

        LinearLayout qCard = (LinearLayout) getLayoutInflater().inflate(R.layout.view_multiple_choice_question_layout, questionsLayout, false);
        TextView qText = qCard.findViewById(R.id.questionText);
        qText.setText(question.Content);

        RadioGroup qOptionsView = qCard.findViewById(R.id.questionOptionsRadioGroup);
        qOptionsView.setId(View.generateViewId());
        qOptionsView.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checked = findViewById(checkedId);
            String checkedOptionNo = "";
            for (QuestionOption o : question.Options) {
                if (o.Content.equals(checked.getText())) {
                    checkedOptionNo = o.OptionNo;
                }
            }

                    selectedAnswers.put(
                            question.QuestionNo,
                            new AnswerOption(surveyQuestionnaire.QuestionnaireID, question.QuestionNo, checkedOptionNo));
                    Log.i(TAG, selectedAnswers.toString() + " added optionNo " + checkedOptionNo);
                }
        );
        addOptions(question, qOptionsView);
        questionsLayout.addView(qCard);
    }

    /**
     * Create a view for each option in the provided Question and add it to the provided RadioGroup
     * @param question
     * @param parentRadioGroup
     */
    private void addOptions(Question question, RadioGroup parentRadioGroup) {
        List<QuestionOption> qOptions = question.Options;
        for (QuestionOption o : qOptions) {
            RadioButton oView = (RadioButton) getLayoutInflater().inflate(R.layout.view_multiple_choice_question_option_layout, parentRadioGroup, false);
            oView.setId(View.generateViewId());
            oView.setText(o.Content);
            parentRadioGroup.addView(oView);
        }
    }
}
