package com.example.bettersurveylib.api;


import android.util.Base64;
import android.util.Log;

import com.example.bettersurveylib.api.register.requests.BaseRegisterRequest;
import com.example.bettersurveylib.api.survey.requests.BaseSurveyRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Authenticator {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private  static final String TAG = "EMC AUTH: ";

    /**
     * encodes request body using SHA1 algorithm.
     *
     * @param data
     * @param key
     * @return
     */
    private static String signDataSHA1(String data, String key) {
        byte[] result = null;

        try {
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signinKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());

            result = Base64.encode(rawHmac, Base64.DEFAULT);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }

    }

    private static String signDataSHA256(String data, String key) {
        byte[] result = null;

        try {
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signinKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            // had issues importing the pax market api for security, hoing
            // the android default encoding works the same, both are RFC2045

            result = Base64.encode(rawHmac, Base64.DEFAULT);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }

    }

    /**
     * Provide authentication signature for a request to TerminalRegister (PAX) API
     *
     * @param requestBodyToEncode
     * @param key
     * @return
     */
    public String registerTerminalSignature(String requestBodyToEncode, String key) {
        return signDataSHA256(requestBodyToEncode, key);
    }

    /**
     * Provide authentication signature for a request to Survey (SoundPayments) API
     *
     * Requires the DeviceID (or DeviceSN) AND Timestamp to be present in requestBody and the registerRequestKey as key
     *
     * @param requestBody
     * @param registerRequestKey
     * @return SignatureData which should be included in the Request
     */
    public static String registerSurveySignature(BaseSurveyRequest requestBody, String registerRequestKey) {
        if (requestBody.getTimestamp() == null || requestBody.getDeviceID() == null || requestBody.getToken() == null){
            Log.w(TAG, "Missing required info for Signature Generation");
        }
        String bodyToEncode = requestBody.getTimestamp() + requestBody.getDeviceID() + requestBody.getToken();

        return signDataSHA1(bodyToEncode, registerRequestKey);
    }

    public String surveySignature(BaseRegisterRequest requestBodyToEncode, String requestEncryptKey) {
        return "";
    }

    public String generateTimeStamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        String TimeStamp = String.format("%d%02d%02d%02d%02d%02d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        return TimeStamp;
    }
}
