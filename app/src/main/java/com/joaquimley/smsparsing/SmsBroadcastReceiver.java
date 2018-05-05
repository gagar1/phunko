package com.joaquimley.smsparsing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();
            }
            Log.d(TAG, "SMS detected: From " + smsSender + " With text " + smsBody);
            String response = null;
            DriverBAC driverBAC = null;
            int bacValueBencMark = 5;
            boolean driverTipsy = false;
            List<DriverBAC> list =null;
            if (smsBody.startsWith("RIDE REQUEST")) {
                Toast.makeText(context, "Blow in BAC sensor, you received a : " + smsBody, Toast.LENGTH_LONG).show();
                long startTime = System.currentTimeMillis();
                while (driverTipsy==false && (System.currentTimeMillis() - startTime <= 60000)) {
                    HttpConnect connection = new HttpConnect();
                    try {
                        response = connection.sendGET();
                        ObjectMapper mapper = new ObjectMapper();
                        list = mapper.readValue(response,
                                TypeFactory.defaultInstance().constructCollectionType(List.class,
                                        DriverBAC.class));
                        Log.d(TAG, "Driver Record::" + response);
                    } catch (IOException jse) {
                        Log.d(TAG, "Http connect failed");
                        jse.printStackTrace();
                    }
                    for (ListIterator<DriverBAC> iter = list.listIterator(); iter.hasNext(); ) {
                        Log.d(TAG, "Drive records being interated...");
                        DriverBAC driverRecord = iter.next();
                        if (Integer.valueOf(driverRecord.getBacValue()) >= 5) {
                            driverTipsy = true;
                            Log.d(TAG, "Sms with condition detected");
                            Toast.makeText(context, "ALARM!!! You are drunk, can't take ride, bye-bye", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    if (driverTipsy==false){
                            Toast.makeText(context, "HURRAY!!! You passed BAC test, go for ride", Toast.LENGTH_LONG).show();
                            driverTipsy = true;
                    }
                }
            }

        }
    }
}
