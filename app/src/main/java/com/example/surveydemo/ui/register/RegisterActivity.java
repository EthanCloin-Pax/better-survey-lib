package com.example.surveydemo.ui.register;

import static com.paxus.pay.host.ui.init.surveydemo.util.SurveyUtil.generateQRCode;
import static com.paxus.pay.host.ui.init.surveydemo.util.SurveyUtil.getRegisterData;
import static com.paxus.pay.host.ui.init.surveydemo.util.SurveyUtil.registerTerminal;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;
import com.paxus.pay.host.ui.init.R;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {


    public static final String TAG = "EMC: RegisterActivity - ";
    private Context context;
    private ImageView qrIv;
    private ProgressDialog spinner = null;
    private boolean isTerminalRegistered = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        getRegisterData(context);

        qrIv = findViewById(R.id.register_register_qr_iv);
        //TODO: spinner
        toggleSpinner(true, "Loading terminal info...");
        Button activateBtn = findViewById(R.id.register_register_activate_btn);
        activateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (!isTerminalRegistered) {
//                    makeToast("Please scan the QR code and complete registration first.");
//                } else {
//                    toggleSpinner(true, "Activating Terminal...");
//                    // TODO: check if url is not null
                    registerTerminal(context);
//                }
            }
        });
    }

    public static final String SURVEY_REGISTER_DATA_RECEIVED = "SURVEY_REGISTER_DATA_RECEIVED";
    public static final String SURVEY_TERMINAL_REGISTERED = "SURVEY_TERMINAL_REGISTERED";
    BroadcastReceiver receiver0 = new ResultReceiver();
    BroadcastReceiver receiver1 = new ResultReceiver();

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter0 = new IntentFilter(SURVEY_REGISTER_DATA_RECEIVED);
        registerReceiver(receiver0, filter0);

        IntentFilter filter1 = new IntentFilter(SURVEY_TERMINAL_REGISTERED);
        registerReceiver(receiver1, filter1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver0);
        unregisterReceiver(receiver1);
    }

    private void toggleSpinner(boolean toggle, String text) {

        if (spinner == null) return;
        if (toggle) {
            spinner = new ProgressDialog(context);
            spinner.setMessage(text);
            spinner.show();
        } else {
            spinner.cancel();
            spinner = null;
        }
    }

    private void setImageView(String url) {
        try {
            qrIv.setImageBitmap(generateQRCode(url, qrIv.getWidth(), qrIv.getHeight()));
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            //FIXME what if it throws exception?
            toggleSpinner(false, null);
        }
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        try {
//            qrIv.setImageBitmap(generateQRCode(url, qrIv.getWidth(), qrIv.getHeight()));
//        } catch (WriterException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    private void makeToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
     }

    public class ResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == SURVEY_REGISTER_DATA_RECEIVED) { // get url

                String url = intent.getExtras().getString(Intent.EXTRA_TEXT, null);
                setImageView(url);

            } else if (intent.getAction() == SURVEY_TERMINAL_REGISTERED) { // activate click result

                String resultCode = intent.getExtras().getString(Intent.EXTRA_TEXT, null);
                toggleSpinner(false, null);
                if (resultCode.equals("200") || resultCode.equals("0000")) {
//                    isTerminalRegistered = true;
                    makeToast("Successfully registered.");
                    Intent activityIntent = new Intent(context, RegisterCompleteActivity.class);
                    startActivity(activityIntent);
                    finish();
                } else if (resultCode != null) {
                    makeToast("something went wrong~ while registering~");
                }
//                else {
//                    makeToast("Store has not been registered.");
//                }
            }
        }
    }
}