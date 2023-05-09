package com.example.surveydemo.api.survey.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable, Parcelable {

    @SerializedName("QuestionNo")
    public String QuestionNo;

    @SerializedName("Content")
    public String Content;

    @SerializedName("ProductID")
    String ProductID;

    @SerializedName("Options")
    public List<QuestionOption> Options;

    public Question(String questionNo, String content, String productID, List<QuestionOption> options) {
        QuestionNo = questionNo;
        Content = content;
        ProductID = productID;
        Options = options;
    }

    protected Question(Parcel in) {
        QuestionNo = in.readString();
        Content = in.readString();
        ProductID = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(QuestionNo);
        parcel.writeString(Content);
        parcel.writeString(ProductID);
    }
}
