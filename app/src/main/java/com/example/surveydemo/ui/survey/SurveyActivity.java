package com.example.surveydemo.ui.survey;

import static com.example.surveydemo.util.SurveyUtil.getQuestionnairesData;
import static com.example.surveydemo.util.SurveyUtil.submitAnswers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.bettersurveylib.R;
import com.example.surveydemo.api.survey.models.AnswerOption;
import com.example.surveydemo.api.survey.models.Question;
import com.example.surveydemo.api.survey.models.QuestionOption;
import com.example.surveydemo.api.survey.models.Questionnaire;

import com.example.surveydemo.api.survey.QuestionHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyActivity extends AppCompatActivity {
    private static final String TAG = "EMC: ";
    private Context mContext;
    private boolean isMockData = false;

    private final boolean USES_DEMO_STAR_MODE = true;

    // data
    LinearLayout qCard;
    Questionnaire surveyQuestionnaire;
    List<Question> surveyQuestions;
    QuestionHolder questionHolder = QuestionHolder.getInstance();
    Map<String, AnswerOption> selectedAnswers;

    // layout
    LinearLayout questionsLayout;
    TextView titleTv;
    Button submitBtn;

    RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        mContext = this;
        questionsLayout = findViewById(R.id.questionsLinearLayout);

        getQuestionnairesData(mContext);
        selectedAnswers = new HashMap<>();

        titleTv = findViewById(R.id.questionnaireTitle);

        submitBtn = findViewById(R.id.questionnaireSubmitBtn);
        submitBtn.setClickable(false);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: answer null check
                if (isMockData) {
                    navigateNext();
                } else {
                    if (USES_DEMO_STAR_MODE) {
                        List<AnswerOption> demoUploadResponse = new ArrayList<>();

                        AnswerOption quizFormatAnswer = new ArrayList<>(selectedAnswers.values()).get(0);
                        AnswerOption ratingFormatAnswer = new AnswerOption(surveyQuestionnaire.QuestionnaireID, questionHolder.getQuestionsList().get(1).QuestionNo,
                                String.valueOf(Math.round(ratingBar.getRating())));

                        demoUploadResponse.add(quizFormatAnswer);
                        demoUploadResponse.add(ratingFormatAnswer);

                        submitAnswers(mContext, demoUploadResponse);
                    } else {
                        submitAnswers(mContext, new ArrayList<>(selectedAnswers.values()));
                    }
                }
            }
        });
    }

    public static final String SURVEY_ANSWERS_SUBMITTED = "SURVEY_ANSWERS_SUBMITTED";
    public static final String SURVEY_QUESTIONNAIRE_RECEIVED = "SURVEY_QUESTIONNAIRE_RECEIVED";
    public static final String SURVEY_QUESTION_DATA_RECEIVED = "SURVEY_QUESTION_DATA_RECEIVED";
    public static final String TEMP_SURVEY_QUESTIONNAIRE_RECEIVED = "TEMP_SURVEY_QUESTIONNAIRE_RECEIVED";

    BroadcastReceiver receiver0 = new ResultReceiver();
    BroadcastReceiver receiver1 = new ResultReceiver();
    BroadcastReceiver receiver2 = new ResultReceiver();
    BroadcastReceiver receiver1temp = new ResultReceiver();

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter0 = new IntentFilter(SURVEY_ANSWERS_SUBMITTED);
        registerReceiver(receiver0, filter0);

        IntentFilter filter1 = new IntentFilter(SURVEY_QUESTIONNAIRE_RECEIVED);
        registerReceiver(receiver1, filter1);

        IntentFilter filter2 = new IntentFilter(SURVEY_QUESTION_DATA_RECEIVED);
        registerReceiver(receiver2, filter2);

        IntentFilter filter1temp = new IntentFilter(TEMP_SURVEY_QUESTIONNAIRE_RECEIVED);
        registerReceiver(receiver1temp, filter1temp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver0);
        unregisterReceiver(receiver1);
        unregisterReceiver(receiver2);
        unregisterReceiver(receiver1temp);
    }

    private void navigateNext() {
        Intent surveyCompleteActivity = new Intent(SurveyActivity.this, SurveyCompleteActivity.class);
        startActivity(surveyCompleteActivity);
        finish();
    }

    private void setQuestionTitle(String text) {
        titleTv.setText(text);
    }

    /**
     * Create a View for the provided Question data and add to the
     */
    private void fillQuestionsView() {
        for (Question q : surveyQuestions) {
            addQuestionView(q);
        }
    }

    /**
     * creates the UI expecting two questions in provided questionnaire. first is rendered as
     * a radio group, and second is rendered as a rating bar
     */
    private void fillQuestionsDemoView() {
        addQuestionView(surveyQuestions.get(0));
        addRatingView(surveyQuestions.get(1));
    }

    private void addRatingView(Question question) {
        TextView qText = qCard.findViewById(R.id.ratingText);
        qText.setText(question.Content);
        ratingBar = qCard.findViewById(R.id.questionRatingBar);
        ratingBar.setRating(0);
        questionsLayout.addView(qCard);
    }

    private void addQuestionView(Question question) {

        qCard = (LinearLayout) getLayoutInflater().inflate(R.layout.view_mc_and_star_rating,
                questionsLayout, false);
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

            if (allQuestionsAnswered()) {
                submitBtn.setClickable(true);
            }
        }
        );
        addOptions(question, qOptionsView);
    }

    /**
     * Create a view for each option in the provided Question and add it to the
     * provided RadioGroup
     * 
     * @param question
     * @param parentRadioGroup
     */
    private void addOptions(Question question, RadioGroup parentRadioGroup) {
        List<QuestionOption> qOptions = question.Options;
        for (QuestionOption o : qOptions) {
            RadioButton oView = (RadioButton) getLayoutInflater()
                    .inflate(R.layout.view_multiple_choice_question_option_layout, parentRadioGroup, false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                oView.setId(View.generateViewId());
            }
            oView.setText(o.Content);
            parentRadioGroup.addView(oView);
        }
    }

    private boolean allQuestionsAnswered() {
        if (selectedAnswers == null || surveyQuestions == null)
            return false;
        if (selectedAnswers.size() == surveyQuestions.size())
            return true;
        return false;
    }

    private void fillQuestionsWithDummyData() { // FIXME
        surveyQuestionnaire = new Questionnaire("DEMO_SURVEY", "Satisfaction Survey",
                "Demonstration of how a survey would appear to the customer.");

        List<QuestionOption> satisfactionOptions = new ArrayList<>();
        QuestionOption oPerfect = new QuestionOption("1", "Absolutely Perfect!", false);
        QuestionOption oGreat = new QuestionOption("2", "Great!", false);
        QuestionOption oGood = new QuestionOption("3", "Good.", false);
        QuestionOption oPoor = new QuestionOption("4", "Bad .", false);
        QuestionOption oWorst = new QuestionOption("5", "Never Again!", false);
        satisfactionOptions.add(oPerfect);
        satisfactionOptions.add(oGreat);
        satisfactionOptions.add(oGood);
        satisfactionOptions.add(oPoor);
        satisfactionOptions.add(oWorst);

        Question qCustomerService = new Question("1",
                "Which option best describes your customer service experience today?", "customer_service",
                satisfactionOptions);
        Question qProductQuality = new Question("2",
                "How would you describe the quality of the product you purchased today?", "product_quality",
                satisfactionOptions);
        Question qStoreCleanliness = new Question("3", "How would you describe the cleanliness of the store today?",
                "store_cleanliness", satisfactionOptions);

        List<Question> questions = new ArrayList<>();
        questions.add(qCustomerService);
        questions.add(qProductQuality);
        questions.add(qStoreCleanliness);

        surveyQuestions = questions;
    }

    public class ResultReceiver extends BroadcastReceiver {


        public static final String EXTRA_SERIALIZABLE = "EXTRA_SERIALIZABLE";

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() == SURVEY_ANSWERS_SUBMITTED) {
                navigateNext();
            } else if (intent.getAction() == SURVEY_QUESTIONNAIRE_RECEIVED) {
                surveyQuestionnaire = (Questionnaire) intent.getExtras().getSerializable(EXTRA_SERIALIZABLE);
                setQuestionTitle(surveyQuestionnaire.Title);
            } else if (intent.getAction() == SURVEY_QUESTION_DATA_RECEIVED) {
//                surveyQuestions = (List<Question>) intent.getExtras().getSerializable(EXTRA_SERIALIZABLE);
//                ArrayList<Question> temp = (ArrayList<Question>) intent.getExtras().getSerializable(EXTRA_SERIALIZABLE);
//                surveyQuestions = temp;
                // surveyQuestions = (List<Question>)
                // intent.getExtras().getSerializable(EXTRA_SERIALIZABLE);
                // ArrayList<Question> temp = (ArrayList<Question>)
                // intent.getExtras().getSerializable(EXTRA_SERIALIZABLE);
                // surveyQuestions = temp;
                surveyQuestions = questionHolder.getQuestionsList();

                if (USES_DEMO_STAR_MODE) {
                    fillQuestionsDemoView();
                } else {
                    fillQuestionsView();
                }
            } else if (intent.getAction() == TEMP_SURVEY_QUESTIONNAIRE_RECEIVED) { // FIXME
                isMockData = true;
                fillQuestionsWithDummyData();
                if (USES_DEMO_STAR_MODE) {
                    fillQuestionsDemoView();
                } else {
                    fillQuestionsView();
                }
            }
        }
    }
}
