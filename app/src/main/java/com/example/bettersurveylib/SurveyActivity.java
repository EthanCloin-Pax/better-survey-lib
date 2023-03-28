package com.example.bettersurveylib;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bettersurveylib.api.survey.models.AnswerOption;
import com.example.bettersurveylib.api.survey.models.Question;
import com.example.bettersurveylib.api.survey.models.QuestionOption;
import com.example.bettersurveylib.api.survey.models.Questionnaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyActivity extends AppCompatActivity {
    private static final String TAG = "EMC: ";
    // data
    Questionnaire surveyQuestionnaire;
    List<Question> surveyQuestions;
    Map<String, AnswerOption> selectedAnswers;

    // layout
    LinearLayout questionsLayout;

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

        initPlaceholderQuestionnaireInfo();
        for (Question q : surveyQuestions) {
            addQuestionView(q);
        }
    }

    /**
     * Fill survey with dummy data
     */
    private void initPlaceholderQuestionnaireInfo() {
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
     * add question to the survey view
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

    private void addOptions(Question question, RadioGroup parentRadioGroup) {
        List<QuestionOption> qOptions = question.Options;
        for (QuestionOption o : qOptions) {
            RadioButton oView = (RadioButton) getLayoutInflater().inflate(R.layout.view_multiple_choice_question_option_layout, parentRadioGroup, false);
            oView.setId(View.generateViewId());
            oView.setText(o.Content);
            parentRadioGroup.addView(oView);
            Log.i(TAG, "parent rg of id " + parentRadioGroup.getId() + " has " + parentRadioGroup.getChildCount() + " kids");
        }
    }
}
