package com.example.bettersurveylib.api.register.responses;

import com.example.bettersurveylib.api.register.models.StoreDetail;
import com.google.gson.annotations.SerializedName;

public class SearchStoreRsp extends BaseRegisterResponse {

    @SerializedName("storeDetails")
    public StoreDetail storeDetail;


    public SearchStoreRsp(String responseCode, String responseMessage) {
        super(responseCode, responseMessage);
    }
}
