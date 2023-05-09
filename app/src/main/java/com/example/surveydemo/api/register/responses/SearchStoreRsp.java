package com.example.surveydemo.api.register.responses;

import com.google.gson.annotations.SerializedName;
import com.example.surveydemo.api.register.models.StoreDetail;

public class SearchStoreRsp extends BaseRegisterResponse {

    @SerializedName("storeDetails")
    public StoreDetail storeDetail;


    public SearchStoreRsp(String responseCode, String responseMessage) {
        super(responseCode, responseMessage);
    }
}
