package com.example.bettersurveylib.api;


import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Authenticator {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * use to encode request body for signature data. key should be registerRequestKey from TerminalRegister API
     *
     * @param data
     * @param key
     * @return
     */
    private static String signDataWithKey(String data, String key) {
        byte[] result = null;

        try {
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signinKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            // had issues importing the pax market api for security, hoping
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
     * use to encode request body for signature data. key should be registerRequestKey from TerminalRegister API
     *
     * @param requestBodyToEncode
     * @param key
     * @return
     */
    public String generateSignature(String requestBodyToEncode, String key) {
        // probably add checks here for required values?
        return signDataWithKey(requestBodyToEncode, key);
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
